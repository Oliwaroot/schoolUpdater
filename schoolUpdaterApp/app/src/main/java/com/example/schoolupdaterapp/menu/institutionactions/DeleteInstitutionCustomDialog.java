package com.example.schoolupdaterapp.menu.institutionactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.InstitutionsFragment.getAllData;

public class DeleteInstitutionCustomDialog extends DialogFragment {

    private Button deleteButton;
    private Button cancelButton;
    private String id;

    public DeleteInstitutionCustomDialog(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_institution, container, false);
        deleteButton = view.findViewById(R.id.delete_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer idVal = Integer.parseInt(id);
                deleteInstitution(idVal);
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

    private void deleteInstitution(Integer id){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        DeleteInstitutionDataModel deleteInstitutionDataModel = new DeleteInstitutionDataModel(id);
        Call<DeleteInstitutionDataModel> call = apiInterface.createDeletion(deleteInstitutionDataModel);

        call.enqueue(new Callback<DeleteInstitutionDataModel>() {
            @Override
            public void onResponse(Call<DeleteInstitutionDataModel> call, Response<DeleteInstitutionDataModel> response) {
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Institution has been deleted", Toast.LENGTH_SHORT).show();
                    getAllData();
                }
                else{
                    Toast.makeText(getActivity(), "Error in deletion. Institution has courses.", Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<DeleteInstitutionDataModel> call, Throwable t) {

            }
        });

    }
}
