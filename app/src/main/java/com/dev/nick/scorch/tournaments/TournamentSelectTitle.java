package com.dev.nick.scorch.tournaments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.nick.scorch.R;

public class TournamentSelectTitle extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private Button btnCancel;
    private Button btnNext;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_name_fragment, container, false);

//        teamName = (EditText) view.findViewById(R.id.teamName);
        btnCancel = (Button) view.findViewById(R.id.cancelBtn);
        btnNext = (Button) view.findViewById(R.id.nextBtn);

        btnCancel.setOnClickListener(this);
        btnNext.setOnClickListener(this);
//
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
//
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TeamNewActivity.teamName = teamName.getText().toString();
//
//                if(TeamNewActivity.teamName.isEmpty()) {
//                    Toast.makeText(getActivity(), "This team will need a name", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    ((TeamNewActivity) getActivity()).incPager();
//                    View view = getActivity().getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//                }
//            }
//        });

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
                mListener.onCancel();
                break;
            case R.id.nextBtn:
                // TODO check for value
                if(mListener != null) {
                    mListener.onNext(1);
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void updateTitle(String title);
        void onCancel();
        void onNext(int item);
    }

}
