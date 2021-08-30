package com.example.schoolupdaterapp.menu.studentactions;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.EntityModelClass;
import com.example.schoolupdaterapp.menu.courseactions.DeleteCourseCustomDialog;
import com.example.schoolupdaterapp.menu.courseactions.EditCourseCustomDialog;
import com.example.schoolupdaterapp.menu.institutionactions.GetInstitutionDataModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<GetStudentsDataModel> eClasses;
    ArrayList<GetStudentsDataModel> eClassesFull;

    public ArrayList<GetStudentsDataModel> geteClasses() {
        return eClasses;
    }

    public void seteClasses(ArrayList<GetStudentsDataModel> eClasses) {
        this.eClasses = eClasses;
        eClassesFull = new ArrayList<>(eClasses);
    }

//    Dialog myDialog;

    public StudentRecyclerViewAdapter(Context mContext, ArrayList<GetStudentsDataModel> eClasses) {
        this.mContext = mContext;
        this.eClasses = eClasses;
    }

    @NonNull
    @Override
    public StudentRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.student_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);


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
        holder.tv_entityName.setText(eClasses.get(position).getName());
        holder.tv_entityName2.setText(eClasses.get(position).getInstitution());
        holder.tv_entityName3.setText(eClasses.get(position).getCourseName());

        holder.changeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                ChangeCourseCustomDialog dialog = new ChangeCourseCustomDialog(newVal);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog 3");
            }
        });
//
        holder.changeInstitution.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                ChangeInstitutionCustomDialog dialog = new ChangeInstitutionCustomDialog(newVal);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog 3");
            }
        });
//
        holder.editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                String newVal2 = eClasses.get(holder.getBindingAdapterPosition()).getCourse();
                EditStudentCustomDialog dialog = new EditStudentCustomDialog(newVal, newVal2);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog 3");
            }
        });

        holder.deleteStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                DeleteStudentCustomDialog dialog = new DeleteStudentCustomDialog(newVal);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog 3");
            }
        });
    }

    @Override
    public int getItemCount() {
        return eClasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_entityName;
        private TextView tv_entityName2;
        private TextView tv_entityName3;
        private Button dropArrow;
        private LinearLayout expandableView;
        private CardView cardView;
        private Button editStudent;
        private Button deleteStudent;
        private Button changeCourse;
        private Button changeInstitution;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            changeCourse = (Button) itemView.findViewById(R.id.change_entity_course);
            changeInstitution = (Button) itemView.findViewById(R.id.change_entity_institution);
            deleteStudent = (Button) itemView.findViewById(R.id.delete_entity_button);
            editStudent = (Button) itemView.findViewById(R.id.edit_entity_button);
            expandableView = (LinearLayout) itemView.findViewById(R.id.expandableLinear);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            dropArrow = (Button) itemView.findViewById(R.id.drop_down);
            tv_entityName = (TextView) itemView.findViewById(R.id.entity_name);
            tv_entityName2 = (TextView) itemView.findViewById(R.id.name_of_institution);
            tv_entityName3 = (TextView) itemView.findViewById(R.id.name_of_course);
        }
    }

    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GetStudentsDataModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                if(eClassesFull != null){
                    filteredList.addAll(eClassesFull);
                }
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(GetStudentsDataModel model : eClassesFull){
                    if(model.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(model);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            if(((ArrayList<?>) filterResults.values).size() > 0){
                eClasses.clear();
                eClasses.addAll((ArrayList<GetStudentsDataModel>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    };

}
