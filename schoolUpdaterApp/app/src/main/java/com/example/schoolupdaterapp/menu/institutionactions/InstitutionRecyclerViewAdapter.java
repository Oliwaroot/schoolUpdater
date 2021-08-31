package com.example.schoolupdaterapp.menu.institutionactions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolupdaterapp.R;

import java.util.ArrayList;
import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class InstitutionRecyclerViewAdapter extends RecyclerView.Adapter<InstitutionRecyclerViewAdapter.MyViewHolder> implements Filterable{

    Context mContext;
    ArrayList<GetInstitutionDataModel> eClasses;
    ArrayList<GetInstitutionDataModel> eClassesFull;
//    AddInstitutionCustomDialog myDialog;

    public InstitutionRecyclerViewAdapter(Context mContext, ArrayList<GetInstitutionDataModel> eClasses) {
        eClasses = new ArrayList<>();
        this.mContext = mContext;
        this.eClasses = eClasses;
        eClassesFull = new ArrayList<>(eClasses);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.entity_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
//        myDialog = new AddInstitutionCustomDialog();

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
        Log.i("this", "position"+position);

        holder.tv_entityName.setText(eClasses.get(position).getName());
        holder.dropArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.expandableView.setVisibility(View.VISIBLE);
                    holder.dropArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else{
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.expandableView.setVisibility(View.GONE);
                    holder.dropArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });

        holder.deleteEntity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                Log.i("Ain't gonna work", newVal);
                DeleteInstitutionCustomDialog dialog = new DeleteInstitutionCustomDialog(newVal);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog");
            }
        });

        holder.editEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newVal = eClasses.get(holder.getBindingAdapterPosition()).getId();
                EditInstitutionCustomDialog dialog = new EditInstitutionCustomDialog(newVal);
                dialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"Custom Dialog");
            }
        });
    }

    public ArrayList<GetInstitutionDataModel> geteClasses() {
        return eClasses;
    }

    public void seteClasses(ArrayList<GetInstitutionDataModel> eClasses) {
        this.eClasses = eClasses;
        eClassesFull = new ArrayList<>(eClasses);
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
        private Button editEntity;
        private Button deleteEntity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            deleteEntity = (Button) itemView.findViewById(R.id.delete_button);
            editEntity = (Button) itemView.findViewById(R.id.edit_button);
            expandableView = (LinearLayout) itemView.findViewById(R.id.expandableLinear);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            dropArrow = (Button) itemView.findViewById(R.id.drop_down);
            tv_entityName = (TextView) itemView.findViewById(R.id.entity_name);
        }
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<GetInstitutionDataModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                if(eClassesFull != null){
                    filteredList.addAll(eClassesFull);
                }
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(GetInstitutionDataModel model : eClassesFull){
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
                eClasses.addAll((ArrayList<GetInstitutionDataModel>) filterResults.values);
                notifyDataSetChanged();
            }
//            else{
//                eClasses.clear();
//                notifyDataSetChanged();
//            }
        }
    };
}
