package com.example.viralyapplication.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.api.NewsFeedApi;
import com.example.viralyapplication.repository.model.CreatePostModel;
import com.example.viralyapplication.utility.BitmapToFile;
import com.example.viralyapplication.utility.Constant;
import com.example.viralyapplication.utility.NetworkProfile;
import com.example.viralyapplication.utility.ReadPathUtil;
import com.example.viralyapplication.utility.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    private ConstraintLayout cntrs;

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
        edtContent = findViewById(R.id.edt_status_user);
        cntrs = findViewById(R.id.ctrs_image);
        ivCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_func_camera:
                selectImage(CreatePostActivity.this);
                cntrs.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void selectImage(Context context) {
        final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.choose_from_gallery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    ActivityCompat.requestPermissions(CreatePostActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else {
                    isPicked = 0;
                    ActivityCompat.requestPermissions(CreatePostActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String requiredPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int checkVal = checkCallingOrSelfPermission(requiredPermission);
        if (checkVal != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreatePostActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
                Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                ivUserPost.setImageBitmap(selectedImage);
                mImageUri = BitmapToFile.getInstance().getImageUri(CreatePostActivity.this, selectedImage);
                isPicked = 1;
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();
            ivUserPost.setImageURI(mImageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && isPicked == 0) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (isPicked == 1) {

                } else {
                    Toast.makeText(this, getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private void postImage() {
        showProgressDialog();
        NewsFeedApi mApi = NetworkProfile.getRetrofitInstance().create(NewsFeedApi.class);
        File file = new File(ReadPathUtil.getPath(CreatePostActivity.this, mImageUri));
        RequestBody requestFile =  RequestBody.create(MediaType.parse(getContentResolver().getType(mImageUri)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(Constant.IMAGE, file.getName(), requestFile);
        Call<CreatePostModel> call = mApi.uploadFile(body);
        call.enqueue(new Callback<CreatePostModel>() {
            @Override
            public void onResponse(Call<CreatePostModel> call, Response<CreatePostModel> response) {
                dismissProgressDialog();
                if (response.code() == Constant.IS_SUCCESS){
                    Log.e(TAG, "onResponse: ");
                }else {
                    Log.e(TAG, response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<CreatePostModel> call, Throwable t) {
                dismissProgressDialog();
                Log.e("onFailure ", "" + t.getMessage());
            }
        });

    }

//    private void createPostuser(String caption, String url){
//        showProgressDialog();
//        NewsFeedApi mApi = NetworkProfile.getRetrofitInstance().create(NewsFeedApi.class);
//        Map<String, String> requestBody =  new HashMap<>();
//        mApi.createPost(caption, requestBody).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.code() == Constant.IS_SUCCESS){
//                    Log.e("aaa", "aaa");
//                }else {
//                    Log.e("fail", response.message());
//                }
//            }
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Utils.showAlertDialogOk(CreatePostActivity.this, getString(R.string.error_txt), t.getMessage(), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//            }
//        });
//    }


    private void createPost() {
        String content = edtContent.getText().toString().trim();
//        createPostuser(content, "");
    }


    @Override
    public void onClickTitleRight() {
        super.onClickTitleRight();
        createPost();
    }

}