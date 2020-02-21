package com.example.mynotemaker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private Context context;
    private ArrayList<String> list;

    public MyAdapter(Context context, ArrayList<String> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        final String s=list.get(position);

        holder.tvFileName.setText(s);

        holder.tvFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, EditActivity.class);
                i.putExtra("fileName", s);
                context.startActivity(i);
            }
        });

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbHelper dbHelper=new MyDbHelper(context);
                dbHelper.delete(s);

                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());

                context.deleteFile(s);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tvFileName;
        ImageButton btnDel;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvFileName=itemView.findViewById(R.id.tvFileName);
            btnDel=itemView.findViewById(R.id.btnDel);
        }
    }
}
