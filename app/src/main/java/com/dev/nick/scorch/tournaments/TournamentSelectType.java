package com.dev.nick.scorch.tournaments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.dev.nick.scorch.R;

public class TournamentSelectType extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private Button btnNext;
    private Button btnBack;

    private RadioButton radioTeam;
    private RadioButton radioPlayer;

    public static TournamentSelectType newInstance() {
        return new TournamentSelectType();
    }

    public TournamentSelectType() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tournament_select_type_fragment, container, false);

        btnNext = (Button) v.findViewById(R.id.nextBtn);
        btnBack = (Button) v.findViewById(R.id.backBtn);

        radioTeam = (RadioButton) v.findViewById(R.id.radio_teams);
        radioPlayer = (RadioButton) v.findViewById(R.id.radio_players);

        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        radioTeam.setOnClickListener(this);
        radioPlayer.setOnClickListener(this);

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
        switch (id) {
            case R.id.backBtn:
                mListener.onBack(0);
                break;
            case R.id.nextBtn:
                if(mListener != null) {
                    mListener.onNext(2);
                }
                break;
            case R.id.radio_players:
                if(mListener != null) {
                    mListener.onSelect(0);
                    radioPlayer.setTextColor(getResources().getColor(R.color.white));
                    radioTeam.setTextColor(getResources().getColor(R.color.grey));
                }
                break;
            case R.id.radio_teams:
                if(mListener != null) {
                    mListener.onSelect(1);
                    radioTeam.setTextColor(getResources().getColor(R.color.white));
                    radioPlayer.setTextColor(getResources().getColor(R.color.grey));
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onNext(int item);
        void onBack(int item);
        void onSelect(int typ);
    }

}
