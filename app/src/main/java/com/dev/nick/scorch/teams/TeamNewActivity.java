package com.dev.nick.scorch.teams;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.adapters.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamNewActivity extends AppCompatActivity {

    public static String teamName;
    public static ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_new_activity);

        teamName = "";
        mPager = (ViewPager) findViewById(R.id.new_team_pager);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.new_team_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.new_team);

        setupViewPager(mPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TeamName(), "");
        adapter.addFrag(new TeamPlayerSelection(), "");
        viewPager.setAdapter(adapter);
    }

    public void incPager(){
        if(mPager != null) {
            mPager.setCurrentItem(1);
        }
    }

    public void decPager(){
        if(mPager != null) {
            mPager.setCurrentItem(0);
        }
    }
}
