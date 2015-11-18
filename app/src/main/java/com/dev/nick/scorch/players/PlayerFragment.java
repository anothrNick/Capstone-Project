package com.dev.nick.scorch.players;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;
import com.dev.nick.scorch.RecyclerItemClickListener;
import com.dev.nick.scorch.model.Player;

import java.util.Date;
import java.util.List;

public class PlayerFragment extends Fragment{

    public static String TAG = PlayerFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private PlayerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new PlayerListAdapter(getContext(), Player.listAll(Player.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        // Set the adapter
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        long playerid = mAdapter.getItemId(position);
                        Intent intent = new Intent(getActivity(), PlayerDetailActivity.class);
                        intent.putExtra(PlayerDetailActivity.PLAYER_ID, playerid);
                        startActivity(intent);
                    }
                })
        );

        final FloatingActionButton playerBtn = (FloatingActionButton) view.findViewById(R.id.newPlayer);
        playerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View promptView = layoutInflater.inflate(R.layout.player_new, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setTitle("New Player");
                final EditText editText = (EditText) promptView.findViewById(R.id.newPlayerName);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //resultText.setText("Hello, " + editText.getText());
                                String playerName = editText.getText().toString();
                                if(!playerName.isEmpty()) {
                                    String name = editText.getText().toString();
                                    Toast.makeText(getActivity(), "Added " + name, Toast.LENGTH_SHORT).show();
                                    Player mPlayer = new Player();
                                    mPlayer.name = name;
                                    mPlayer.created = new Date().toString();
                                    mPlayer.save();

                                    mAdapter.updatePlayerList(Player.listAll(Player.class));
                                    mAdapter.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(getActivity(), "Player name cannot be empty", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onSectionAttached(MainActivity.PLAYERS);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).onSectionAttached(MainActivity.PLAYERS);
    }
}
