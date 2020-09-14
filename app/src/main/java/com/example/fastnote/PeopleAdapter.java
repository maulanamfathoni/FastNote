package com.example.fastnote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<People> jobList;


    public PeopleAdapter(Context context, ArrayList<People> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_people, parent, false);
        return new ViewHolder(v);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView mTextNama;
        public TextView mTextPosition;
        LinearLayout linear;
        public ImageView imgPeople;

        ViewHolder(View v) {
            super(v);
            mTextNama = v.findViewById(R.id.tv_name);
            mTextPosition = v.findViewById(R.id.tv_position);
            imgPeople = v.findViewById(R.id.img_people);
            linear = v.findViewById(R.id.linear_row);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final People job = jobList.get(position);
        holder.mTextNama.setText(job.getNama());
        //String gender = job.getPekerjaan();
        holder.mTextPosition.setText(job.getPekerjaan());
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPeople.class);
                intent.putExtra("nama", job.getNama());
                intent.putExtra("pekerjaan", job.getPekerjaan());
                intent.putExtra("jk", job.getJk());
                context.startActivity(intent);

            }
        });

        if (job.getJk().equals("Male"))
            holder.imgPeople.setBackgroundResource(R.drawable.boy);
        else
            holder.imgPeople.setBackgroundResource(R.drawable.gurl);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }



}
