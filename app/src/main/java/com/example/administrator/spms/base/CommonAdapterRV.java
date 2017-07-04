package com.example.administrator.spms.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class CommonAdapterRV<T> extends RecyclerView.Adapter<ViewHolderRV> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private MyOnclick myOnclick;


    public void setMyOnclick(MyOnclick myOnclick){
        this.myOnclick = myOnclick;
    }

    public interface  MyOnclick{
        void onItemClick(View view, int postion);

        void onItemClick(int pos);
    }
    
    
    
    public CommonAdapterRV(Context context, int layoutId, List<T> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = data;
    }

    @Override
    public ViewHolderRV onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolderRV viewHolder = ViewHolderRV.createViewHolder(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderRV holder, int position) {
        convert(holder, mDatas.get(position));
        holder.setPos(position);

        if(myOnclick != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    myOnclick.onItemClick(pos);
                }
            });
        }
        
        
    }

    public void convert(ViewHolderRV holder, T t) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
