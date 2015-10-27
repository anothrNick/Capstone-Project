package com.dev.nick.scorch.games;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nick.scorch.MainActivity;
import com.dev.nick.scorch.R;

public class GameFragment extends Fragment {

    public static final String TAG = GameFragment.class.getSimpleName();
    public static String GAME_TYPE = "com.dev.nick.scorch.GAME_TYPE";

    private FloatingActionButton newGameBtn;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // Temporary solution to titles not appearing
        if(mViewPager != null) {
            mViewPager.setCurrentItem(1);
            mViewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewPager.setCurrentItem(0);
                }
            }, 100);
        }
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
        mViewPager.setAdapter(mSectionsPagerAdapter);

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

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new GameListFragment();
            Bundle args = new Bundle();
            args.putInt(GameListFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Current";
                case 1:
                    return "Tournament";
                case 2:
                    return "Game Over";
            }
            return "Tab What";
        }

    }
}
