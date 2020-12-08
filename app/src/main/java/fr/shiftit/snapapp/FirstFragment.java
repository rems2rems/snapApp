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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        TextView textView = (TextView)view.findViewById(R.id.textview_first);
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:3000/api/v1/users")
                        .build();
                try  {
                    //Response response = client.newCall(request).execute();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            Log.e("mylogs",e.getMessage());
                            Log.e("mylogs","exception",e);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data = response.body().string();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    Type usersType = new TypeToken<List<User>>(){}.getType();
                                    List<User> users = gson.fromJson(data,usersType);
                                    String msg = "Users:\n";
                                    for(User user : users) {
                                        msg += user.getName() + " (id:" + user.getId() + ")\n";
                                    }
                                    textView.setText(msg);
                                }
                            });
                            Log.i("mylogs","ok");
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