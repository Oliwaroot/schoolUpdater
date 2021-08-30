package com.example.schoolupdaterapp.menu.courseactions;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.institutionactions.GetInstitutionDataModel;
import com.example.schoolupdaterapp.menu.studentactions.GetStudentsDataModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.MyViewHolder> implements Filterable {
    Context mContext;

    public ArrayList<GetCoursesDataModel> geteClasses() {
        return eClasses;
    }

    public void seteClasses(ArrayList<GetCoursesDataModel> eClasses) {
        this.eClasses = eClasses;
        eClassesFull = new ArrayList<>(eClasses);
    }

    ArrayList<GetCoursesDataModel> eClasses;
    ArrayList<GetCoursesDataModel> eClassesFull;
//    Dialog myDialog;

    public CourseRecyclerViewAdapter(Context mContext, ArrayList<GetCoursesDataModel> eClasses) {
        eClasses = new ArrayList<>();
        this.mContext = mContext;
        this.eClasses = eClasses;
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.course_item,parent,false);
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
        holder.tv_instName.setText(eClasses.get(position).getInstitutionName());

        holder.editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                String newVal2 = eClasses.get(holder.getBindingAdapterPosition()).getInstitution();
                EditCourseCustomDialog dialog = new EditCourseCustomDialog(newVal, newVal2);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog 2");
            }
        });

        holder.deleteCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                DeleteCourseCustomDialog dialog = new DeleteCourseCustomDialog(newVal);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog 2");
            }
        });
    }

    @Override
    public int getItemCount() {
        return eClasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_entityName;
        private TextView tv_instName;
        private Button dropArrow;
        private LinearLayout expandableView;
        private CardView cardView;
        private Button editCourse;
        private Button deleteCourse;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            editCourse = (Button) itemView.findViewById(R.id.edit_button);
            deleteCourse = (Button) itemView.findViewById(R.id.delete_button);
            expandableView = (LinearLayout) itemView.findViewById(R.id.expandableLinear);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            dropArrow = (Button) itemView.findViewById(R.id.drop_down);
            tv_entityName = (TextView) itemView.findViewById(R.id.entity_name);
            tv_instName = (TextView) itemView.findViewById(R.id.inst_name);
        }
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    public Filter getQueryFilter2(){ return queryFilter2; }

    private Filter queryFilter2 = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GetCoursesDataModel> filteredList = new ArrayList<>();

            if(charSequence == "All"){
                if(eClassesFull != null){
                    filteredList.addAll(eClassesFull);
                }
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(GetCoursesDataModel model : eClassesFull){
                    if(model.getInstitutionName().toLowerCase().contains(filterPattern)){
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
                eClasses.addAll((ArrayList<GetCoursesDataModel>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    };

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GetCoursesDataModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                if(eClassesFull != null){
                    filteredList.addAll(eClassesFull);
                }
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(GetCoursesDataModel model : eClassesFull){
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
                eClasses.addAll((ArrayList<GetCoursesDataModel>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    };

}
