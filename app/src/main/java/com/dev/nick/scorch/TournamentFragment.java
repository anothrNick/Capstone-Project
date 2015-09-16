package com.dev.nick.scorch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dev.nick.scorch.canvas.TournamentCanvas;

public class TournamentFragment extends Fragment {

    public static final String TAG = GameFragment.class.getSimpleName();

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    public static TournamentFragment newInstance() {
        TournamentFragment fragment = new TournamentFragment();

        return fragment;
    }

    public TournamentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tournament_fragment, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(MainActivity.TOURNAMENTS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new TabbedContentFragment();
            Bundle args = new Bundle();
            args.putInt(TabbedContentFragment.ARG_ROUND_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Round 1";
                case 1:
                    return "Round 2";
                case 2:
                    return "Round 3";
                case 3:
                    return "Round 4";
            }
            return "Tab What";
        }

    }

    public static class TabbedContentFragment extends Fragment {

        public static final String ARG_ROUND_NUMBER = "round_number";

        private TournamentCanvas mCanvas;
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

        private int round = 0;

        public TabbedContentFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                round = getArguments().getInt(ARG_ROUND_NUMBER);

                // testing ui
                if(round == 1) round = 4;
                else if(round == 3 || round == 4) round = 1;
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tournament_page,
                    container, false);

            mCanvas = new TournamentCanvas(getContext(), round);
            mCanvas.requestFocus();
            LinearLayout background = (LinearLayout) rootView.findViewById(R.id.canvas);
            background.addView(mCanvas);

            mAdapter = new TournamentGameListAdapter(round);

            // Set the adapter
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.game_list);
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.setAdapter(mAdapter);

            return rootView;
        }
    }
}
