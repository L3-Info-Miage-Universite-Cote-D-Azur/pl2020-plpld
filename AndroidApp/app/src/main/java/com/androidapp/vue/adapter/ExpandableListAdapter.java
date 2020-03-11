package com.androidapp.vue.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.R;

import metier.Matiere;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> UECollections;
    private List<String> UE;
    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Map<Integer, RecyclerViewAdapter> AdapterCollection;


    public ExpandableListAdapter(Activity context, List<String> UE,
                                 Map<String, List<String>> UECollections) {
        this.context = context;
        this.UECollections = UECollections;
        this.UE = UE;
        AdapterCollection = new LinkedHashMap<>(getGroupCount()); //On crée une collection de RecyclerViewAdapter pour récupérer la séléction de l'utilisateur (un adaptateur pour chaque discipline sur laquelle l'utilisateur clique)
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return UECollections.get(UE.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }
        mRecyclerView = convertView.findViewById(R.id.recycler_view);
        if(AdapterCollection.containsKey(groupPosition))
            mAdapter = AdapterCollection.get(groupPosition);
        else
            mAdapter = new RecyclerViewAdapter(getListData(UE.get(groupPosition)));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        AdapterCollection.put(groupPosition, mAdapter);
        return mRecyclerView;
    }

    private List<Model> getListData(String discipline) {
        mModelList = new ArrayList<>();
        for(String UE: UECollections.get(discipline)) {
            mModelList.add(new Model(UE));
        }
        return mModelList;
    }

    public List<Matiere> selection(Matiere numSemestre) {
        List<Matiere> Selection = new ArrayList<Matiere>();
        Selection.add(numSemestre);
        for(Map.Entry<Integer, RecyclerViewAdapter> R: AdapterCollection.entrySet()) {
            Selection.addAll(R.getValue().selection());
        }
        return Selection;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; //Un seul enfant par groupe : le recyclerView
    }

    @Override
    public Object getGroup(int groupPosition) {
        return UE.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return UE.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String UEName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = convertView.findViewById(R.id.disciplines);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(UEName);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}