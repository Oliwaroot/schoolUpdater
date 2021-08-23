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

public class StudentsFragment extends Fragment {
    View v3;
    private RecyclerView recyclerView3;
    private List<EntityModelClass> studentModel;

    public StudentsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentModel = new ArrayList<>();
        studentModel.add(new EntityModelClass("StudentOne","insttution: ThisOne","course: ThatOne"));
        studentModel.add(new EntityModelClass("StudentOne","insttution: ThisOne","course: ThatOne"));
        studentModel.add(new EntityModelClass("StudentOne","insttution: ThisOne","course: ThatOne"));
        studentModel.add(new EntityModelClass("StudentOne","insttution: ThisOne","course: ThatOne"));
        studentModel.add(new EntityModelClass("StudentOne","insttution: ThisOne","course: ThatOne"));
        studentModel.add(new EntityModelClass("StudentOne","insttution: ThisOne","course: ThatOne"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v3 = inflater.inflate(R.layout.students_fragment,container,false);
        recyclerView3 = (RecyclerView) v3.findViewById(R.id.student_recycler);
        StudentRecyclerViewAdapter recyclerViewAdapter = new StudentRecyclerViewAdapter(getContext(),studentModel);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView3.setAdapter(recyclerViewAdapter);
        return v3;
    }
}
