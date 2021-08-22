package com.example.schoolupdaterapp.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schoolupdaterapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<EntityModelClass> eClasses;

    public RecyclerViewAdapter(Context mContext, List<EntityModelClass> eClasses) {
        this.mContext = mContext;
        this.eClasses = eClasses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.entity_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_entityName.setText(eClasses.get(position).getEntityName());
        holder.tv_firstValue.setText(eClasses.get(position).getFirstEntityValue());
        holder.tv_secondValue.setText(eClasses.get(position).getSecondEntityValue());
    }

    @Override
    public int getItemCount() {
        return eClasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_entityName;
        private TextView tv_firstValue;
        private TextView tv_secondValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_entityName = (TextView) itemView.findViewById(R.id.entity_name);
            tv_firstValue = (TextView) itemView.findViewById(R.id.given_value);
            tv_secondValue = (TextView) itemView.findViewById(R.id.given_value2);
        }
    }
}
