package com.androidapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import metier.Matiere;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> UECollections;
    private List<String> UE;
    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;


    public ExpandableListAdapter(Activity context, List<String> UE,
                                 Map<String, List<String>> UECollections) {
        this.context = context;
        this.UECollections = UECollections;
        this.UE = UE;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return UECollections.get(UE.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        mRecyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(getListData(UE.get(groupPosition)));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    private List<Model> getListData(String discipline) {
        mModelList = new ArrayList<>();
        for(String UE: UECollections.get(discipline)) {
            mModelList.add(new Model(UE));
        }
        return mModelList;
    }

    public List<Matiere> selection() {
        if(mAdapter==null) return new ArrayList<Matiere>(Arrays.asList(new Matiere()));
        return new ArrayList<Matiere>(Arrays.asList(new Matiere()));
    }


    public int getChildrenCount(int groupPosition) {
        return UECollections.get(UE.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return UE.get(groupPosition);
    }

    public int getGroupCount() {
        return UE.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String UEName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.disciplines);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(UEName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}