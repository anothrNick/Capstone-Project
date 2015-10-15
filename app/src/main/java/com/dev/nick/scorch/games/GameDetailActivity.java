package com.dev.nick.scorch.games;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;

public class GameDetailActivity extends AppCompatActivity {

    public static String GAME_ID = "com.dev.nick.scorch.GAME_ID";
    public static String GAME = "com.dev.nick.scorch.GAME";
    private ScorchDbHelper dbHelper;

    private Button finishGameBtn;

    private ImageButton scoreOneUp;
    private ImageButton scoreOneDown;
    private ImageButton scoreTwoUp;
    private ImageButton scoreTwoDown;

    private TextView scoreOne;
    private TextView scoreTwo;

    private TextView teamOne;
    private TextView teamTwo;

    private GameBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail_activity);

        dbHelper = new ScorchDbHelper(this);

        finishGameBtn = (Button) findViewById(R.id.finishBtn);

        teamOne = (TextView) findViewById(R.id.team_one);
        teamTwo = (TextView) findViewById(R.id.team_two);

        scoreOne = (TextView) findViewById(R.id.team_one_score);
        scoreTwo = (TextView) findViewById(R.id.team_two_score);

        scoreOneDown = (ImageButton) findViewById(R.id.score_one_down);
        scoreOneUp = (ImageButton) findViewById(R.id.score_one_up);
        scoreTwoDown = (ImageButton) findViewById(R.id.score_two_down);
        scoreTwoUp = (ImageButton) findViewById(R.id.score_two_up);

        scoreOneDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(scoreOne.getText().toString());
                val--;
                updateScoreOne(val);
            }
        });

        scoreOneUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(scoreOne.getText().toString());
                val++;
                updateScoreOne(val);
            }
        });

        scoreTwoDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(scoreTwo.getText().toString());
                val--;
                updateScoreTwo(val);
            }
        });

        scoreTwoUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(scoreTwo.getText().toString());
                val++;
                updateScoreTwo(val);
            }
        });

        finishGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                // complete game
                finish();
            }
        });

        Intent i = getIntent();

        if(i != null) {
            bean = i.getParcelableExtra(GameDetailActivity.GAME);
            if(bean != null) {
                teamOne.setText(bean.teamOne);
                teamTwo.setText(bean.teamTwo);

                scoreOne.setText(Integer.toString(bean.teamOneScore));
                scoreTwo.setText(Integer.toString(bean.teamTwoScore));
            }
        }
    }

    public void updateScoreOne(int val) {
        scoreOne.setText(Integer.toString(val));

        if(bean != null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put(ScorchContract.GameTeams.COLUMN_SCORE, val);

            db.update(ScorchContract.GameTeams.TABLE_NAME, newValues, "id=" + bean.teamOneId, null);
            db.close();

            bean.teamOneScore = val;
        }
    }

    public void updateScoreTwo(int val) {
        scoreTwo.setText(Integer.toString(val));

        if(bean != null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put(ScorchContract.GameTeams.COLUMN_SCORE, val);

            db.update(ScorchContract.GameTeams.TABLE_NAME, newValues, "id=" + bean.teamTwoId, null);
            db.close();

            bean.teamTwoScore = val;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
