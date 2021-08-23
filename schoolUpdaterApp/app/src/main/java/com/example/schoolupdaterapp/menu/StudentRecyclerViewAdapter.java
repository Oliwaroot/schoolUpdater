package com.example.schoolupdaterapp.menu;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolupdaterapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    List<EntityModelClass> eClasses;
    Dialog myDialog;

    public StudentRecyclerViewAdapter(Context mContext, List<EntityModelClass> eClasses) {
        this.mContext = mContext;
        this.eClasses = eClasses;
    }

    @NonNull
    @Override
    public StudentRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.student_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        myDialog = new Dialog(mContext);

        myViewHolder.addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.add_student);
                myDialog.show();
            }
        });

        myViewHolder.changeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.change_course);
                myDialog.show();
            }
        });


        myViewHolder.changeInstitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.change_institution);
                myDialog.show();
            }
        });

        myViewHolder.deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.delete_student);
                myDialog.show();
            }
        });

        myViewHolder.editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.edit_student);
                myDialog.show();
            }
        });

        myViewHolder.dropArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myViewHolder.expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(myViewHolder.cardView, new AutoTransition());
                    myViewHolder.expandableView.setVisibility(View.VISIBLE);
                    myViewHolder.dropArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else{
                    TransitionManager.beginDelayedTransition(myViewHolder.cardView, new AutoTransition());
                    myViewHolder.expandableView.setVisibility(View.GONE);
                    myViewHolder.dropArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_entityName.setText(eClasses.get(position).getEntityName());
    }

    @Override
    public int getItemCount() {
        return eClasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_entityName;
        private Button dropArrow;
        private LinearLayout expandableView;
        private CardView cardView;
        private Button editStudent;
        private Button deleteStudent;
        private Button addStudent;
        private Button changeCourse;
        private Button changeInstitution;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            changeCourse = (Button) itemView.findViewById(R.id.change_course);
            changeInstitution = (Button) itemView.findViewById(R.id.change_institution);
            deleteStudent = (Button) itemView.findViewById(R.id.delete_button);
            addStudent = (Button) itemView.findViewById(R.id.add_button);
            editStudent = (Button) itemView.findViewById(R.id.edit_button);
            expandableView = (LinearLayout) itemView.findViewById(R.id.expandableLinear);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            dropArrow = (Button) itemView.findViewById(R.id.drop_down);
            tv_entityName = (TextView) itemView.findViewById(R.id.entity_name);
        }
    }
}
