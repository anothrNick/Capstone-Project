package com.dev.nick.scorch.games;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dev.nick.scorch.R;

public class GameDetailActivity extends AppCompatActivity {

    private Button finishGameBtn;

    private ImageButton scoreOneUp;
    private ImageButton scoreOneDown;
    private ImageButton scoreTwoUp;
    private ImageButton scoreTwoDown;

    private TextView scoreOne;
    private TextView scoreTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail_activity);

        finishGameBtn = (Button) findViewById(R.id.finishBtn);

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
                scoreOne.setText(Integer.toString(val));
            }
        });

        scoreOneUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(scoreOne.getText().toString());
                val++;
                scoreOne.setText(Integer.toString(val));
            }
        });

        scoreTwoDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(scoreTwo.getText().toString());
                val--;
                scoreTwo.setText(Integer.toString(val));
            }
        });

        scoreTwoUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(scoreTwo.getText().toString());
                val++;
                scoreTwo.setText(Integer.toString(val));
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
