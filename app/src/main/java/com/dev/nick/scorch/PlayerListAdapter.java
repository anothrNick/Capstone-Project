package com.dev.nick.scorch;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.nick.scorch.dao.ScorchContract;

/**
 * Created by Nick on 9/12/2015.
 */
public class PlayerListAdapter extends CursorRecyclerViewAdapter<PlayerListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView textName;
        TextView textJoined;
        ImageView imageIcon;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.player_name);
            textJoined = (TextView) itemView.findViewById(R.id.player_joined);
            imageIcon = (ImageView) itemView.findViewById(R.id.player_icon);
        }

    }

    public PlayerListAdapter(Context context,Cursor cursor){
        super(context,cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(v,viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.textName.setText(cursor.getString(cursor.getColumnIndex(ScorchContract.Players.COLUMN_NAME)));
        holder.textJoined.setText(cursor.getString(cursor.getColumnIndex(ScorchContract.Players.COLUMN_CREATED)));
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
