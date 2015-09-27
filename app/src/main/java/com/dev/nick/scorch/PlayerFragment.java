package com.dev.nick.scorch;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;

import java.util.Date;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PlayerFragment extends Fragment{

    private OnFragmentInteractionListener mListener;

    private ScorchDbHelper dbHelper;
    private RecyclerView mRecyclerView;
    private PlayerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static String[] player_projection = {
            ScorchContract.Players.COLUMN_NAME,
            ScorchContract.Players.COLUMN_CREATED,
            ScorchContract.Players.COLUMN_ID
    };

    public static String player_sortOrder = ScorchContract.Players.COLUMN_CREATED + " DESC";

    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
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

        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHelper = new ScorchDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                ScorchContract.Players.TABLE_NAME,
                player_projection,
                null,
                null,
                null,
                null,
                player_sortOrder
        );
        mAdapter = new PlayerListAdapter(getContext(), cursor);
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
                                    if(dbHelper != null) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put(ScorchContract.Players.COLUMN_NAME, name);
                                        values.put(ScorchContract.Players.COLUMN_CREATED, new Date().toString());
                                        db.insert(ScorchContract.Players.TABLE_NAME, "null", values);
                                        //mAdapter.notifyDataSetChanged();
                                        Cursor cursor = db.query(
                                                ScorchContract.Players.TABLE_NAME,
                                                player_projection,
                                                null,
                                                null,
                                                null,
                                                null,
                                                player_sortOrder
                                        );
                                        mAdapter.changeCursor(cursor);
                                        //mRecyclerView.swapAdapter(mAdapter, false);
                                    }
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.PLAYERS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
