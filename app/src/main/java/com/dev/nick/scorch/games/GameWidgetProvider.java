package com.dev.nick.scorch.games;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.dev.nick.scorch.R;
import com.dev.nick.scorch.model.Game;

import java.util.List;

/**
 * Created by Nick on 11/25/2015.
 */
public class GameWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.game_widget);

        pushWidgetUpdate(context, remoteViews);
    }
    @Override
    public void onReceive(final Context context, Intent intent) {

        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.game_widget);
            pushWidgetUpdate(context, remoteViews);
        }

        super.onReceive(context, intent);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {

        List<Game> gameList = Game.findWithQuery(Game.class, "select * from game where complete = ?", "0");
        if(gameList != null && gameList.size() > 0) {
            gameList.get(0).loadTeamList();
            if(gameList.get(0).gameTeamList != null && gameList.get(0).gameTeamList.size() > 0) {
                if (gameList.get(0).gameTeamList.get(0).type == Game.PLAYERS) {
                    remoteViews.setTextViewText(R.id.team_one_widget, gameList.get(0).gameTeamList.get(0).player.name);
                    remoteViews.setTextViewText(R.id.team_two, gameList.get(0).gameTeamList.get(1).player.name);

                } else {
                    remoteViews.setTextViewText(R.id.team_one, gameList.get(0).gameTeamList.get(0).team.name);
                    remoteViews.setTextViewText(R.id.team_two, gameList.get(0).gameTeamList.get(1).team.name);
                }

                remoteViews.setTextViewText(R.id.team_one_score, Integer.toString(gameList.get(0).gameTeamList.get(0).score));
                remoteViews.setTextViewText(R.id.team_two_score, Integer.toString(gameList.get(0).gameTeamList.get(1).score));
            }
            else {
                remoteViews.setTextViewText(R.id.team_one, "Empty teams");
            }
        }
        else {
            remoteViews.setTextViewText(R.id.team_one, "Failed to load");
        }

        ComponentName myWidget = new ComponentName(context, GameWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}
