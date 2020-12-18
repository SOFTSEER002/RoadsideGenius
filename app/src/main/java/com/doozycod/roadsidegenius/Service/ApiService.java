package com.doozycod.roadsidegenius.Service;


import com.doozycod.roadsidegenius.Model.AddCustomerNumberModel.VerifyOTPModel;
import com.doozycod.roadsidegenius.Model.AdminRegisterModel;
import com.doozycod.roadsidegenius.Model.Company.CompanyModel;
import com.doozycod.roadsidegenius.Model.Customer.CustomerLoginModel;
import com.doozycod.roadsidegenius.Model.DriverList.DriversListModel;
import com.doozycod.roadsidegenius.Model.DriverLogin.DriverLoginModel;
import com.doozycod.roadsidegenius.Model.JobList.JobsListModel;
import com.doozycod.roadsidegenius.Model.OTP.OTPModel;
import com.doozycod.roadsidegenius.Model.ServiceList.ServiceListModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//  api Interface for Retrofit
public interface ApiService {

    //    admin Login
    @POST("admin/login.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> adminLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_id") String device_id,
            @Field("push_token") String push_token);

    //    Customer Login
    @POST("customer/login.php")
    @FormUrlEncoded
    Call<CustomerLoginModel> customerLogin(
            @Field("phone") String number);

    //    get unassigned jobs
    @POST("job/unassigned.php")
    @FormUrlEncoded
    Call<JobsListModel> getJobsList(
            @Field("jwt") String jwt);

    //    Customer Login
    @POST("customer/add-number.php")
    @FormUrlEncoded
    Call<VerifyOTPModel> addCustomerNumber(
            @Field("jwt") String jwt,
            @Field("number") String number);

    //    Service List
    @POST("service/list-all.php")
    @FormUrlEncoded
    Call<ServiceListModel> serviceList(
            @Field("jwt") String jwt);


    //    Customer Login
    @POST("customer/verify.php")
    @FormUrlEncoded
    Call<OTPModel> verifyOTP(
            @Field("jwt") String jwt,
            @Field("otp") String otp,
            @Field("device_id") String device_id,
            @Field("push_token") String push_token);

    //    Service Add
    @POST("service/add.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> addService(
            @Field("jwt") String jwt,
            @Field("type") String type,
            @Field("cost") String cost,
            @Field("description") String description);


    //    Service Add
    @POST("job/create.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> createJobRequest(
            @Field("jwt") String jwt,
            @Field("customer_id") String customer_id,
            @Field("customer_name") String customer_name,
            @Field("customer_number") String customer_number,
            @Field("customer_pickup") String customer_pickup,
            @Field("customer_dropoff") String customer_dropoff,
            @Field("customer_email") String customer_email,
            @Field("service_needed") String service_needed,
            @Field("customer_notes") String customer_notes,
            @Field("amount_quoted") String amount_quoted);


    //    Assign new Job
    @POST("admin/assign-job.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> assignJob(
            @Field("jwt") String jwt,
            @Field("job_id") String job_id,
            @Field("driver_id") String driver_id,
            @Field("dispatch_date") String dispatch_date,
            @Field("site") String site,
            @Field("eta") String eta,
            @Field("status") String status,
            @Field("vehicle_make") String vehicle_make,
            @Field("vehicle_model") String vehicle_model,
            @Field("vehicle_color") String vehicle_color,
            @Field("dispatched") String dispatched,
            @Field("total_job_time") String total_job_time,
            @Field("total_miles") String total_miles,
            @Field("invoice_total") String invoice_total,
            @Field("comments") String comments,
            @Field("truck") String truck
    );


    //    company register
    @POST("company/register.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> registerCompany(
            @Field("jwt") String jwt,
            @Field("email") String email,
            @Field("number") String number,
            @Field("name") String name,
//            @Field("vendor_id") String vendor_id,
            @Field("l_9_forms") String l_9_forms,
            @Field("w_9_forms") String w_9_forms);


    //    company register
    @POST("driver/login.php")
    @FormUrlEncoded
    Call<DriverLoginModel> loginDriver(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_id") String device_id,
            @Field("push_token") String push_token);

    //    driver register
    @POST("driver/register.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> registerDriver(
            @Field("jwt") String jwt,
            @Field("email") String email,
            @Field("number") String number,
            @Field("name") String name,
            @Field("vendor_id") String vendor_id,
//            @Field("driver_id") String driver_id,
            @Field("driver_address") String driver_address,
            @Field("driver_zipcode") String driver_zipcode,
            @Field("service_area") String service_area,
            @Field("pay_per_job") String pay_per_job,
            @Field("service_vehicle_type") String service_vehicle_type,
            @Field("service_vehicle_model") String service_vehicle_model,
            @Field("service_vehicle_year") String service_vehicle_year,
            @Field("service_vehicle_make") String service_vehicle_make
    );

    //    driver register
    @POST("driver/edit.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> editDriverDetails(
            @Field("jwt") String jwt,
            @Field("id") String id,
            @Field("email") String email,
            @Field("number") String number,
            @Field("name") String name,
            @Field("vendor_id") String vendor_id,
            @Field("driver_address") String driver_address,
            @Field("driver_zipcode") String driver_zipcode,
            @Field("service_area") String service_area,
            @Field("pay_per_job") String pay_per_job,
            @Field("service_vehicle_type") String service_vehicle_type,
            @Field("service_vehicle_model") String service_vehicle_model,
            @Field("service_vehicle_year") String service_vehicle_year,
            @Field("service_vehicle_make") String service_vehicle_make
    );

    //    get Company list
    @POST("driver/delete.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> deleteDriver(
            @Field("jwt") String jwt,
            @Field("id") String id);

    //    delete service
    @POST("service/delete.php")
    @FormUrlEncoded
    Call<AdminRegisterModel> deleteService(
            @Field("jwt") String jwt,
            @Field("id") String id);

    //    get Company list
    @POST("company/list-all.php")
    @FormUrlEncoded
    Call<CompanyModel> getCompanyLists(
            @Field("jwt") String jwt);

    //    get Drivers List
    @POST("driver/list-all.php")
    @FormUrlEncoded
    Call<DriversListModel> getDriverList(
            @Field("jwt") String jwt);

}
