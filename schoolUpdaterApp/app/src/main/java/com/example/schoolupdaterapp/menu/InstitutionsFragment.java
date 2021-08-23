package com.example.schoolupdaterapp.menu;

import android.app.Dialog;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.schoolupdaterapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InstitutionsFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private ArrayList<DataModel> institutionModels;


    public InstitutionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.institutions_fragment,container,false);
        recyclerView = v.findViewById(R.id.institution_recycler);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setLayoutManager(layoutManager);
        institutionModels = new ArrayList<DataModel>();
//        institutionModels.add(new DataModel("Hello There"));


        Log.i("tag", "Let's play"+institutionModels.size());
        getAllData();

        return v;
    }

    private void getAllData() {
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        Call<ResponseMethod> call = apiInterface.getAllResponses();
        call.enqueue(new Callback<ResponseMethod>() {
            @Override
            public void onResponse(Call<ResponseMethod> call, Response<ResponseMethod> response) {

//                recyclerView = (RecyclerView) v.findViewById(R.id.institution_recycler);
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                if(response.isSuccessful()){
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),institutionModels);
                    ResponseMethod responseMethod = response.body();
                    assert responseMethod != null;
                    assert response.body() != null;
                    institutionModels = responseMethod.getResponseMethods();
//                    institutionModels = responseMethod.setResponseMethods(responseMethod.getResponseMethods());
                    recyclerViewAdapter.seteClasses(institutionModels);
                    Log.i("log","instMod"+institutionModels.size());
                    recyclerView.setAdapter(recyclerViewAdapter);

                }
            }

            @Override
            public void onFailure(Call<ResponseMethod> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        institutionModel = new ArrayList<>();
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));
//        institutionModel.add(new EntityModelClass("Meru High","courses: 10","students: 80"));

    }
}
