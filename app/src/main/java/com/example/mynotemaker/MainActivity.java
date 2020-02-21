package com.example.mynotemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton btnAdd;
    RecyclerView rv;

    ArrayList<String> list=new ArrayList<>();
    MyDbHelper dbHelper;
    MyAdapter a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.rv);
        btnAdd=findViewById(R.id.btnAdd);
        dbHelper=new MyDbHelper(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        list.addAll(dbHelper.getList());
        a=new MyAdapter(this, list);
        rv.setAdapter(a);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("fileName","new");
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshRV();
    }

    public void refreshRV(){
        list.clear();
        list.addAll(dbHelper.getList());
        a.notifyDataSetChanged();
    }
}
