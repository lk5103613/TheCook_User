package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dcjd.cook.R;
import com.like.entity.ScoreRecord;

public class ScoreRecordAdapter extends BaseAdapter {

    private List<ScoreRecord> mScores;
    private LayoutInflater mInflater;

    public ScoreRecordAdapter(Context context, List<ScoreRecord> scores) {
        this.mScores = scores;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mScores.size();
    }

    @Override
    public Object getItem(int position) {
        return mScores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public void updateList(List<ScoreRecord> scores) {
    	this.mScores = scores;
    	notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ScoreRecord record = mScores.get(position);
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.score_record_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.sTxtTime.setText(record.add_time);
        viewHolder.sTxtAction.setText(record.reason);
        viewHolder.sTxtScore.setText(record.score);
        return convertView;
    }

    public class ViewHolder {
        public TextView sTxtTime;
        public TextView sTxtAction;
        public TextView sTxtScore;

        public ViewHolder (View v) {
            this.sTxtTime = (TextView) v.findViewById(R.id.score_time);
            this.sTxtAction = (TextView) v.findViewById(R.id.score_action);
            this.sTxtScore = (TextView) v.findViewById(R.id.score_score);
        }

    }
}
