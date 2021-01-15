package com.example.viralyapplication.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.api.NewsFeedApi;
import com.example.viralyapplication.repository.model.CreatePostModel;
import com.example.viralyapplication.utility.BitmapToFile;
import com.example.viralyapplication.utility.Constant;
import com.example.viralyapplication.utility.NetworkProfile;
import com.example.viralyapplication.utility.Utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends ToolbarActivity {
    ImageView ivAvatarUser, ivUserPost, ivCamera;
    TextView tvNameUser;
    EditText edtContent;
    private Uri mImageUri;
    private int mRequestCodeImage = 111;
    private NewsFeedApi mApi;
    private String mRealPath = "";

    int isPicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        initView();
        setDisplayBack(true);
        setDisplayTitle(true);
        setDisplayTitleRight(true);
        setTitleToolbar(getString(R.string.post_update_text));
        setTitleRight(getString(R.string.post_text));
    }

    private void initView() {
        ivAvatarUser = findViewById(R.id.iv_avatar_user);
        ivCamera = findViewById(R.id.iv_func_camera);
        ivUserPost = findViewById(R.id.iv_image_user_posted);
        tvNameUser = findViewById(R.id.tv_name_user_new_feed);
        edtContent = findViewById(R.id.edt_content_post);

        ivCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_func_camera:
                selectImage(CreatePostActivity.this);
        }
    }


    private void selectImage(Context context) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, mRequestCodeImage);
//        ActivityCompat.requestPermissions(CreatePostActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, mRequestCodeImage);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCodeImage && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            ivUserPost.setImageURI(uri);
            mRealPath = getRealPathFromURI(uri);
        } else {
            Log.e("onActivityResult: ", "Failed");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, mRequestCodeImage);
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void postImage() {
            showProgressDialog();
            mApi = NetworkProfile.getRetrofitInstance().create(NewsFeedApi.class);
            Call<CreatePostModel> call;
            File mFile = new File(mRealPath);
            Utils.changeNamePathImage(mRealPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", mRealPath, requestBody);
            call = mApi.uploadFile(body);
            call.enqueue(new Callback<CreatePostModel>() {
                @Override
                public void onResponse(Call<CreatePostModel> call, Response<CreatePostModel> response) {
                    dismissProgressDialog();
                    if (response.code() == Constant.IS_SUCCESS) {
                        Toast.makeText(CreatePostActivity.this, "right", Toast.LENGTH_SHORT).show();
                        Log.e("url", response.body().getUrl());
                    } else {
                        Toast.makeText(CreatePostActivity.this, "faile", Toast.LENGTH_SHORT).show();
                        Log.e("status", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<CreatePostModel> call, Throwable t) {
                    dismissProgressDialog();
                    Log.e("onFailure", t.getMessage());
                }
            });
        }

    private void createPost() {
            postImage();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


    @Override
    public void onClickTitleRight() {
        super.onClickTitleRight();
        createPost();
    }

    public static String convertDate(String content) {
        String covertDate = "";
        Date date;

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = input.parse(content);
            covertDate = output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return covertDate;
    }
}