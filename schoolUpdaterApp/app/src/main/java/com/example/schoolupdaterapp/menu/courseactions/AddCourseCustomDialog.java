package com.example.schoolupdaterapp.menu.courseactions;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.CoursesFragment.courseModel;
import static com.example.schoolupdaterapp.menu.CoursesFragment.getAllCourseData;
import static com.example.schoolupdaterapp.menu.CoursesFragment.recyclerView2;
import static com.example.schoolupdaterapp.menu.CoursesFragment.recyclerViewAdapter2;
import static com.example.schoolupdaterapp.menu.InstitutionsFragment.institutionNames;

public class AddCourseCustomDialog extends DialogFragment {

    private EditText editText;
    public static Spinner spinnerInstitution;
    private Button addButton;
    private String institutionName;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course, container, false);
        editText = view.findViewById(R.id.addCourse);
        spinnerInstitution = (Spinner) view.findViewById(R.id.addInstitution);
        addButton = view.findViewById(R.id.add_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        List<String> list = new ArrayList<>();
        list.add("Select Institution...");
        list.addAll(institutionNames);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(recyclerView2.getContext(), android.R.layout.simple_spinner_item, list){
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
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstitution.setAdapter(arrayAdapter3);

        spinnerInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    institutionName = (String) adapterView.getItemAtPosition(i);
                }
            }

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
                    Toast.makeText(getActivity(), "Please enter course name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (institutionName == null) {
                    Toast.makeText(getActivity(), "Please choose institution", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postCourse(editText.getText().toString(), institutionName);
            }
        });



        return view;
    }

    private void postCourse(String name, String institution){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        AddCourseDataModel addCourseDataModel = new AddCourseDataModel(name, institution);
        Call<AddCourseDataModel> call = apiInterface.createCoursePost(addCourseDataModel);

        call.enqueue(new Callback<AddCourseDataModel>() {
            @Override
            public void onResponse(Call<AddCourseDataModel> call, Response<AddCourseDataModel> response) {

                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Course Added Successfully", Toast.LENGTH_SHORT).show();
                    getAllCourseData();
                    //errorBody content has string response
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
            public void onFailure(Call<AddCourseDataModel> call, Throwable t) {

            }
        });

    }
}
