package com.example.fastnote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPeople extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_people);
        getExtrasIntent();

    }
    private void getExtrasIntent() {

        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_pekerjaan = findViewById(R.id.tv_pekerjaan);
        ImageView gender = findViewById(R.id.img_people);

        String jk = getIntent().getStringExtra("jk");


        tv_name.setText(getIntent().getStringExtra("nama"));
        tv_pekerjaan.setText(getIntent().getStringExtra("pekerjaan"));


        if (jk.equals("Male"))
            gender.setBackgroundResource(R.drawable.boy);
        else
            gender.setBackgroundResource(R.drawable.gurl);
    }

}

