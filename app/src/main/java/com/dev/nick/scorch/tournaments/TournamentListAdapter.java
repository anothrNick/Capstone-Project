package com.dev.nick.scorch.tournaments;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.nick.scorch.CursorRecyclerViewAdapter;
import com.dev.nick.scorch.R;

/**
 * Created by Nick on 10/27/2015.
 */
public class TournamentListAdapter extends CursorRecyclerViewAdapter<TournamentListAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textCreated;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.tourney_title);
            textCreated = (TextView) itemView.findViewById(R.id.tourney_created);
        }

    }

    public TournamentListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_list_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(v, viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {

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
