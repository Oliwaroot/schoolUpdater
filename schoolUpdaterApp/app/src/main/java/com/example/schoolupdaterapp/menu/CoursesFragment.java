package com.example.schoolupdaterapp.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.schoolupdaterapp.R;
import com.example.schoolupdaterapp.menu.courseactions.AddCourseCustomDialog;
import com.example.schoolupdaterapp.menu.courseactions.CourseRecyclerViewAdapter;
import com.example.schoolupdaterapp.menu.courseactions.CourseResponseMethod;
import com.example.schoolupdaterapp.menu.courseactions.GetCoursesDataModel;
import com.example.schoolupdaterapp.menu.institutionactions.InstitutionRecyclerViewAdapter;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.InstitutionsFragment.institutionNames;
import static com.example.schoolupdaterapp.menu.courseactions.AddCourseCustomDialog.spinnerInstitution;

public class CoursesFragment extends Fragment {
    View v2;
    public static RecyclerView recyclerView2;
    private FloatingActionButton floatingActionButton;
    public static CourseRecyclerViewAdapter recyclerViewAdapter2;
    public static ArrayList<GetCoursesDataModel> courseModel;
    private static Spinner spinner2;
    public static ArrayList<String> courseList;

    public CoursesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v2 = inflater.inflate(R.layout.courses_fragment,container,false);
        recyclerView2 = (RecyclerView) v2.findViewById(R.id.course_recycler);
        floatingActionButton = v2.findViewById(R.id.floating_add_course);
        spinner2 = (Spinner) v2.findViewById(R.id.courses_inst_spinner);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AddCourseCustomDialog dialog = new AddCourseCustomDialog();
                dialog.show(getChildFragmentManager(),"Custom Dialog 2");
            }
        });
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseModel = new ArrayList<GetCoursesDataModel>();
        courseList = new ArrayList<>();
        recyclerViewAdapter2 = new CourseRecyclerViewAdapter(getContext(),courseModel);
        getAllCourseData();

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                recyclerViewAdapter2.getQueryFilter2().filter((CharSequence) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setHasOptionsMenu(true);
        return v2;
    }

    public static void getAllCourseData() {
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        Call<CourseResponseMethod> call = apiInterface.getCourseResponses();
        call.enqueue(new Callback<CourseResponseMethod>() {
            @Override
            public void onResponse(Call<CourseResponseMethod> call, Response<CourseResponseMethod> response) {
                if(response.isSuccessful()){
                    //CourseRecyclerViewAdapter recyclerViewAdapter = new CourseRecyclerViewAdapter(getContext(),courseModel);
                    CourseResponseMethod responseMethod = response.body();
                    assert responseMethod != null;
                    assert response.body() != null;
                    courseModel = responseMethod.getCourses();
                    recyclerViewAdapter2.seteClasses(courseModel);

                    for (int i=0; i<courseModel.size(); i++){
                        courseList.add(recyclerViewAdapter2.geteClasses().get(i).getInstitutionName() +" " +
                                recyclerViewAdapter2.geteClasses().get(i).getName());
                    }

                    List<String> list2 = new ArrayList<String>();
                    list2.add("All");
                    list2.addAll(institutionNames);
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(recyclerView2.getContext(), android.R.layout.simple_spinner_item, list2);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(arrayAdapter2);
                    recyclerView2.setAdapter(recyclerViewAdapter2);

                }
            }

            @Override
            public void onFailure(Call<CourseResponseMethod> call, Throwable t) {
                System.out.println("Failed");
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
                recyclerViewAdapter2.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
