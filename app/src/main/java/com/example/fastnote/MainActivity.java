package com.example.fastnote;


import android.app.Dialog;

import android.content.res.Configuration;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<People> peopleList = new ArrayList<>();
    private PeopleAdapter mAdapter;

    //on Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildRV();


        FloatingActionButton fb_add = findViewById(R.id.fab_add);
        fb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    private  void buildRV() {

        mRecyclerView = findViewById(R.id.rv_data);
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
        mAdapter = new PeopleAdapter(this, peopleList);
        mRecyclerView.setAdapter(mAdapter);

        int swipeDirs;
        if(gridColumnCount > 1){
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                swipeDirs) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(peopleList, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                peopleList.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

    //onclick floating button
    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add);
        final TextInputEditText tv_name = dialog.findViewById(R.id.tv_name);
        final TextInputEditText tv_pekerjaan = dialog.findViewById(R.id.tv_pekerjaan);
        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        final Spinner sp_gender = dialog.findViewById(R.id.sp_gender);

        populateGender(sp_gender);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_name.getText().toString().isEmpty()) {
                    tv_name.setError("Nama Belum Di Isi !");
                    return;
                }

                if (tv_pekerjaan.getText().toString().isEmpty()) {
                    tv_pekerjaan.setError("Pekerjaan Belum Di Isi !");
                    return;
                }

                People data = new People (
                        tv_name.getText().toString(),
                        tv_pekerjaan.getText().toString(),
                        sp_gender.getSelectedItem().toString()
                );

                peopleList.add(data);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Spinner

    private void populateGender(Spinner sp_gender) {
        ArrayList<String> gender = new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        sp_gender.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, gender));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }




}