package com.dev.nick.scorch.players;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;
import com.dev.nick.scorch.dummy.DummyFragment;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetailActivity extends AppCompatActivity {

    public static String PLAYER_ID = "com.dev.nick.scorch.PLAYER_ID";

    private TextView playerName;
    private TextView playerPosition;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_detail_activity);

        playerName = (TextView) findViewById(R.id.player_name);
        playerPosition = (TextView) findViewById(R.id.player_position);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = Long.toString(extras.getLong(PLAYER_ID));

            if(value != null && !value.isEmpty()){
                ScorchDbHelper dbHelper = new ScorchDbHelper(this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String where = "id = ?";
                String[] whereArgs = new String[]{
                        value
                };

                Cursor cursor = db.query(
                        ScorchContract.Players.TABLE_NAME,
                        PlayerFragment.player_projection,
                        where,
                        whereArgs,
                        null,
                        null,
                        PlayerFragment.player_sortOrder
                );

                if(cursor.moveToFirst()) {
                    playerName.setText(cursor.getString(cursor.getColumnIndex(ScorchContract.Players.COLUMN_NAME)));
                    playerPosition.setText(cursor.getString(cursor.getColumnIndex(ScorchContract.Players.COLUMN_ID)));
                }
            }
        }

        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DummyFragment(), "Stats");
        adapter.addFrag(new DummyFragment(), "Games");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
