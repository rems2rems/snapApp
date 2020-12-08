package fr.shiftit.snapapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UsersService usersService = new UsersServiceBuilder()
                .setUrl("http://10.0.2.2:3000/api/v1/")
                .build();

        TextView textView = (TextView)view.findViewById(R.id.textview_first);
        view.findViewById(R.id.button_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try  {
                    //Response response = client.newCall(request).execute();
                    usersService.getUsers().enqueue(new Callback<List<User>>() {

                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String msg = "Users:\n";
                                    for(User user : response.body()) {
                                        msg += user.getName() + " (id:" + user.getId() + ")\n";
                                    }
                                    textView.setText(msg);
                                }
                            });
                            Log.i("mylogs","ok");
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {

                            t.printStackTrace();
                            Log.e("mylogs", t.getMessage());
                            Log.e("mylogs", "exception", t);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("mylogs",e.getMessage());
                    Log.e("mylogs","exception",e);
                }
            }
        });

        view.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try  {
                    //Response response = client.newCall(request).execute();
                    User user = new User();
                    user.setName("houra");
                    usersService.createUser(user).enqueue(new Callback<ApiId>() {

                        @Override
                        public void onResponse(Call<ApiId> call, Response<ApiId> response) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    user.setId(response.body().getId());
                                    textView.setText("nouvel user créé! (id:" + user.getId()+")");
                                }
                            });
                            Log.i("mylogs","ok");
                        }

                        @Override
                        public void onFailure(Call<ApiId> call, Throwable t) {

                            t.printStackTrace();
                            Log.e("mylogs", t.getMessage());
                            Log.e("mylogs", "exception", t);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("mylogs",e.getMessage());
                    Log.e("mylogs","exception",e);
                }
            }
        });
    }
}