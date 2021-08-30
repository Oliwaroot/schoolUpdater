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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.CoursesFragment.getAllCourseData;

public class AddCourseCustomDialog extends DialogFragment {

    private EditText editText;
    private EditText editText2;
    private Button addButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course, container, false);
        editText = view.findViewById(R.id.addCourse);
        editText2 = view.findViewById(R.id.addInstitution);
        addButton = view.findViewById(R.id.add_button);
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
                if (editText.getText().toString().trim().length() == 0 || editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter both values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postCourse(editText.getText().toString(), editText2.getText().toString());
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
                }
                else{
                    Toast.makeText(getActivity(), "Error adding course", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<AddCourseDataModel> call, Throwable t) {

            }
        });

    }
}
