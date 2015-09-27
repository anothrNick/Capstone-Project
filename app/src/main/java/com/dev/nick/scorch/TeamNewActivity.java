package com.dev.nick.scorch;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;

import java.util.ArrayList;
import java.util.Date;

public class TeamNewActivity extends AppCompatActivity {

    private ScorchDbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private PlayerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> players;
    private Button createBtn;
    private EditText teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_new_activity);

        players = new ArrayList<String>();
        dbHelper = new ScorchDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                ScorchContract.Players.TABLE_NAME,
                PlayerFragment.player_projection,
                null,
                null,
                null,
                null,
                PlayerFragment.player_sortOrder
        );
        mAdapter = new PlayerListAdapter(this, cursor);

        // Set the adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.players);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        long playerid = mAdapter.getItemId(position);
                        ImageView check = (ImageView) view.findViewById(R.id.selected);
                        if (check != null) {
                            String pid = Long.toString(playerid);
                            //myButton.setAnimation(animFadeOut)
                            if (check.getVisibility() == View.VISIBLE) {
                                check.setVisibility(View.INVISIBLE);
                                players.remove(pid);
                            } else if (check.getVisibility() == View.INVISIBLE) {
                                check.setVisibility(View.VISIBLE);
                                players.add(pid);
                            }
                        }
                    }
                })
        );

        teamName = (EditText) findViewById(R.id.teamName);
        createBtn = (Button) findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tn = teamName.getText().toString();
                if(tn.isEmpty())
                    Toast.makeText(TeamNewActivity.this, "This team will need a name", Toast.LENGTH_SHORT).show();
                else if(players.size() < 1)
                    Toast.makeText(TeamNewActivity.this, "At least one player must be on a team", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(TeamNewActivity.this, "Team created", Toast.LENGTH_SHORT).show();
                    if(dbHelper != null) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(ScorchContract.Teams.COLUMN_NAME, tn);
                        values.put(ScorchContract.Teams.COLUMN_CREATED, new Date().toString());
                        long id = db.insert(ScorchContract.Teams.TABLE_NAME, "null", values);

                        if(id > 0) {
                            for(String pid : players) {
                                values = new ContentValues();
                                values.put(ScorchContract.TeamPlayers.COLUMN_PLAYER, pid);
                                values.put(ScorchContract.TeamPlayers.COLUMN_TEAM, id);
                                db.insert(ScorchContract.TeamPlayers.TABLE_NAME, "null", values);
                            }
                        }
                    }
                }
            }
        });
        // Inflate the layout for this fragment
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team_new, menu);
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
