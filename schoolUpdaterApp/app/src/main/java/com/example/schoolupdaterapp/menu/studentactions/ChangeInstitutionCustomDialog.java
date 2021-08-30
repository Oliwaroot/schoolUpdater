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

public class ChangeInstitutionCustomDialog extends DialogFragment {
    private EditText editText;
    private EditText editText2;
    private Button acceptButton;
    private Button cancelButton;
    private String id;


    public ChangeInstitutionCustomDialog(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_institution, container, false);
        editText = view.findViewById(R.id.course_name_inst_change);
        editText2 = view.findViewById(R.id.inst_name_inst_change);
        acceptButton = view.findViewById(R.id.accept_change_inst_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer idVal = Integer.parseInt(id);
                changeStudentInstitution(idVal, editText.getText().toString(), editText2.getText().toString());
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
                    Toast.makeText(getActivity(), "Transfer failed.", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<ChangeInstitutionDataModel> call, Throwable t) {

            }
        });
    }

}
