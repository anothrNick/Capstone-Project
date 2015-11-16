package com.dev.nick.scorch.games;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {

    public static final String TAG = GameFragment.class.getSimpleName();
    public static String GAME_TYPE = "com.dev.nick.scorch.GAME_TYPE";

    private FloatingActionButton newGameBtn;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_fragment, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);

        mSectionsPagerAdapter.addFrag(new GameListFragment(), "Open");
        mSectionsPagerAdapter.addFrag(new GameListFragment(), "Finished");
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) v.findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        newGameBtn = (FloatingActionButton) v.findViewById(R.id.newGame);

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameNewActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onSectionAttached(MainActivity.GAMES);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).onSectionAttached(MainActivity.GAMES);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
