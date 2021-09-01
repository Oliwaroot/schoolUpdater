package com.example.schoolupdaterapp.menu.studentactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.courseactions.DeleteCourseDataModel;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.StudentsFragment.getAllStudentData;

public class DeleteStudentCustomDialog extends DialogFragment {

    private String id;
    private Button deleteButton;
    private Button cancelButton;

    public DeleteStudentCustomDialog(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_student, container, false);
        deleteButton = view.findViewById(R.id.delete_student_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer idVal = Integer.parseInt(id);
                deleteStudent(idVal);
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

    private void deleteStudent(Integer id){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        DeleteStudentDataModel deleteStudentDataModel = new DeleteStudentDataModel(id);
        Call<DeleteStudentDataModel> call = apiInterface.createStudentDeletion(deleteStudentDataModel);

        call.enqueue(new Callback<DeleteStudentDataModel>() {
            @Override
            public void onResponse(Call<DeleteStudentDataModel> call, Response<DeleteStudentDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Student has been deleted", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<DeleteStudentDataModel> call, Throwable t) {

            }
        });

    }
}
