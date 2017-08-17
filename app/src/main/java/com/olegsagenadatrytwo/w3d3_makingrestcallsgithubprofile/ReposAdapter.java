package com.olegsagenadatrytwo.w3d3_makingrestcallsgithubprofile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.olegsagenadatrytwo.w3d3_makingrestcallsgithubprofile.model.Repo;

import java.util.ArrayList;

/**
 * Created by omcna on 8/16/2017.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {


    private ArrayList<Repo> list = new ArrayList<>();

    public ReposAdapter(ArrayList<Repo> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_repos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvRepoName.setText(list.get(position).getName());
        holder.tvDescription.setText(list.get(position).getDescription());
        holder.tvSize.setText(String.valueOf(list.get(position).getSize()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRepoName;
        private TextView tvDescription;
        private TextView tvSize;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRepoName  = (TextView)  itemView.findViewById(R.id.tvRepoName);
            tvDescription  = (TextView)  itemView.findViewById(R.id.tvDescription);
            tvSize  = (TextView)  itemView.findViewById(R.id.tvSize);

        }
    }
}
