package com.example.schoolupdaterapp.menu.courseactions;

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

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.CoursesFragment.getAllCourseData;

public class EditCourseCustomDialog extends DialogFragment {
    private EditText editText;
    private Button editButton;
    private Button cancelButton;
    private String id;
    private String institution;

    public EditCourseCustomDialog(String id, String institution) {
        this.id = id;
        this.institution = institution;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_course, container, false);
        editText = view.findViewById(R.id.editText);
        editButton = view.findViewById(R.id.edit_course_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer idVal = Integer.parseInt(id);
                Integer instVal = Integer.parseInt(institution);
                putCourse(editText.getText().toString(), idVal, instVal);
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

    private void putCourse(String name, Integer id, Integer institution){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        EditCourseDataModel editCourseDataModel = new EditCourseDataModel(name, id, institution);
        Call<EditCourseDataModel> call = apiInterface.createCoursePut(editCourseDataModel);

        call.enqueue(new Callback<EditCourseDataModel>() {
            @Override
            public void onResponse(Call<EditCourseDataModel> call, Response<EditCourseDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Course Edited Successfully", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<EditCourseDataModel> call, Throwable t) {

            }
        });
    }
}
