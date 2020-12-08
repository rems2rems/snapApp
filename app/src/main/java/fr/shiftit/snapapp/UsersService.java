package fr.shiftit.snapapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsersService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/:id")
    Call<User> getUser(@Path("id") String id);

    @POST("users")
    Call<Integer> createUser(@Body User user);

    @PUT("users/:id")
    Call<Void> updateUser(@Path("id") String id,@Body User user);

    @DELETE("users/:id")
    Call<Void> deleteUser(@Path("id") String id);
}
