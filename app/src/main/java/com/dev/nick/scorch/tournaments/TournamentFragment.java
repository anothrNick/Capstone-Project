package com.dev.nick.scorch.tournaments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;
import com.dev.nick.scorch.canvas.TournamentCanvas;

import java.util.ArrayList;

public class TournamentFragment extends Fragment {

    public static final String TAG = TournamentFragment.class.getSimpleName();
    public static ArrayList<String> offSets = new ArrayList<String>();

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
        mSectionsPagerAdapter.round_count = 4;

        offSets.add("0");
        offSets.add("0");
        offSets.add("0");
        offSets.add("0");

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
        public int round_count = 0;
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
            return round_count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Round " + (position + 1);
        }

    }

    public static class TabbedContentFragment extends Fragment {

        public static final String ARG_ROUND_NUMBER = "round_number";

        private TournamentCanvas mCanvas;
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

        private int round = 0;
        private int games = 0;

        public TabbedContentFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                round = getArguments().getInt(ARG_ROUND_NUMBER);

                // testing ui
                if(round == 1) games = 6;
                else if(round == 2) games = 3;
                else if(round == 3) games = 2;
                else if(round == 4) games = 1;
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tournament_page,
                    container, false);

            mCanvas = new TournamentCanvas(getContext(), games);
            mCanvas.requestFocus();
            LinearLayout background = (LinearLayout) rootView.findViewById(R.id.canvas);
            background.addView(mCanvas);

            mAdapter = new TournamentGameListAdapter(games);

            // Set the adapter
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.game_list);
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.setAdapter(mAdapter);
            //mCanvas.yOffset = mRecyclerView.computeVerticalScrollOffset();
            //mCanvas.scrolledOffset = Integer.parseInt(offSets.get(round-1));
            //mCanvas.invalidate();

            Log.d(TAG, "round " + round + ": yOffset " + mCanvas.yOffset + ", scrolled offset " + mCanvas.scrolledOffset);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    mCanvas.yOffset += dy;
                    //offSets.set(round-1, Integer.toString(mCanvas.yOffset));
                    mCanvas.invalidate();
                }
            });

            return rootView;
        }
    }
}
