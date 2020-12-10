package fr.shiftit.snapapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        //retour
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result",result);
//        setResult(Activity.RESULT_OK,returnIntent);
//        finish();
//
//        //uri -> image
//        Uri selectedImage = imageUri;
//        getContentResolver().notifyChange(selectedImage, null);
//        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
//        ContentResolver cr = getContentResolver();
//        Bitmap bitmap;
//        try {
//            bitmap = android.provider.MediaStore.Images.Media
//                    .getBitmap(cr, selectedImage);
//
//            imageView.setImageBitmap(bitmap);
//            Toast.makeText(this, selectedImage.toString(),
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
//                    .show();
//            Log.e("Camera", e.toString());
//        }

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}