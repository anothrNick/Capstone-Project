package com.dev.nick.scorch.players;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.games.GameFragment;
import com.dev.nick.scorch.games.GameListFragment;
import com.dev.nick.scorch.model.Player;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetailActivity extends AppCompatActivity {

    public static String PLAYER_ID = "com.dev.nick.scorch.PLAYER_ID";

    private String TAG = "PlayerDetailActivity";
    private TextView playerName;
    //private TextView playerPosition;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView playerIcon;
    private Player mPlayer;

    private long pid;

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_detail_activity);

        playerIcon = (ImageView) findViewById(R.id.player_icon);
        playerName = (TextView) findViewById(R.id.player_name);
        //playerPosition = (TextView) findViewById(R.id.player_position);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pid = extras.getLong(PLAYER_ID);
            String value = Long.toString(pid);

            if(value != null && !value.isEmpty()){

                mPlayer = Player.findById(Player.class, pid);

                if(mPlayer != null) {

                    playerName.setText(mPlayer.name);
                    String imageUri = mPlayer.avatar;

                    if (imageUri != null && !imageUri.isEmpty()) {
                        Log.d(TAG, imageUri);
                        try {
                            Uri selectedImage = Uri.parse(imageUri);
                            final int takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            // Check for the freshest data.
                            getContentResolver().takePersistableUriPermission(selectedImage, takeFlags);

                            Picasso.with(this).load(selectedImage).into(playerIcon);
                        } catch (Exception e) {
                            Log.w(TAG, e.getMessage());
                        }
                    }
                }
            }
        }

        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        playerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                photoPickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 100:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    // Check for the freshest data.
                    try {
                        getContentResolver().takePersistableUriPermission(selectedImage, takeFlags);
                        // update player image
                        if(mPlayer != null) {
                            mPlayer.avatar = selectedImage.toString();
                            mPlayer.save();
                        }

                        //Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(getRealPathFromURI(selectedImageURI))),
                        //        64, 64);
                        //playerIcon.setImageURI(selectedImage);
                        Picasso.with(this).load(selectedImage).into(playerIcon);
                    }
                    catch(Exception e) {
                        Log.w(TAG, e.getMessage());
                    }
                }
                else{
                    Log.d(TAG, Integer.toString(resultCode));
                }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        GameListFragment glf = new GameListFragment();
        Bundle bun = new Bundle();
        bun.putInt(GameFragment.GAME_TYPE, 0);
        bun.putLong(PLAYER_ID, pid);
        glf.setArguments(bun);

        adapter.addFrag(new StatsFrag(), "Stats");
        adapter.addFrag(glf, "Games");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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

    public static class GamesFrag extends Fragment {
        public GamesFrag() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.player_game_list, container, false);

            return view;
        }
    }


    /**
     * stats graph
     */
    public static class StatsFrag extends Fragment {

        private LineChart mChart;

        public StatsFrag() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.player_detail_fragment, container, false);

            mChart = (LineChart) view.findViewById(R.id.chart);

            dummyChart();

            return view;
        }

        private void dummyChart()
        {
            //mChart.setOnChartGestureListener(this);
            //mChart.setOnChartValueSelectedListener(this);
            mChart.setDrawGridBackground(false);

            // no description text
            mChart.setDescription("");
            mChart.setNoDataTextDescription("You need to provide data for the chart.");

            // enable value highlighting
            mChart.setHighlightEnabled(true);

            // enable touch gestures
            mChart.setTouchEnabled(true);

            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            // mChart.setScaleXEnabled(true);
            // mChart.setScaleYEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(true);

            mChart.setDoubleTapToZoomEnabled(false);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

            mChart.getAxisRight().setEnabled(false);

            setData(6, 20);

            mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

            // get the legend (only possible after setting data)
            Legend l = mChart.getLegend();

            l.setForm(Legend.LegendForm.LINE);
        }

        private void setData(int count, float range) {

            ArrayList<String> xVals = new ArrayList<String>();
            for (int i = 0; i < count; i++) {
                xVals.add((i) + "");
            }

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            for (int i = 0; i < count; i++) {

                float mult = (range + 1);
                float val = (float) (Math.random() * mult) + 3;// + (float)
                // ((mult *
                // 0.1) / 10);
                yVals.add(new Entry(val, i));
            }

            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleSize(8f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(9f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.BLACK);
            set1.setDrawFilled(true);

            ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            // set data
            mChart.setData(data);
        }
    }


}
