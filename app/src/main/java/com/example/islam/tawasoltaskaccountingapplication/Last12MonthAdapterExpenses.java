package com.example.islam.tawasoltaskaccountingapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;

import java.util.ArrayList;

/**
 * Created by islam on 25/10/2016.
 */
public class Last12MonthAdapterExpenses extends ArrayAdapter<MyTask> {

    Activity activity;
    int layoutresource;
    ArrayList<MyTask> mData = new ArrayList<>();

    public Last12MonthAdapterExpenses(Activity act, int resource, ArrayList<MyTask> data) {
        super(act, resource, data);

        activity = act;
        layoutresource = resource;
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public MyTask getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getPosition(MyTask item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null || (row.getTag()) == null) {

            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(R.layout.last12month_row, null);
            holder = new ViewHolder();

            holder.mType = (TextView) row.findViewById(R.id.last12month);


            row.setTag(holder);

        } else {

            holder = (ViewHolder) row.getTag();
        }

        holder.myTask = getItem(position);
        holder.mType.setText(holder.myTask.getType());



        return row;
    }

    class ViewHolder {

        MyTask myTask;
        TextView mName;
        TextView mType;
        TextView mAmount;
        TextView mDescription;
        TextView mPlace;
        TextView mDate;
        TextView mId;

    }

}


