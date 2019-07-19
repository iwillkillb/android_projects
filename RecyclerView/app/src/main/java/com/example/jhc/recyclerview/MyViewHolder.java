package com.example.jhc.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JHC on 2017-12-01.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tv1;
    ImageView imageView;

    public MyViewHolder (View itemView){
        super(itemView);

        tv1 = (TextView) itemView.findViewById(R.id.itemtextview);
        imageView = (ImageView) itemView.findViewById(R.id.itemimageview);
    }
}
