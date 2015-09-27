package com.dev.nick.scorch;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.nick.scorch.dao.ScorchContract;

/**
 * Created by Nick on 9/15/2015.
 */
public class TeamListAdapter extends CursorRecyclerViewAdapter<TeamListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView teamName;

        public ViewHolder(View itemView) {
            super(itemView);
            teamName = (TextView)itemView.findViewById(R.id.team_name);
        }
    }

    public TeamListAdapter(Context context,Cursor cursor){
        super(context,cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.team_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.teamName.setText(cursor.getString(cursor.getColumnIndex(ScorchContract.Teams.COLUMN_NAME)));
    }

    @Override
    public int getItemCount() {
        if(getCursor() != null)
            return getCursor().getCount();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}
