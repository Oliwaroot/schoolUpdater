package com.example.schoolupdaterapp.menu.courseactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.institutionactions.DeleteInstitutionDataModel;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.CoursesFragment.getAllCourseData;

public class DeleteCourseCustomDialog extends DialogFragment {
    private String id;
    private Button deleteButton;
    private Button cancelButton;

    public DeleteCourseCustomDialog(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_course, container, false);
        deleteButton = view.findViewById(R.id.delete_course_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer idVal = Integer.parseInt(id);
                deleteCourse(idVal);
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

    private void deleteCourse(Integer id){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        DeleteCourseDataModel deleteCourseDataModel = new DeleteCourseDataModel(id);
        Call<DeleteCourseDataModel> call = apiInterface.createCourseDeletion(deleteCourseDataModel);

        call.enqueue(new Callback<DeleteCourseDataModel>() {
            @Override
            public void onResponse(Call<DeleteCourseDataModel> call, Response<DeleteCourseDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Course has been deleted", Toast.LENGTH_SHORT).show();
                    getAllCourseData();
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
            public void onFailure(Call<DeleteCourseDataModel> call, Throwable t) {

            }
        });

    }
}
