package com.dev.nick.scorch.tournaments;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.adapters.ViewPagerAdapter;

public class TournamentNewActivity extends AppCompatActivity {

    private ViewPager mPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_new_activity);

        mPager = (ViewPager) findViewById(R.id.new_tournament_pager);
        toolbar = (Toolbar) findViewById(R.id.new_tourney_toolbar);

        setSupportActionBar(toolbar);
        setupViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tournament_new, menu);
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //adapter.addFrag(gameSelectType, "");
        //adapter.addFrag(gameSelectMembers, "");
        viewPager.setAdapter(adapter);
    }
}
