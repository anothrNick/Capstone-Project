package com.dev.nick.scorch;

import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int TOURNAMENTS = 1;
    public static final int GAMES = 2;
    public static final int PLAYERS = 3;
    public static final int TEAMS = 4;

    String NAV_TITLES[] = {"Tournaments", "Games", "Players", "Teams"};
    int NAV_ICONS[] = {R.drawable.ic_tourney, R.drawable.ic_games, R.drawable.ic_player, R.drawable.ic_team};

    String NAME = "Nick Sjostrom";
    int AVATAR = R.drawable.ic_player;

    //private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NavAdapter(NAV_TITLES,NAV_ICONS,NAME,"",AVATAR);

        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                    Drawer.closeDrawers();
                    //Toast.makeText(MainActivity.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    switch(recyclerView.getChildPosition(child)){
                        case 0:
                            break;
                        case TOURNAMENTS:
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, TournamentFragment.newInstance())
                                    .commit();
                            break;
                        case GAMES:
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, GameFragment.newInstance())
                                    .commit();
                            break;
                        case PLAYERS:
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, PlayerFragment.newInstance())
                                    .commit();
                            break;
                        case TEAMS:
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, TeamFragment.newInstance())
                                    .commit();
                            break;
                    }
                    return true;

                }

                return false;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();

        mTitle = getTitle();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case TOURNAMENTS:
                mTitle = getString(R.string.nav_tournaments);
                break;
            case GAMES:
                mTitle = getString(R.string.nav_games);
                break;
            case PLAYERS:
                mTitle = getString(R.string.nav_players);
                break;
            case TEAMS:
                mTitle = getString(R.string.nav_teams);
                break;
        }

        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        restoreActionBar();
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
