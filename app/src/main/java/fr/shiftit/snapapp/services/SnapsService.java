package fr.shiftit.snapapp.services;

import java.util.List;

import fr.shiftit.snapapp.model.ApiId;
import fr.shiftit.snapapp.model.Snap;
import fr.shiftit.snapapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SnapsService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id);

    @POST("users")
    Call<ApiId> createUser(@Body User user);

    @PUT("users/{id}")
    Call<Void> updateUser(@Path("id") String id,@Body User user);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);

    @GET("albums/{userId}/{albumId}")
    Call<List<Snap>> getSnaps(@Path("userId") String userId, @Path("albumId") String albumId);

    @POST("albums/{userId}/{albumId}")
    Call<ApiId> createSnap(@Path("userId") String userId,@Path("albumId") String albumId,@Body Snap snap);
}
