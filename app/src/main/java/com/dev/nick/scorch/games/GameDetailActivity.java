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
import android.widget.Toast;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;
import com.dev.nick.scorch.model.Game;
import com.dev.nick.scorch.model.GameTeam;

public class GameDetailActivity extends AppCompatActivity {

    public static String GAME_ID = "com.dev.nick.scorch.GAME_ID";
    public static String GAME = "com.dev.nick.scorch.GAME";
    //private ScorchDbHelper dbHelper;

    private Button finishGameBtn;
    private Button closeGameBtn;

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

        //dbHelper = new ScorchDbHelper(this);

        finishGameBtn = (Button) findViewById(R.id.finishBtn);
        closeGameBtn = (Button) findViewById(R.id.closeBtn);

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
                Toast.makeText(v.getContext(), "Press and hold to finish game", Toast.LENGTH_SHORT).show();
            }
        });

        finishGameBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Game over! <player> wins!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        });

        closeGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();

        if(i != null) {
            Game game = Game.findById(Game.class, i.getLongExtra(GameDetailActivity.GAME_ID, 0));

            if(game != null) {

                teamOne.setText(game.gameTeamList.get(0).player.name);
                teamTwo.setText(game.gameTeamList.get(1).player.name);

                scoreOne.setText(game.gameTeamList.get(0).score);
                scoreTwo.setText(game.gameTeamList.get(1).score);
            }
        }
    }

    public void updateScoreOne(int val) {
        scoreOne.setText(Integer.toString(val));

        if(bean != null) {
            GameTeam gameTeam = GameTeam.findById(GameTeam.class, bean.teamOneId);
            gameTeam.score = val;
            gameTeam.save();

            bean.teamOneScore = val;
        }
    }

    public void updateScoreTwo(int val) {
        scoreTwo.setText(Integer.toString(val));

        if(bean != null) {
            GameTeam gameTeam = GameTeam.findById(GameTeam.class, bean.teamTwoId);
            gameTeam.score = val;
            gameTeam.save();

            bean.teamTwoScore = val;
        }
    }
}
