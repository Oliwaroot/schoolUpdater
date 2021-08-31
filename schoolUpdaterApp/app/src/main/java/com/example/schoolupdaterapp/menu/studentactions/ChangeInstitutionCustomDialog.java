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
import static com.example.schoolupdaterapp.menu.InstitutionsFragment.institutionNames;
import static com.example.schoolupdaterapp.menu.StudentsFragment.getAllStudentData;
import static com.example.schoolupdaterapp.menu.StudentsFragment.recyclerView3;
import static com.example.schoolupdaterapp.menu.StudentsFragment.recyclerViewAdapter3;

public class ChangeInstitutionCustomDialog extends DialogFragment {
    private Spinner spinnerIns2;
    private Spinner spinnerCourse2;
    private Button acceptButton;
    private Button cancelButton;
    private String id;
    private String institutionName;
    private String courseName;



    public ChangeInstitutionCustomDialog(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_institution, container, false);
        spinnerCourse2 = view.findViewById(R.id.course_name_inst_change);
        spinnerIns2 = view.findViewById(R.id.inst_name_inst_change);
        acceptButton = view.findViewById(R.id.accept_change_inst_button);
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
        spinnerIns2.setAdapter(arrayAdapter2);
        recyclerView3.setAdapter(recyclerViewAdapter3);

        spinnerIns2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                spinnerCourse2.setAdapter(arrayAdapter2);
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCourse2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                courseName = (String) adapterView.getItemAtPosition(i);
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (institutionName == null) {
                    Toast.makeText(getActivity(), "Please choose institution", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (courseName == null) {
                    Toast.makeText(getActivity(), "Please choose course", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer idVal = Integer.parseInt(id);
                changeStudentInstitution(idVal, courseName, institutionName);
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

    private void changeStudentInstitution(Integer id, String course, String institution){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        ChangeInstitutionDataModel changeInstitutionDataModel = new ChangeInstitutionDataModel(id, course, institution);
        Call<ChangeInstitutionDataModel> call = apiInterface.changeStudentInstitution(changeInstitutionDataModel);

        call.enqueue(new Callback<ChangeInstitutionDataModel>() {
            @Override
            public void onResponse(Call<ChangeInstitutionDataModel> call, Response<ChangeInstitutionDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Institution Transfer Successful", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ChangeInstitutionDataModel> call, Throwable t) {

            }
        });
    }

}
