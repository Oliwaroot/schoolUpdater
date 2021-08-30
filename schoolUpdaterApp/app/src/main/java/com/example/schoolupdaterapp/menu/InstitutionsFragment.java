package com.example.schoolupdaterapp.menu;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.institutionactions.AddInstitutionDataModel;
import com.example.schoolupdaterapp.menu.institutionactions.GetInstitutionDataModel;
import com.example.schoolupdaterapp.menu.institutionactions.InstitutionRecyclerViewAdapter;
import com.example.schoolupdaterapp.menu.institutionactions.InstitutionResponseMethod;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstitutionsFragment extends Fragment {

    View v;
    private static RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    static InstitutionRecyclerViewAdapter recyclerViewAdapter;
    private static ArrayList<GetInstitutionDataModel> institutionModels;
    AlertDialog dialog;

    public InstitutionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.institutions_fragment,container,false);
        recyclerView = v.findViewById(R.id.institution_recycler);
        floatingActionButton = v.findViewById(R.id.floating_add);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.add_institution, null);
        EditText editText = view.findViewById(R.id.editText);
        Button addButton = view.findViewById(R.id.add_button);
        Button cancelButton =  view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
               dialog.dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().isEmpty() || editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postInstitution(editText.getText().toString());
                editText.setText("");
            }
        });

        builder.setView(view);
        dialog = builder.create();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        institutionModels = new ArrayList<GetInstitutionDataModel>();
        recyclerViewAdapter = new InstitutionRecyclerViewAdapter(getContext(),institutionModels);
        getAllData();
        setHasOptionsMenu(true);
        return v;
    }

    public static void getAllData() {
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        Call<InstitutionResponseMethod> call = apiInterface.getAllResponses();
        call.enqueue(new Callback<InstitutionResponseMethod>() {
            @Override
            public void onResponse(Call<InstitutionResponseMethod> call, Response<InstitutionResponseMethod> response) {
                if(response.isSuccessful()){
                    //InstitutionRecyclerViewAdapter recyclerViewAdapter = new InstitutionRecyclerViewAdapter(getContext(),institutionModels);
                    InstitutionResponseMethod responseMethod = response.body();
                    assert responseMethod != null;
                    assert response.body() != null;
                    institutionModels = responseMethod.getResponseMethods();
                    recyclerViewAdapter.seteClasses(institutionModels);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<InstitutionResponseMethod> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }

    private void postInstitution(String name){
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        AddInstitutionDataModel addInstitutionDataModel = new AddInstitutionDataModel(name);
        Call<AddInstitutionDataModel> call = apiInterface.createPost(addInstitutionDataModel);

        call.enqueue(new Callback<AddInstitutionDataModel>() {
            @Override
            public void onResponse(Call<AddInstitutionDataModel> call, Response<AddInstitutionDataModel> response) {
                if(response.code() == 400){
                    Toast.makeText(getActivity(), "Institution Already Exists", Toast.LENGTH_SHORT).show();
                }
                if(response.code() == 200){
                    Toast.makeText(getActivity(), "Institution Added Successfully", Toast.LENGTH_SHORT).show();
                    getAllData();
                    //call adapter and get items list, add item to list
                    //notifyItemAdded (index of last item in list)
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AddInstitutionDataModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.searching_action);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}


