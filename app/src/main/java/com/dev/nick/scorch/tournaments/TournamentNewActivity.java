package com.dev.nick.scorch.tournaments;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import com.dev.nick.scorch.R;
import com.dev.nick.scorch.adapters.ViewPagerAdapter;

import java.util.ArrayList;

public class TournamentNewActivity extends AppCompatActivity implements TournamentSelectType.OnFragmentInteractionListener, TournamentSelectMembers.OnFragmentInteractionListener, TournamentSelectTitle.OnFragmentInteractionListener {

    private ViewPager mPager;
    private Toolbar toolbar;

    private TournamentSelectTitle selectTitle;
    private TournamentSelectType selectType;
    private TournamentSelectMembers selectMembers;
    private int type; // 0 = player, 1 = teams
    private ArrayList<String> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_new_activity);

        type = -1;

        mPager = (ViewPager) findViewById(R.id.new_tournament_pager);
        toolbar = (Toolbar) findViewById(R.id.new_tourney_toolbar);

        selectType = new TournamentSelectType();
        selectMembers = new TournamentSelectMembers();
        selectTitle = new TournamentSelectTitle();

        members = new ArrayList<>();

        setSupportActionBar(toolbar);
        setupViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tournament_new, menu);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("New Tournament");
        }
        return true;
    }

    public void clearMembers() {
        members.clear();
    }

    public void onNext(int item) {
        if(mPager != null)
            mPager.setCurrentItem(item);
    }

    public void onSelect(int typ) {
        type = typ;
        selectMembers.changeAdapter(type);
        clearMembers();
    }

    public void onCancel() {
        finish();
    }

    public void onCreateTourney() {

    }

    public void onMemberSelected(String id) {
        if(members.contains(id)) {
            members.remove(id);
        }
        else {
            members.add(id);
        }
    }

    public void onBack(int item) {
        if(mPager != null)
            mPager.setCurrentItem(item);
    }

    public void updateTitle(String title) {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(selectTitle, "");
        adapter.addFrag(selectType, "");
        adapter.addFrag(selectMembers, "");
        viewPager.setAdapter(adapter);
    }
}
