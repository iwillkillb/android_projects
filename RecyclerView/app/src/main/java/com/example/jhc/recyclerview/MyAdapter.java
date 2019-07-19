package com.example.jhc.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by JHC on 2017-12-01.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public ArrayList<PaintTitle> mDataset;

    public MyAdapter(ArrayList<PaintTitle> myDataset){
        mDataset = myDataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewitem, null);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.imageView.setImageResource(mDataset.get(position%2).imagetId);
        holder.tv1.setText(mDataset.get(position%2).title);
    }

    @Override
    public int getItemCount(){
        return 100;
    }
}
