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
import static com.example.schoolupdaterapp.menu.StudentsFragment.getAllStudentData;
import static com.example.schoolupdaterapp.menu.StudentsFragment.recyclerView3;

public class ChangeCourseCustomDialog extends DialogFragment {

    private Spinner courseSpinner;
    private Button acceptButton;
    private Button cancelButton;
    private String id;
    private String institution;
    private String courseName;

    public ChangeCourseCustomDialog(String id, String institution) {
        this.id = id;
        this.institution = institution;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_course, container, false);
        courseSpinner = view.findViewById(R.id.course_name_course_change);
        acceptButton = view.findViewById(R.id.accept_change_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        List<String> courses = new ArrayList<>();
        courses.add("Select Course...");
        for (int j=0; j< courseList.size(); j++){
            if(courseList.get(j).contains(institution)){
                String newCourse = courseList.get(j);
                String newCourse2 = newCourse.replace(institution, "").trim();
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
        courseSpinner.setAdapter(arrayAdapter2);

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    courseName = (String) adapterView.getItemAtPosition(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseName == null) {
                    Toast.makeText(getActivity(), "Please pick a course", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer idVal = Integer.parseInt(id);
                changeStudentCourse(idVal, courseName);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void changeStudentCourse(Integer id, String course){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        ChangeCourseDataModel changeDataModel = new ChangeCourseDataModel(id, course);
        Call<ChangeCourseDataModel> call = apiInterface.changeStudentCourse(changeDataModel);

        call.enqueue(new Callback<ChangeCourseDataModel>() {
            @Override
            public void onResponse(Call<ChangeCourseDataModel> call, Response<ChangeCourseDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Course Changed Successfully", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ChangeCourseDataModel> call, Throwable t) {

            }
        });
    }

}
