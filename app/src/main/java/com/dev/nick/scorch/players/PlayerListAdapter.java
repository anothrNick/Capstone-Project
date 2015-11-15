package com.dev.nick.scorch.players;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.nick.scorch.CursorRecyclerViewAdapter;
import com.dev.nick.scorch.R;
import com.dev.nick.scorch.dao.ScorchContract;
import com.dev.nick.scorch.model.Player;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nick on 9/12/2015.
 */
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {

    SimpleDateFormat prettyFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    List<Player> lstPlayers;
    Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

    public PlayerListAdapter(Context context, List<Player> lstPlayers){
        this.mContext = context;
        this.lstPlayers = lstPlayers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(v,viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Player mPlayer = new Player(cursor);

        Player mPlayer = lstPlayers.get(position);

        holder.textName.setText(mPlayer.name);

        String startDate = mPlayer.created;

        try {
            holder.textJoined.setText(prettyFormat.format(format.parse(startDate)));
        }
        catch(Exception e) {
            Log.w("blah", e.toString());
        }

        String imageUri = mPlayer.avatar;

        if (imageUri != null && !imageUri.isEmpty()) {
            try {
                Uri selectedImage = Uri.parse(imageUri);
                final int takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                // Check for the freshest data.
                mContext.getContentResolver().takePersistableUriPermission(selectedImage, takeFlags);

                Picasso.with(mContext).load(imageUri).into(holder.imageIcon);
                //holder.imageIcon.setImageURI(selectedImage);
            } catch (Exception e) {
                Log.w("PlayerListAdapter", e.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        if(lstPlayers != null)
            return lstPlayers.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return lstPlayers.get(position).getId();
    }
}
