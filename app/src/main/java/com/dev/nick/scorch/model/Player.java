package com.dev.nick.scorch.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.dao.ScorchDbHelper;

/**
 * Created by Nick on 11/14/2015.
 */
public class Player {

    private String id, name, rank, created, avatar;
    private Context mContext;
    private boolean isLoaded = false;

    public Player() {
        clear();
    }

    public Player(Long pid, Context context) {
        mContext = context;
        init(pid);
    }

    public Player(Cursor pCursor) {
        init(pCursor);
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getRank() { return this.rank; }
    public String getCreated() { return this.created; }
    public String getAvatar() { return this.avatar; }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    private void init(Long pid) {
        String value = Long.toString(pid);

        if(value != null && !value.isEmpty()) {

            ScorchDbHelper dbHelper = new ScorchDbHelper(mContext);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String where = "id = ?";
            String[] whereArgs = new String[]{
                    value
            };

            Cursor cursor = db.query(
                    ScorchContract.Players.TABLE_NAME,
                    ScorchContract.Players.projection,
                    where,
                    whereArgs,
                    null,
                    null,
                    ScorchContract.Players.sortOrder
            );

            init(cursor);

            db.close();
        }
    }

    private void init(Cursor pCursor) {
        if(pCursor.moveToFirst()) {
            isLoaded = true;
            this.id = pCursor.getString(pCursor.getColumnIndex(ScorchContract.Players.COLUMN_ID));
            this.name = pCursor.getString(pCursor.getColumnIndex(ScorchContract.Players.COLUMN_NAME));
            this.rank = pCursor.getString(pCursor.getColumnIndex(ScorchContract.Players.COLUMN_RANK));
            this.created = pCursor.getString(pCursor.getColumnIndex(ScorchContract.Players.COLUMN_CREATED));
            this.avatar = pCursor.getString(pCursor.getColumnIndex(ScorchContract.Players.COLUMN_AVATAR));
        }
    }

    public void clear() {
        this.id = this.name = this.rank = this.created = this.avatar;
    }

}
