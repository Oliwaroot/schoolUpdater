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

public class CoursesFragment extends Fragment {
    View v2;
    private RecyclerView recyclerView2;
    private List<EntityModelClass> courseModel;

    public CoursesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = new ArrayList<>();
        courseModel.add(new EntityModelClass("CourseOne","insttution: ThisOne","students: 80"));
        courseModel.add(new EntityModelClass("CourseOne","insttution: ThisOne","students: 80"));
        courseModel.add(new EntityModelClass("CourseOne","insttution: ThisOne","students: 80"));
        courseModel.add(new EntityModelClass("CourseOne","insttution: ThisOne","students: 80"));
        courseModel.add(new EntityModelClass("CourseOne","insttution: ThisOne","students: 80"));
        courseModel.add(new EntityModelClass("CourseOne","insttution: ThisOne","students: 80"));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v2 = inflater.inflate(R.layout.courses_fragment,container,false);
        recyclerView2 = (RecyclerView) v2.findViewById(R.id.course_recycler);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),courseModel);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setAdapter(recyclerViewAdapter);
        return v2;
    }
}
