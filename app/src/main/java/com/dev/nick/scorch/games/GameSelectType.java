package com.dev.nick.scorch.games;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.dev.nick.scorch.R;

public class GameSelectType extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private Button btnNext;
    private Button btnCancel;

    private RadioButton radioTeam;
    private RadioButton radioPlayer;

    public static GameSelectType newInstance() {
        GameSelectType fragment = new GameSelectType();
        return fragment;
    }

    public GameSelectType() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_select_type_fragment, container, false);

        btnNext = (Button) v.findViewById(R.id.nextBtn);
        btnCancel = (Button) v.findViewById(R.id.cancelBtn);

        radioTeam = (RadioButton) v.findViewById(R.id.radio_teams);
        radioPlayer = (RadioButton) v.findViewById(R.id.radio_players);

        btnNext.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        radioTeam.setOnClickListener(this);
        radioPlayer.setOnClickListener(this);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof Activity)
                mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getName()
                    + " must implement OnSelectTypeInteractionListener");
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
                mListener.onCancel();
                Log.d("GameSelectType", "Cancel btn pressed");
                break;
            case R.id.nextBtn:
                if(mListener != null) {
                    mListener.onNext();
                }
                break;
            case R.id.radio_players:
                if(mListener != null) {
                    mListener.onSelect(0);
                }
                break;
            case R.id.radio_teams:
                if(mListener != null) {
                    mListener.onSelect(1);
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
        void onNext();
        void onSelect(int typ);
        void onCancel();
    }

}
