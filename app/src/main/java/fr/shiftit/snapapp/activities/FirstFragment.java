package fr.shiftit.snapapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.shiftit.snapapp.util.ImageToText;
import fr.shiftit.snapapp.R;
import fr.shiftit.snapapp.services.SnapsService;
import fr.shiftit.snapapp.services.SnapsServiceBuilder;
import fr.shiftit.snapapp.model.ApiId;
import fr.shiftit.snapapp.model.Snap;
import fr.shiftit.snapapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private static final int TAKE_PICTURE = 111; //nombre random
    private Snap currentSnap;
    private String currentPhotoFile;

    SnapsService snapsService;

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

        currentSnap = null;

        snapsService = new SnapsServiceBuilder()
                .setUrl("http://10.0.2.2:3000/api/v1/")
                .build();

        TextView textView = (TextView) view.findViewById(R.id.textview_first);
        view.findViewById(R.id.button_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                refreshUsers(snapsService, textView);
            }
        });

        view.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * la photo prise sera envoyée dans la méthode onActivityResult(...)
                 */
                startCameraApp();
            }
        });
    }

    private void startCameraApp() {

        File tmpFile = null;
        try {
//        pour stockage ds la galerie (/!\ permissions)
//            getExternalStoragePublicDirectory()
            tmpFile = File.createTempFile(
                    "tmpSnap",
                    ".jpg",
                    getActivity().getExternalFilesDir(null) //getActivity().getExternalFilesDir(null)
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoFile = tmpFile.getAbsolutePath();

            Uri photoURI = FileProvider.getUriForFile(getActivity(),
                    "fr.shiftit.snapapp",
                    tmpFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, TAKE_PICTURE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postSnap(Snap snap, TextView textView) {
        try {
            //Response response = client.newCall(request).execute();
            snapsService.createSnap(snap.getUserId(), snap.getAlbumId(), snap).enqueue(new Callback<ApiId>() {

                @Override
                public void onResponse(Call<ApiId> call, Response<ApiId> response) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = "Id:" + response.body().getId();
                            textView.setText(msg);
                        }
                    });
                    Log.i("mylogs", "snap sent");
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
            Log.e("mylogs", e.getMessage());
            Log.e("mylogs", "exception", e);
        }
    }

    private void refreshUsers(SnapsService usersService, TextView textView) {
        try {
            //Response response = client.newCall(request).execute();
            usersService.getUsers().enqueue(new Callback<List<User>>() {

                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = "Users:\n";
                            for (User user : response.body()) {
                                msg += user.getName() + " (id:" + user.getId() + ")\n";
                            }
                            textView.setText(msg);
                        }
                    });
                    Log.i("mylogs", "ok");
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
            Log.e("mylogs", e.getMessage());
            Log.e("mylogs", "exception", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

//            Bitmap miniature = (Bitmap) data.getExtras().get("data");

            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap fullSizePhoto = BitmapFactory.decodeFile(currentPhotoFile,options);

            ImageView photoView = getActivity().findViewById(R.id.photo);
            photoView.setImageBitmap(fullSizePhoto);


            currentSnap = new Snap();
            currentSnap.setUserId("8h8cv9");
            currentSnap.setAlbumId("nHya2t");
            currentSnap.setImage(fullSizePhoto);
            Log.i("mylogs","base64 photo:" + currentSnap.getImageData());

            TextView textView = getActivity().findViewById(R.id.textview_first);
            postSnap(currentSnap,textView);
//            addPhotoToSnap(currentSnap,fullSizePhoto);
            //frag2 + photo
        }
    }

    private void setPic(ImageView imageView, String currentPhotoPath) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }


}