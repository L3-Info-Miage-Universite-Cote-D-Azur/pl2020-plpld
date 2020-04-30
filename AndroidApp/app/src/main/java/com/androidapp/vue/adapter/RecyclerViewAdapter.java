package com.androidapp.vue.adapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.util.ArrayList;
import java.util.List;
import constantes.Net;
import metier.Identité;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Model> mModelList;
    Activity context;
    public RecyclerViewAdapter(List<Model> modelList , Activity context) {
        this.context=context;
        this.mModelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Model model = mModelList.get(position);
        holder.textView.setText(model.getText());
        holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                Log.d("POUR MONTRER", "on a cliqué sur " + model.getText());
                Connexion.CONNEXION.envoyerMessage2(Net.CHOIX, new Identité(model.getText()));
            }
        });

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Description de l'UE :" + model.getText())
                        .setMessage(Connexion.CONNEXION.getDescriptionUE().get(model.getText()))
                        .setNegativeButton(R.string.revenir, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();

                return true;
            }
        });

    }

    public List<String> selection() {
        List<String> Selection = new ArrayList<>();
        for (Model model : mModelList) {
            if (model.isSelected()) {
                Selection.add(model.getText());
            }
        }
        return Selection;
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
