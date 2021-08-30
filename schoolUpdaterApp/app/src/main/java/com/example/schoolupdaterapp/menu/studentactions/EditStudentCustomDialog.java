package com.example.schoolupdaterapp.menu.studentactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.courseactions.EditCourseDataModel;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.StudentsFragment.getAllStudentData;

public class EditStudentCustomDialog extends DialogFragment {
    private EditText editText;
    private Button editButton;
    private Button cancelButton;
    private String id;
    private String course;

    public EditStudentCustomDialog(String id, String course) {
        this.id = id;
        this.course = course;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_student, container, false);
        editText = view.findViewById(R.id.edit_student);
        editButton = view.findViewById(R.id.edit_student_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer idVal = Integer.parseInt(id);
                Integer courseVal = Integer.parseInt(course);
                putCourse(idVal, editText.getText().toString(), courseVal);
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

    private void putCourse(Integer id, String name, Integer course){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        EditStudentDataModel editStudentDataModel = new EditStudentDataModel(id, name, course);
        Call<EditStudentDataModel> call = apiInterface.createStudentPut(editStudentDataModel);

        call.enqueue(new Callback<EditStudentDataModel>() {
            @Override
            public void onResponse(Call<EditStudentDataModel> call, Response<EditStudentDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Student Edited Successfully", Toast.LENGTH_SHORT).show();
                    getAllStudentData();
                }
                else{
                    Toast.makeText(getActivity(), "Failed to edit. Student Already Exists.", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<EditStudentDataModel> call, Throwable t) {

            }
        });
    }
}
