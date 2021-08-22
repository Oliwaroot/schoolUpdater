package com.example.schoolupdaterapp.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolupdaterapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InstitutionsFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<EntityModelClass> institutionModel;

    public InstitutionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.institutions_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.institution_recycler);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),institutionModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        institutionModel = new ArrayList<>();
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
    }
}
