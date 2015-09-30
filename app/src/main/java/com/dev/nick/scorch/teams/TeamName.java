package com.dev.nick.scorch.teams;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.nick.scorch.R;

public class TeamName extends Fragment {

    EditText teamName;
    Button cancelBtn;
    Button nextBtn;

    public static TeamName newInstance() {
        TeamName fragment = new TeamName();
        return fragment;
    }

    public TeamName() {
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

        teamName = (EditText) view.findViewById(R.id.teamName);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        nextBtn = (Button) view.findViewById(R.id.nextBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamNewActivity.teamName = teamName.getText().toString();

                if(TeamNewActivity.teamName.isEmpty()) {
                    Toast.makeText(getActivity(), "This team will need a name", Toast.LENGTH_SHORT).show();
                }
                else {
                    ((TeamNewActivity) getActivity()).incPager();
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
