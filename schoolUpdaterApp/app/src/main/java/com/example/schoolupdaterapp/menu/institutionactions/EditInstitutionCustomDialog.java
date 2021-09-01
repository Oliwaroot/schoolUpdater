package com.example.schoolupdaterapp.menu.institutionactions;

import android.os.Bundle;
import android.util.Log;
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

import static com.example.schoolupdaterapp.menu.InstitutionsFragment.getAllData;

public class EditInstitutionCustomDialog extends DialogFragment {

    private EditText editText;
    private Button editButton;
    private Button cancelButton;
    private String id;

    public EditInstitutionCustomDialog(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_institution, container, false);
        editText = view.findViewById(R.id.editText);
        editButton = view.findViewById(R.id.edit_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().isEmpty() || editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer idVal = Integer.parseInt(id);
                putInstitution(idVal, editText.getText().toString());
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

    private void putInstitution(Integer id, String name) {
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        EditInstitutionDataModel editInstitutionDataModel = new EditInstitutionDataModel(name, id);
        Call<EditInstitutionDataModel> call = apiInterface.createPut(editInstitutionDataModel);

        call.enqueue(new Callback<EditInstitutionDataModel>() {
            @Override
            public void onResponse(Call<EditInstitutionDataModel> call, Response<EditInstitutionDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Institution Edited Successfully", Toast.LENGTH_SHORT).show();
                    getAllData();
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
            public void onFailure(Call<EditInstitutionDataModel> call, Throwable t) {

            }
        });

    }
}
