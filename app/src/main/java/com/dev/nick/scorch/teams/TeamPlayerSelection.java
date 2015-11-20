package com.dev.nick.scorch.teams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.RecyclerItemClickListener;
import com.dev.nick.scorch.model.Player;
import com.dev.nick.scorch.model.Team;
import com.dev.nick.scorch.model.TeamPlayer;
import com.dev.nick.scorch.players.PlayerListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamPlayerSelection extends Fragment {

    private RecyclerView mRecyclerView;
    private PlayerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> players;
    private Button createBtn;
    private Button backBtn;

    public static TeamPlayerSelection newInstance() {
        TeamPlayerSelection fragment = new TeamPlayerSelection();
        return fragment;
    }

    public TeamPlayerSelection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_player_selection_fragment, container, false);
        players = new ArrayList<>();

        List<Player> lstPlayers = Player.listAll(Player.class);
        mAdapter = new PlayerListAdapter(getContext(), lstPlayers);

        // Set the adapter
        mRecyclerView = (RecyclerView) view.findViewById(R.id.players);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
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

        backBtn = (Button) view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TeamNewActivity) getActivity()).decPager();
            }
        });

        createBtn = (Button) view.findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tn = TeamNewActivity.teamName;
                if (tn.isEmpty()) {
                    Toast.makeText(getActivity(), "This team will need a name", Toast.LENGTH_SHORT).show();
                    ((TeamNewActivity) getActivity()).decPager();
                }
                else if (players.size() < 1)
                    Toast.makeText(getActivity(), "At least one player must be on a team", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getActivity(), "Team created", Toast.LENGTH_SHORT).show();
                    //if (dbHelper != null) {
                        //SQLiteDatabase db = dbHelper.getWritableDatabase();

                        Team team = new Team();
                        team.name = tn;
                        team.created = new Date().toString();
                        team.save();
                        long id = team.getId();

                        if (id > 0) {

                            for (String pid : players) {
                                TeamPlayer teamPlayer = new TeamPlayer();
                                teamPlayer.player = Player.findById(Player.class, Long.parseLong(pid));
                                teamPlayer.team = team;
                                teamPlayer.save();
                            }
                        }
                    //}
                    getActivity().finish();
                }
            }
        });

        return view;
    }
}
