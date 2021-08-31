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
import com.example.schoolupdaterapp.menu.studentactions.AddStudentCustomDialog;
import com.example.schoolupdaterapp.menu.studentactions.GetStudentsDataModel;
import com.example.schoolupdaterapp.menu.studentactions.StudentRecyclerViewAdapter;
import com.example.schoolupdaterapp.menu.studentactions.StudentResponseMethod;
import com.example.schoolupdaterapp.retrofit.ApiInterface;
import com.example.schoolupdaterapp.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.schoolupdaterapp.menu.CoursesFragment.courseList;
import static com.example.schoolupdaterapp.menu.InstitutionsFragment.institutionNames;

public class StudentsFragment extends Fragment {
    View v3;
    public static RecyclerView recyclerView3;
    public static StudentRecyclerViewAdapter recyclerViewAdapter3;
    private FloatingActionButton floatingActionButton;
    public static ArrayList<GetStudentsDataModel> studentModel;
    private static Spinner spinner;
    private static Spinner spinner2;
    private static String instName;

    public StudentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v3 = inflater.inflate(R.layout.students_fragment, container, false);
        recyclerView3 = (RecyclerView) v3.findViewById(R.id.student_recycler);
        floatingActionButton = (FloatingActionButton) v3.findViewById(R.id.floating_add_student);
        spinner = (Spinner) v3.findViewById(R.id.students_spinner);
        spinner2 = (Spinner) v3.findViewById(R.id.students_inst_spinner);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudentCustomDialog dialog = new AddStudentCustomDialog();
                dialog.show(getChildFragmentManager(), "Custom Dialog 3");
            }
        });

        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        studentModel = new ArrayList<GetStudentsDataModel>();
        recyclerViewAdapter3 = new StudentRecyclerViewAdapter(getContext(), studentModel);
        getAllStudentData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                recyclerViewAdapter3.getQueryFilter().filter((CharSequence) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                instName = (String) adapterView.getItemAtPosition(i);
                List<String> courses = new ArrayList<>();
                if(!instName.equals("All")){
                    courses.add("All");
                }
                for (int j=0; j< courseList.size(); j++){
                    if(courseList.get(j).contains(instName)){
                        String newCourse = courseList.get(j);
                        String newCourse2 = newCourse.replace(instName, "").trim();
                        courses.add(newCourse2);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(recyclerView3.getContext(), android.R.layout.simple_spinner_item, courses);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                recyclerViewAdapter3.getQueryFilter2().filter((CharSequence) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v3;
    }

    public static void getAllStudentData() {
        ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        Call<StudentResponseMethod> call = apiInterface.getStudentResponses();
        call.enqueue(new Callback<StudentResponseMethod>() {
            @Override
            public void onResponse(Call<StudentResponseMethod> call, Response<StudentResponseMethod> response) {
                if (response.isSuccessful()) {
                    StudentResponseMethod responseMethod = response.body();
                    assert responseMethod != null;
                    assert response.body() != null;
                    studentModel = responseMethod.getStudents();
                    recyclerViewAdapter3.seteClasses(studentModel);

                    List<String> list2 = new ArrayList<String>();
                    list2.add("All");
                    list2.addAll(institutionNames);
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(recyclerView3.getContext(), android.R.layout.simple_spinner_item, list2);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(arrayAdapter2);
                    recyclerView3.setAdapter(recyclerViewAdapter3);

                }
            }

            @Override
            public void onFailure(Call<StudentResponseMethod> call, Throwable t) {
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
                recyclerViewAdapter3.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
