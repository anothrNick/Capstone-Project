package com.dev.nick.scorch.games;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.adapters.ViewPagerAdapter;
import com.dev.nick.scorch.dummy.DummyFragment;

import java.util.ArrayList;

public class GameNewActivity extends AppCompatActivity implements GameSelectType.OnFragmentInteractionListener, GameSelectMembersFragment.OnFragmentInteractionListener {

    private ViewPager mPager;
    private Toolbar toolbar;
    private GameSelectType gameSelectType;
    private GameSelectMembersFragment gameSelectMembers;
    private int type; // 0 = players, 1 = teams
    private ArrayList<Integer> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_new_activity);

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
            actionBar.setTitle("New Game");
        }

        return true;
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
    }

    public void onCancel() {
        finish();
    }

    /**
     * GameSelectMembersFragment.OnFragmentInteractionListener
     */
    public void onMemberSelected(int id, boolean add) {
        if(members.contains(id)) {
            members.remove(id);
        }
        else {
            members.add(id);
        }
    }

    public void onStartGame() {
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
