package com.example.schoolupdaterapp.menu.studentactions;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.courseactions.AddCourseDataModel;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.CoursesFragment.courseList;
import static com.example.schoolupdaterapp.menu.CoursesFragment.courseModel;
import static com.example.schoolupdaterapp.menu.CoursesFragment.recyclerView2;
import static com.example.schoolupdaterapp.menu.CoursesFragment.recyclerViewAdapter2;
import static com.example.schoolupdaterapp.menu.InstitutionsFragment.institutionNames;
import static com.example.schoolupdaterapp.menu.StudentsFragment.getAllStudentData;
import static com.example.schoolupdaterapp.menu.StudentsFragment.recyclerView3;
import static com.example.schoolupdaterapp.menu.StudentsFragment.recyclerViewAdapter3;
import static com.example.schoolupdaterapp.menu.StudentsFragment.studentModel;

public class AddStudentCustomDialog extends DialogFragment {
    private EditText editText;
    private Spinner spinnerCourse;
    private String institutionName;
    private String courseName;
    private static Spinner spinnerIns;
    private Button addButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_student, container, false);
        editText = view.findViewById(R.id.editStudent);
        spinnerCourse = view.findViewById(R.id.editCourse);
        spinnerIns = view.findViewById(R.id.editInstitution);
        addButton = view.findViewById(R.id.add_student_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        List<String> list = new ArrayList<>();
        list.add("Select Institution...");
        list.addAll(institutionNames);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(recyclerView3.getContext(), android.R.layout.simple_spinner_item, list){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIns.setAdapter(arrayAdapter2);
        recyclerView3.setAdapter(recyclerViewAdapter3);

        spinnerIns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                institutionName = (String) adapterView.getItemAtPosition(i);
                List<String> courses = new ArrayList<>();
                courses.add("Select Course...");
                for (int j=0; j< courseList.size(); j++){
                    if(courseList.get(j).contains(institutionName)){
                        String newCourse = courseList.get(j);
                        String newCourse2 = newCourse.replace(institutionName, "").trim();
                        courses.add(newCourse2);
                    }
                }

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(recyclerView3.getContext(), android.R.layout.simple_spinner_item, courses){
                    @Override
                    public boolean isEnabled(int position) {
                        return position != 0;
                    }
                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCourse.setAdapter(arrayAdapter2);
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                courseName = (String) adapterView.getItemAtPosition(i);
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter student name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (institutionName == null) {
                    Toast.makeText(getActivity(), "Please choose institution", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (courseName == null) {
                    Toast.makeText(getActivity(), "Please choose course", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postStudent(editText.getText().toString(), courseName, institutionName);
            }
        });



        return view;
    }

    private void postStudent(String name, String course, String institution){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        AddStudentDataModel addStudentDataModel = new AddStudentDataModel(name, course, institution);
        Call<AddStudentDataModel> call = apiInterface.createStudentPost(addStudentDataModel);

        call.enqueue(new Callback<AddStudentDataModel>() {
            @Override
            public void onResponse(Call<AddStudentDataModel> call, Response<AddStudentDataModel> response) {

                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Student Added Successfully", Toast.LENGTH_SHORT).show();
                    getAllStudentData();
                }
                else{
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<AddStudentDataModel> call, Throwable t) {

            }
        });

    }
}
