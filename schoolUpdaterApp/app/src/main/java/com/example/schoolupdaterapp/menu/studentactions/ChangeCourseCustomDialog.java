package com.example.schoolupdaterapp.menu.studentactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.StudentsFragment.getAllStudentData;

public class ChangeCourseCustomDialog extends DialogFragment {

    private EditText editText;
    private Button acceptButton;
    private Button cancelButton;
    private String id;

    public ChangeCourseCustomDialog(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_course, container, false);
        editText = view.findViewById(R.id.course_name_course_change);
        acceptButton = view.findViewById(R.id.accept_change_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer idVal = Integer.parseInt(id);
                changeStudentCourse(idVal, editText.getText().toString());
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
                    Toast.makeText(getActivity(), "Failed to change course.", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<ChangeCourseDataModel> call, Throwable t) {

            }
        });
    }

}
