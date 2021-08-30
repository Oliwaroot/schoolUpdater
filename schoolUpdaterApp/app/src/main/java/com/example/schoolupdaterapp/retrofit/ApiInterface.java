package com.example.schoolupdaterapp.retrofit;

import com.example.schoolupdaterapp.menu.courseactions.AddCourseDataModel;
import com.example.schoolupdaterapp.menu.courseactions.CourseResponseMethod;
import com.example.schoolupdaterapp.menu.courseactions.DeleteCourseDataModel;
import com.example.schoolupdaterapp.menu.courseactions.EditCourseDataModel;
import com.example.schoolupdaterapp.menu.institutionactions.AddInstitutionDataModel;
import com.example.schoolupdaterapp.menu.institutionactions.DeleteInstitutionDataModel;
import com.example.schoolupdaterapp.menu.institutionactions.EditInstitutionDataModel;
import com.example.schoolupdaterapp.menu.institutionactions.InstitutionResponseMethod;
import com.example.schoolupdaterapp.menu.studentactions.AddStudentDataModel;
import com.example.schoolupdaterapp.menu.studentactions.ChangeCourseDataModel;
import com.example.schoolupdaterapp.menu.studentactions.ChangeInstitutionDataModel;
import com.example.schoolupdaterapp.menu.studentactions.DeleteStudentDataModel;
import com.example.schoolupdaterapp.menu.studentactions.EditStudentDataModel;
import com.example.schoolupdaterapp.menu.studentactions.StudentResponseMethod;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @GET("institution")
    Call<InstitutionResponseMethod> getAllResponses();

    @POST("institution")
    Call<AddInstitutionDataModel> createPost(@Body AddInstitutionDataModel addInstitutionsDataModel);

    @PUT("institution")
    Call<EditInstitutionDataModel> createPut(@Body EditInstitutionDataModel editInstitutionDataModel);

    @POST("institution/deleteInstitution")
    Call<DeleteInstitutionDataModel> createDeletion(@Body DeleteInstitutionDataModel deleteInstitutionDataModel);

    @GET("course")
    Call<CourseResponseMethod> getCourseResponses();

    @POST("course")
    Call<AddCourseDataModel> createCoursePost(@Body AddCourseDataModel addCourseDataModel);

    @PUT("course")
    Call<EditCourseDataModel> createCoursePut(@Body EditCourseDataModel editCourseDataModel);

    @POST("course/deleteCourse")
    Call<DeleteCourseDataModel> createCourseDeletion(@Body DeleteCourseDataModel deleteCourseDataModel);

    @GET("student")
    Call<StudentResponseMethod> getStudentResponses();

    @POST("student")
    Call<AddStudentDataModel> createStudentPost(@Body AddStudentDataModel addStudentDataModel);

    @PUT("student")
    Call<EditStudentDataModel> createStudentPut(@Body EditStudentDataModel editStudentDataModel);

    @POST("student/deleteStudent")
    Call<DeleteStudentDataModel> createStudentDeletion(@Body DeleteStudentDataModel deleteStudentDataModel);

    @POST("student/changeCourse")
    Call<ChangeCourseDataModel> changeStudentCourse(@Body ChangeCourseDataModel changeDataModel);

    @POST("student/changeCourseInstitution")
    Call<ChangeInstitutionDataModel> changeStudentInstitution(@Body ChangeInstitutionDataModel changeInstitutionDataModel);
}
