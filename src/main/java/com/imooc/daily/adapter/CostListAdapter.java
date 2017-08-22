package com.imooc.daily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imooc.daily.R;
import com.imooc.daily.entity.CostBean;

import java.util.List;

/**
 * Created by xhx12366 on 2017-08-18.
 */

public class CostListAdapter extends BaseAdapter {
    private List<CostBean> list;
    private Context context;
    private LayoutInflater inflater;

    public CostListAdapter(Context context, List<CostBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        CostBean costBean = (CostBean) getItem(position);

        if(convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.list_item, null);
            holder.mTvCostTitle = (TextView) convertView.findViewById(R.id.text_title);
            holder.mTvCostData = (TextView) convertView.findViewById(R.id.text_data);
            holder.mTvCostMoney = (TextView) convertView.findViewById(R.id.text_money);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.mTvCostTitle.setText(costBean.getCostTitle());
        holder.mTvCostData.setText(costBean.getCostData());
        holder.mTvCostMoney.setText(costBean.getCostMoney());

        return convertView;
    }

    private static class Holder{
        TextView mTvCostTitle;
        TextView mTvCostData;
        TextView mTvCostMoney;
    }
}
