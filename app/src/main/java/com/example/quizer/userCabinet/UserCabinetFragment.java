/*
package com.example.quizer.userCabinet;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quizer.R;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("ConstantConditions")
public class UserCabinetFragment extends Fragment {

    private final int REQUEST_CAMERA = 0;
    private final int REQUEST_PHOTO = 1;

    private final int REQUEST_PERMISSION = 2;

    private File photoFile;
    ImageView photoView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_cabinet, container, false);
            //setting user info fields.
            //User user = Repository.getInstance(getActivity()).getUser();
            TextView nicknameTextView = v.findViewById(R.id.nickname_text);
           // nicknameTextView.setText(user.getNickname());
            TextView ratingTextView = v.findViewById(R.id.rating_text);
            //String rating = getString(R.string.rating_text, user.getRating());
           // ratingTextView.setText(rating);
            photoView = v.findViewById(R.id.user_photo_view);
            //photoFile = Repository.getInstance(getActivity()).getPhotoFile(user);
            if (photoFile.exists()) {
                updateUserPhoto();
            }

            final android.app.AlertDialog.Builder builder = getChosePhotoDialog();

            //Setting for choosing photo button.
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

        return v;
    }

    //alertDialog for choosing photo and creating right intent.
    private AlertDialog.Builder getChosePhotoDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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
                    int permissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        getPhotoFromGallery();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSION);
                    }
                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }

            }
        });
        return builder;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getPhotoFromGallery();
        }
    }

    private void updateUserPhoto() {

        Glide.with(getActivity()).load(photoFile).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .centerCrop()
                .into(photoView);
    }

    private void getPhotoFromGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PHOTO);
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
            try {
                onSelectFromGalleryResult(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void onSelectFromGalleryResult(Intent data) throws IOException {
        Uri selectedImage = data.getData();
        String chosenPicPath = getPath(selectedImage);
        File sourceLocation = new File(chosenPicPath);
        copyFile(sourceLocation, photoFile);
        updateUserPhoto();
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private void copyFile(File from, File to) throws IOException {
        InputStream in = new FileInputStream(from);
        OutputStream out = new FileOutputStream(to);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
    }


    private void onCaptureImageResult() {
        updateUserPhoto();
    }

}
*/
