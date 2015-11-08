package com.dev.nick.scorch.tournaments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dev.nick.scorch.R;

public class TournamentSelectTitle extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private Button btnCancel;
    private Button btnNext;
    private EditText tourneyName;

    public static TournamentSelectTitle newInstance() {
        TournamentSelectTitle fragment = new TournamentSelectTitle();
        return fragment;
    }

    public TournamentSelectTitle() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mListener != null && tourneyName != null) {
            tourneyName.setText(mListener.getTournamentTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tournament_select_title_fragment, container, false);

        tourneyName = (EditText) view.findViewById(R.id.tourneyName);
        btnCancel = (Button) view.findViewById(R.id.cancelBtn);
        btnNext = (Button) view.findViewById(R.id.nextBtn);

        btnCancel.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
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
            case R.id.cancelBtn:
                if(mListener != null) {
                    mListener.onCancel();
                }
                break;
            case R.id.nextBtn:
                if(mListener != null) {
                    mListener.updateTitle(tourneyName.getText().toString());
                    mListener.onNext(1);
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void updateTitle(String title);
        void onCancel();
        void onNext(int item);
        String getTournamentTitle();
    }

}
