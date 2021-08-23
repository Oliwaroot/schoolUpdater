package com.example.schoolupdaterapp.menu;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<DataModel> eClasses;
    Dialog myDialog;

    public RecyclerViewAdapter(Context mContext, ArrayList<DataModel> eClasses) {
        eClasses = new ArrayList<>();
        this.mContext = mContext;
        this.eClasses = eClasses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.entity_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
//        myDialog = new Dialog(mContext);
//        eClasses = new ArrayList<>();
//        eClasses.add(new DataModel("Hello There"));
//
//        myViewHolder.addEntity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myDialog.setContentView(R.layout.add_institution);
//                myDialog.show();
//            }
//        });
//
//
//        myViewHolder.deleteEntity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myDialog.setContentView(R.layout.delete_institution);
//                myDialog.show();
//            }
//        });
//
//        myViewHolder.editEntity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myDialog.setContentView(R.layout.edit_institution);
//                myDialog.show();
//            }
//        });
//
//        myViewHolder.dropArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(myViewHolder.expandableView.getVisibility()==View.GONE){
//                    TransitionManager.beginDelayedTransition(myViewHolder.cardView, new AutoTransition());
//                    myViewHolder.expandableView.setVisibility(View.VISIBLE);
//                    myViewHolder.dropArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
//                }
//                else{
//                    TransitionManager.beginDelayedTransition(myViewHolder.cardView, new AutoTransition());
//                    myViewHolder.expandableView.setVisibility(View.GONE);
//                    myViewHolder.dropArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
//                }
//            }
//        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Log.i("thisMessage", "pleaseWork1"+eClasses.get(position).getName());
        Log.i("this", "position"+position);
        holder.tv_entityName.setText(eClasses.get(position).getName());
//        Log.i("thisMessage", "pleaseWork2"+eClasses.get(position).getName());
    }

    public ArrayList<DataModel> geteClasses() {
        return eClasses;
    }

    public void seteClasses(ArrayList<DataModel> eClasses) {
        this.eClasses = eClasses;
    }

    @Override
    public int getItemCount() {
        Log.i("thisMessage", "pleaseWork3"+eClasses.size());
        return eClasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_entityName;
        private Button dropArrow;
        private LinearLayout expandableView;
        private CardView cardView;
        private Button editEntity;
        private Button deleteEntity;
        private Button addEntity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            deleteEntity = (Button) itemView.findViewById(R.id.delete_button);
            addEntity = (Button) itemView.findViewById(R.id.add_button);
            editEntity = (Button) itemView.findViewById(R.id.edit_button);
            expandableView = (LinearLayout) itemView.findViewById(R.id.expandableLinear);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            dropArrow = (Button) itemView.findViewById(R.id.drop_down);
            tv_entityName = (TextView) itemView.findViewById(R.id.entity_name);
        }
    }
}
