package com.androidapp.vue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidapp.R;
import com.anton46.stepsview.StepsView;

public class StepsProgressAdapter extends ArrayAdapter<String> {
    private final String[] labels = {"S1", "S2", "S3", "S4"};

    public StepsProgressAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stepsprogess_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mLabel.setText(getItem(position));

        holder.mStepsView.setCompletedPosition(position % labels.length)
                .setLabels(labels)
                .setBarColorIndicator(
                        getContext().getResources().getColor(R.color.material_blue_grey_800))
                .setProgressColorIndicator(getContext().getResources().getColor(R.color.orange))
                .setLabelColorIndicator(getContext().getResources().getColor(R.color.orange))
                .drawView();

        return convertView;
    }

    static class ViewHolder {

        TextView mLabel;
        StepsView mStepsView;

        public ViewHolder(View view) {
            mLabel = (TextView) view.findViewById(R.id.label);
            mStepsView = (StepsView) view.findViewById(R.id.stepsView);
        }
    }
}
