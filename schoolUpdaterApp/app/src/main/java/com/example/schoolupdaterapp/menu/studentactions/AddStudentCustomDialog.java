package com.example.schoolupdaterapp.menu.studentactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.courseactions.AddCourseDataModel;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.StudentsFragment.getAllStudentData;

public class AddStudentCustomDialog extends DialogFragment {
    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private Button addButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_student, container, false);
        editText = view.findViewById(R.id.editStudent);
        editText2 = view.findViewById(R.id.editCourse);
        editText3 = view.findViewById(R.id.editInstitution);
        addButton = view.findViewById(R.id.add_student_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() == 0 || editText2.getText().toString().trim().length() == 0 || editText3.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter both values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postStudent(editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
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
                    Toast.makeText(getActivity(), "Error adding student", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<AddStudentDataModel> call, Throwable t) {

            }
        });

    }
}
