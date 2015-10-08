package com.dev.nick.scorch.games;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.nick.scorch.R;

public class GameSelectMembersFragment extends Fragment implements View.OnClickListener {

    private Button btnBack;
    private Button btnStart;

    private OnFragmentInteractionListener mListener;

    public static GameSelectMembersFragment newInstance() {
        GameSelectMembersFragment fragment = new GameSelectMembersFragment();
        return fragment;
    }

    public GameSelectMembersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_select_members_fragment, container, false);

        btnBack = (Button) v.findViewById(R.id.cancelBtn);
        btnStart = (Button) v.findViewById(R.id.nextBtn);

        btnBack.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onClick(View v) {
        final int id = v.getId();
        Log.d("GameSelectType", "something pressed");
        switch (id) {
            case R.id.cancelBtn:
                if(mListener != null) {
                    mListener.onBack();
                }
                break;
            case R.id.nextBtn:
                if(mListener != null) {
                    mListener.onStartGame();
                }
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        public void onStartGame();
        public void onMemberSelected();
        public void onBack();
    }

}
