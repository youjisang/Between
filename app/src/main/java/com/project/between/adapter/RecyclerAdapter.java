package com.project.between.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.between.R;
import com.project.between.domain.MyMessage;
import com.project.between.util.ConstantUtil;
import com.project.between.util.PreferenceUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-06.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyAdapter> {
    ArrayList<MyMessage> list = new ArrayList<>();
    final static int MY_MESSAGE = 0;
    final static int YOUR_MESSAGE = 1;
    Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void refreshData(ArrayList<MyMessage> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if ((list.get(position).user_num).equals(PreferenceUtil.getStringValue(context, ConstantUtil.MY_NUMBER))) {
            return MY_MESSAGE;
        } else {
            return YOUR_MESSAGE;
        }
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MY_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_chat_box, parent, false);
            return new MyAdapter(view);
        } else if (viewType == YOUR_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_chat_box, parent, false);
            return new MyAdapter(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {
        holder.textViewMyChat.setText(list.get(position).message);
        holder.textViewTime.setText(list.get(position).messageTime);
        holder.profileUrl = list.get(position).profileUrl;
        URL url = null;
        try {
            url = new URL(holder.profileUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Glide.with(context).load(url).into(holder.imageProfile);


        holder.textViewDivideLine.setText("------------------------ " + list.get(position).messageDate + "-----------------------");
        if(position>=1 && list.get(position).messageDate.equals(list.get(position-1).messageDate)){
            holder.textViewDivideLine.setVisibility(View.GONE);
        }else{
            holder.textViewDivideLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyAdapter extends RecyclerView.ViewHolder {
        TextView textViewMyChat;
        TextView textViewTime;
        TextView textViewDivideLine;
        ImageView imageProfile;
        String profileUrl;


        public MyAdapter(View itemView) {
            super(itemView);

            textViewMyChat = itemView.findViewById(R.id.textViewMyChat);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewDivideLine = itemView.findViewById(R.id.textViewDivideLine);
            imageProfile = itemView.findViewById(R.id.imageProfile);


        }
    }
}
