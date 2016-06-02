package com.dalong.rotatetextview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zhouweilong on 16/6/1.
 */

public class MyAdapter extends BaseAdapter {

    public List<RotateEntity> list;

    public Context mContext;

    public MyAdapter(List<RotateEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RotateEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.list_item,null);
            mViewHolder=new ViewHolder();
            mViewHolder.mRotateView=(RotateView)convertView.findViewById(R.id.rotate);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.mRotateView.setText(list.get(position).getName());
        mViewHolder.mRotateView.setRotate(list.get(position).getRotate());
        return convertView;
    }

    class ViewHolder{
        RotateView mRotateView;
    }
}
