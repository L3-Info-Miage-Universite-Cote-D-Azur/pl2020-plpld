package com.androidapp.vue.activity;

import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import constantes.Net;
import metier.Etudiant;
import metier.Identité;

public class ConsultationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Connexion.CONNEXION.démarrerÉcoute();
        Connexion.CONNEXION.envoyerMessage(Net.CONSULTATION, new Identité("Consultation"));
        initVue();
    }

    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void initVue() {
        final ProgressDialog dialog = ProgressDialog.show(ConsultationActivity.this, "", "En attente d'une réponse serveur...", true);
        dialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (Connexion.CONNEXION.getConsultationUE().size() > 0) {
                    dialog.dismiss();
                    initAdapter();
                } else {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (Connexion.CONNEXION.getConsultationUE().size() > 0) {
                                initAdapter();
                            } else {
                                displayMsg("Erreur serveur");
                                Connexion.CONNEXION.resetConsultationUE();
                                finish();
                            }
                            dialog.dismiss();
                        }
                    }, 6000);
                }
            }
        }, 500);
    }


    private List<String> groupList = new ArrayList<>();
    private Map<String, List<String>> childListMap = new HashMap<>();
    private void initAdapter() {
        setContentView(R.layout.activity_consultation);

        findViewById(R.id.btn_retour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connexion.CONNEXION.resetConsultationUE();
                finish();
            }
        });

        setTitle("Consultation des parcours des autres étudiant");

        for(Etudiant etudiant : Connexion.CONNEXION.getConsultationUE().keySet()) {
            addStudent(etudiant.getNumEtudiant(), Connexion.CONNEXION.getConsultationUE().get(etudiant));
        }

        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getGroupCount() {
                return Connexion.CONNEXION.getConsultationUE().size();
            }

            @Override
            public int getChildrenCount(int groupIndex) {
                String group = groupList.get(groupIndex);
                List<String> childInfoList = childListMap.get(group);
                return childInfoList.size();
            }

            @Override
            public Object getGroup(int groupIndex) {
                return groupList.get(groupIndex);
            }

            @Override
            public Object getChild(int groupIndex, int childIndex) {
                String group = groupList.get(groupIndex);
                List<String> childInfoList = childListMap.get(group);
                return childInfoList.get(childIndex);
            }

            @Override
            public long getGroupId(int groupIndex) {
                return groupIndex;
            }

            @Override
            public long getChildId(int groupIndex, int childIndex) {
                return childIndex;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int groupIndex, boolean isExpanded, View view, ViewGroup viewGroup) {
                LinearLayout groupLayoutView = new LinearLayout(ConsultationActivity.this);
                groupLayoutView.setOrientation(LinearLayout.HORIZONTAL);

                //A décommenter si on souhaite ajouter une image (comme un avatar) à côté du nom de l'étudiant
                /*
                ImageView groupImageView = new ImageView(ConsultationActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                params.setMargins(100, 1, 1, 1);
                groupImageView.setLayoutParams(params);
                if(isExpanded) {
                    groupImageView.setImageResource(R.mipmap.ic_launcher_round);
                }else
                {
                    groupImageView.setImageResource(R.mipmap.ic_launcher);
                }
                groupLayoutView.addView(groupImageView); */

                String groupText = groupList.get(groupIndex);
                TextView groupTextView = new TextView(ConsultationActivity.this);
                groupTextView.setText(groupText);
                groupTextView.setTextSize(30);
                groupLayoutView.setPadding(100, 0, 0, 0);
                groupLayoutView.addView(groupTextView);

                return groupLayoutView;
            }

            @Override
            public View getChildView(int groupIndex, int childIndex, boolean isLastChild, View view, ViewGroup viewGroup) {
                Object childObj = this.getChild(groupIndex, childIndex);
                String childText = (String)childObj;
                TextView childTextView = new TextView(ConsultationActivity.this);
                childTextView.setText(childText);
                childTextView.setTextSize(20);
                childTextView.setBackgroundColor(Color.WHITE);

                Drawable groupImage = getDrawable(R.mipmap.ic_launcher);
                int groupImageWidth = groupImage.getIntrinsicWidth();

                childTextView.setPadding(groupImageWidth,0,0,0);

                return childTextView;
            }

            @Override
            public boolean isChildSelectable(int groupIndex, int childIndex) {
                return false;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupIndex) {

            }

            @Override
            public void onGroupCollapsed(int groupIndex) {


            }

            @Override
            public long getCombinedChildId(long groupIndex, long childIndex) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupIndex) {
                return 0;
            }
        };

        final ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupIndex) {
                int groupListSize = groupList.size();
                for(int i=0;i < groupListSize; i++) {
                    if(i!=groupIndex) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }
    private void addStudent(String name, List<String> ListUE)
    {
        if(this.groupList == null)
        {
            this.groupList = new ArrayList<String>();
        }

        if(this.childListMap == null)
        {
            this.childListMap = new HashMap<String, List<String>>();
        }

        if(!this.groupList.contains(name)) {
            this.groupList.add(name);
        }

        List<String> childList = new ArrayList<String>();
        for(String UE: ListUE) {
            childList.add(UE);
        }
        this.childListMap.put(name, childList);
    }
}

