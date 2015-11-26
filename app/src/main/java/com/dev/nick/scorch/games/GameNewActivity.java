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
import com.dev.nick.scorch.model.Game;
import com.dev.nick.scorch.model.GameTeam;
import com.dev.nick.scorch.model.Player;
import com.dev.nick.scorch.model.Team;

import java.util.ArrayList;
import java.util.Date;

public class GameNewActivity extends AppCompatActivity implements GameSelectType.OnFragmentInteractionListener, GameSelectMembersFragment.OnFragmentInteractionListener {

    private ViewPager mPager;
    private GameSelectType gameSelectType;
    private GameSelectMembersFragment gameSelectMembers;
    private int type; // 0 = players, 1 = teams
    private ArrayList<String> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_new_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.new_game_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.new_game);

        type = -1;
        mPager = (ViewPager) findViewById(R.id.new_game_pager);

        gameSelectType = new GameSelectType();
        gameSelectMembers = new GameSelectMembersFragment();

        members = new ArrayList<>();

        setupViewPager(mPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
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
        Game game = new Game();
        game.created = new Date().toString();
        game.save();
        long id = game.getId();

        if (id > 0) {
            for (String mid : members) {
                GameTeam gameTeam = new GameTeam();
                if(type == Game.PLAYERS)
                    gameTeam.player = Player.findById(Player.class, Long.parseLong(mid));
                else if(type == Game.TEAMS)
                    gameTeam.team = Team.findById(Team.class, Long.parseLong(mid));
                gameTeam.type = type;
                gameTeam.game = Game.findById(Game.class, id);
                gameTeam.save();
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
