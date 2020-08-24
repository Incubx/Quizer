package com.example.quizer.userCabinet;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quizer.R;
import com.example.quizer.database.Repository;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class UserCabinetFragment extends Fragment {

    private final int REQUEST_CAMERA = 0;
    private final int REQUEST_PHOTO = 1;
    private File photoFile;
    ImageView photoView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_cabinet, container, false);
        try {
            //setting user info fields.
            User user = Repository.getInstance(getActivity()).getUser();
            TextView nicknameTextView = v.findViewById(R.id.nickname_text);
            nicknameTextView.setText(user.getNickname());
            TextView ratingTextView = v.findViewById(R.id.rating_text);
            String rating = getString(R.string.rating_text, user.getRating());
            ratingTextView.setText(rating);
            photoView = v.findViewById(R.id.user_photo_view);
            photoFile = Repository.getInstance(getActivity()).getPhotoFile(user);
            if (photoFile.exists()) {
                updateUserPhoto();
            }

            //intent for getting picture.
            final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (items[i].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri = FileProvider.getUriForFile(getActivity(),
                                "com.example.quizer.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } else if (items[i].equals("Choose from Library")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        Uri uri = FileProvider.getUriForFile(getActivity(),
                                "com.example.quizer.fileprovider",
                                photoFile);
                        startActivityForResult(intent, REQUEST_PHOTO);
                    } else if (items[i].equals("Cancel")) {
                        dialogInterface.dismiss();
                    }

                }
            });

            final Button button = v.findViewById(R.id.create_photo_button);
            final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            PackageManager packageManager = getActivity().getPackageManager();
            button.setEnabled(captureImage.resolveActivity(packageManager) != null);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.show();
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    private void updateUserPhoto() {

        Glide.with(getActivity()).load(photoFile).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .centerCrop()
                .into(photoView);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CAMERA) {
            onCaptureImageResult();
        }
        if (requestCode == REQUEST_PHOTO) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),
                        data.getData());
                saveBitmap(bitmap);
                updateUserPhoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void onCaptureImageResult() {
        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
        saveBitmap(bitmap);
        updateUserPhoto();
    }

    private void saveBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        FileOutputStream fo;
        try {
            photoFile.createNewFile();
            fo = new FileOutputStream(photoFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
