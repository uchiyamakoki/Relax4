package relax.sn.com.relax4.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import relax.sn.com.relax4.R;
import relax.sn.com.relax4.entity.TimeInfo;

/**
 * Created by John on 2018/5/12.
 */
public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<TimeInfo> timeInfoList;

    public TimeAdapter(Context mContext, List<TimeInfo> timeInfoList, LayoutInflater mLayoutInflater) {
        this.mContext = mContext;
        this.timeInfoList = timeInfoList;
        this.mLayoutInflater = mLayoutInflater;
    }

    public TimeAdapter(Context mContext, List<TimeInfo> timeInfoList) {
        this.mContext = mContext;
        this.timeInfoList = timeInfoList;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeViewHolder(LayoutInflater.from(parent.getContext()),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.mTvContent.setText(timeInfoList.get(position).getContent());
        String time="寄往"+timeInfoList.get(position).getEnd_time()+"年";
        holder.mTvTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return timeInfoList.size();
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder{
        TextView mTvContent;
        TextView mTvTime;
        LinearLayout mL2;
        public TimeViewHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.item_rv_time,parent,false));
            mTvContent=(TextView)itemView.findViewById(R.id.main_tv_content2);
            mTvTime=(TextView)itemView.findViewById(R.id.main_tv_comedate);
            mL2=(LinearLayout)itemView.findViewById(R.id.item_ll_control2);
        }
    }
}
