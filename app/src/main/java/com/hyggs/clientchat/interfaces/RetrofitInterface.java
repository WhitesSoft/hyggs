package com.hyggs.clientchat.interfaces;

import com.hyggs.clientchat.models.ChangeOrCreatePassResponse;
import com.hyggs.clientchat.models.GetTermsAndConditionsResponse;
import com.hyggs.clientchat.models.GetTokenResponse;
import com.hyggs.clientchat.models.LoginResponse;
import com.hyggs.clientchat.models.RegisterResponse;
import com.hyggs.clientchat.models.UploadFileResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

//    @FormUrlEncoded
//    @POST("security/LoginSupplier")
//    Call<LoginResponse> loginSuppler(
//            @Field("key") String key,
//            @Field("email_usuario") String idServicio,
//            @Field("clave") String idCliente,
//            @Field("platform") String platform,
//            @Field("ip_cliente") String ipCliente,
//            @Field("id_project") String idProject);
//
//    @FormUrlEncoded
//    @POST("security/RegisterSuppliers")
//    Call<RegisterResponse> registerSupplier(
//            @Field("key") String key,
//            @Field("email_usuario") String emailUser,
//            @Field("platform") String platform,
//            @Field("ip_cliente") String ipClient,
//            @Field("id_project") String idProject,
//            @Field("first_name") String firstName,
//            @Field("company_name") String companyName,
//            @Field("last_name") String lastName,
//            @Field("signature") String signature,
//            @Field("phone") String phone);

    @FormUrlEncoded
    @POST("security/LoginClients")
    Call<LoginResponse> loginClient(
            @Field("key") String key,
            @Field("email_usuario") String email,
            @Field("clave") String clave,
            @Field("platform") String platform,
            @Field("ip_cliente") String ip,
            @Field("id_project") String idProject);

    @FormUrlEncoded
    @POST("security/RegisterClients")
    Call<RegisterResponse> registerClient(
            @Field("key") String key,
            @Field("email_usuario") String email,
            @Field("platform") String platform,
            @Field("ip_cliente") String ip,
            @Field("id_project") String idProject,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("signature") String signature,
            @Field("phone") String phone);

    @FormUrlEncoded
    @POST("index/terms_and_conditions")
    Call<GetTermsAndConditionsResponse> getTermsAndConditions(
            @Field("key") String key);

    @FormUrlEncoded
    @POST("security/ChangePassword")
    Call<ChangeOrCreatePassResponse> changePassword(
            @Field("key") String key,
            @Field("id_user") String idUser,
            @Field("token") String token,
            @Field("password") String password,
            @Field("id_project") String idProject);


    @Multipart
    @POST("index/upload_files")
    Call<UploadFileResponse> uploadFile(@Part("key") RequestBody key,
                                        @Part("id_user") RequestBody id_usuario,
                                        @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("security/GetTokenEmail")
    Call<GetTokenResponse> getTokenEmail(
            @Field("key") String key,
            @Field("id_project") String idProject,
            @Field("email") String email);

}