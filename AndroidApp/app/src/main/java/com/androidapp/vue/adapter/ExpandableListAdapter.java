package com.androidapp.vue.adapter;

import java.util.ArrayList;
import java.util.HashMap;
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

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> UECollections;
    private List<String> UE;
    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private Map<Integer, RecyclerViewAdapter> AdapterCollection;
    private List<String> UEOriginal;
    private Map<String, List<String>> UECollectionsOriginal;

    private Map<String,List<Model>> modelMap;
    private Map<String,List<Model>> modelMapOriginal;
    private List<Model> models;

    public ExpandableListAdapter(Activity context , List<String> UE , Map<String, List<String>> UECollections) {
        this.context = context;

        this.UE =new ArrayList<>();
        this.UE.addAll(UE);
        this.UECollections =new HashMap<>();
        this.UECollections.putAll(UECollections);

        UEOriginal=new ArrayList<>();
        UEOriginal.addAll(UE);
        UECollectionsOriginal=new HashMap<>();
        UECollectionsOriginal.putAll(UECollections);

        AdapterCollection = new LinkedHashMap<>(getGroupCount()); //On crée une collection de RecyclerViewAdapter pour récupérer la séléction de l'utilisateur (un adaptateur pour chaque discipline sur laquelle l'utilisateur clique)

        modelMapOriginal=new HashMap<>();
        modelMap=new HashMap<>();

        models=new ArrayList<>();
        for (String s : UECollectionsOriginal.keySet()){
            for (String ue : UECollectionsOriginal.get(s)){
                Model m =new Model(ue);
                models.add(m);
                modelMapOriginal.put(s,models);
                modelMap.put(s,models);
            }
            models=new ArrayList<>();
        }
    }

    @Override
    public int getGroupCount() {
        return UE.size();
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
    public Object getChild(int groupPosition, int childPosition) {
        return UECollections.get(UE.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
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
            mAdapter = new RecyclerViewAdapter(getListData(UE.get(groupPosition),groupPosition,UE),context);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        AdapterCollection.put(groupPosition, mAdapter);
        return mRecyclerView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private List<Model> getListData(String discipline,int groupPosition,List<String> a) {
        mModelList = new ArrayList<>();
        mModelList.addAll(modelMap.get(a.get(groupPosition)));

        return mModelList;
    }

    public List<String> selection(String numSemestre) {
        List<String> Selection = new ArrayList<String>();
        Selection.add(numSemestre);
        for(Map.Entry<Integer, RecyclerViewAdapter> R: AdapterCollection.entrySet()) {
            Selection.addAll(R.getValue().selection());
        }
        return Selection;
    }


    public void filterData(String query) {
        query = query.toLowerCase();

        UE.clear();
        UECollections.clear();
        AdapterCollection.clear();
        modelMap.clear();

        
        if (query.isEmpty()) {
            UE.addAll(UEOriginal);
            UECollections.putAll(UECollectionsOriginal);
            modelMap.putAll(modelMapOriginal);
        } else {
            for (String s : UEOriginal) {
                ArrayList<Model>  models=new ArrayList<>();
                ArrayList<String> ueDeS = new ArrayList<>();
                for (String ue : UECollectionsOriginal.get(s)) {
                    if (ue.toLowerCase().contains(query) ) {
                        ueDeS.add(ue);
                        for (Model m : modelMapOriginal.get(s)){
                            if(m.getText().equals(ue)){
                                models.add(m);
                            }
                        }
                    }
                }
                if (ueDeS.size() > 0) {
                    UE.add(s);
                    UECollections.put(s,ueDeS);
                    modelMap.put(s,models);
                }
            }
        }

        notifyDataSetChanged();


    }

}
