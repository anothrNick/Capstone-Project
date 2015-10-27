package com.dev.nick.scorch.games;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.adapters.ViewPagerAdapter;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;

import java.util.ArrayList;
import java.util.Date;

public class GameNewActivity extends AppCompatActivity implements GameSelectType.OnFragmentInteractionListener, GameSelectMembersFragment.OnFragmentInteractionListener {

    private ViewPager mPager;
    private Toolbar toolbar;
    private GameSelectType gameSelectType;
    private GameSelectMembersFragment gameSelectMembers;
    private int type; // 0 = players, 1 = teams
    private ArrayList<String> members;
    private ScorchDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_new_activity);
        dbHelper = new ScorchDbHelper(this);

        type = -1;
        mPager = (ViewPager) findViewById(R.id.new_game_pager);
        toolbar = (Toolbar) findViewById(R.id.new_game_toolbar);

        gameSelectType = new GameSelectType();
        gameSelectMembers = new GameSelectMembersFragment();

        members = new ArrayList<>();

        setSupportActionBar(toolbar);
        setupViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_new, menu);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("New GameBean");
        }

        return true;
    }

    public int memberCount() {
        return members.size();
    }

    public void clearMembers() {
        members.clear();
    }

    /**
     * GameSelectType.OnFragmentInteractionListener
     */
    public void onNext() {
        if(mPager != null)
            mPager.setCurrentItem(1);
    }

    public void onSelect(int typ) {
        type = typ;
        gameSelectMembers.changeAdapter(type);
        clearMembers();
    }

    public void onCancel() {
        finish();
    }

    /**
     * GameSelectMembersFragment.OnFragmentInteractionListener
     */
    public void onMemberSelected(String id) {
        if(members.contains(id)) {
            members.remove(id);
        }
        else {
            members.add(id);
        }
    }

    public void onStartGame() {
        //Toast.makeText(this, "GameBean Type: " + type + ", GameBean Members: " + members.toString(), Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ScorchContract.Game.COLUMN_CREATED, new Date().toString());
        long id = db.insert(ScorchContract.Game.TABLE_NAME, "null", values);

        if (id > 0) {
            for (String mid : members) {
                values = new ContentValues();
                values.put(ScorchContract.GameTeams.COLUMN_TEAM, mid);
                values.put(ScorchContract.GameTeams.COLUMN_TYPE, type);
                values.put(ScorchContract.GameTeams.COLUMN_GAME, id);
                db.insert(ScorchContract.GameTeams.TABLE_NAME, "null", values);
            }
        }

        finish();
    }

    public void onBack() {
        if(mPager != null)
            mPager.setCurrentItem(0);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(gameSelectType, "");
        adapter.addFrag(gameSelectMembers, "");
        viewPager.setAdapter(adapter);
    }
}
