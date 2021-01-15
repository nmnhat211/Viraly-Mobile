package com.example.viralyapplication.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.viralyapplication.R;
import com.example.viralyapplication.repository.model.UserModelLogin;
import com.example.viralyapplication.ui.activity.MyApplication;
import com.example.viralyapplication.ui.activity.SignInActivity;
import com.example.viralyapplication.ui.activity.SignUpActivity;
import com.example.viralyapplication.ui.fragment.NewsFeedFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Response;

public class Utils {
    public static MyApplication mContext = MyApplication.instance;
    private DialogListener mDialogListener;
    public static String mUsername = "";
    public static String mPassword = "";
    private static final int LONG_DELAY = 3500; // 3.5 seconds
    private static final int SHORT_DELAY = 2000;
    public static String uid= "";



    public static int getDefaultImage(){
        return R.drawable.avatar;
    }
    public static int getDefaultLoading(){
        return R.drawable.loading_rect_thumb;
    }


    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        Utils.uid = uid;
    }


    private static Utils sGlobalVariable;

    public static Utils getInstance() {
        if (sGlobalVariable == null) {
            sGlobalVariable = new Utils();
        }
        return sGlobalVariable;
    }

    public static void showKeyBoard(View view, boolean isShow) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    public static void goToSigUpActivity(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    public static void goToSigInActivity(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    public static void goToNewFeedFragment(Context context, UserModelLogin data) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtra(NewsFeedFragment.KEY_USER_DATA, data);
        context.startActivity(intent);
    }

    public static void goToNewsFeedFragment(){
        Intent intent = new Intent(MyApplication.instance, NewsFeedFragment.class);
        MyApplication.instance.startActivity(intent);
    }

    public static void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    public static boolean isNull(String string) {
        return string.isEmpty() || string.equals("null");
    }


    public static String isError(int status) {
        String errorMessages = "null";
        switch (status) {
            case Constant.IS_SUCCESS:
                break;
            case Constant.IS_FORBIDDEN:
                errorMessages = Resources.getSystem().getString(R.string.account_baned_text);
                break;
            default:
                return errorMessages;
        }
        return errorMessages;
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

    public static String convertFloat2f(float context) {
        DecimalFormat format = new DecimalFormat("#.00");
        return format.format(context);
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void deleteAlert(Context context, String message, String title, int position, String id, DialogListener dialogListener) {
        mDialogListener = dialogListener;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvDeleteTitle = dialog.findViewById(R.id.tv_title_dialog);
        TextView tvDeleteMessage = dialog.findViewById(R.id.tv_content_dialog);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        Button btnPositive = dialog.findViewById(R.id.btn_delete);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogListener.onAcceptClickListener(dialog, position, id);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        tvDeleteTitle.setText(title);
        tvDeleteMessage.setText(message);
        dialog.show();
    }


    public static void showAlertDialog(Context context, String title, String content) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void showAlertDialogOk(Context context, String title, String content, DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...
                    }
                }).show();
    }

    public static void sendBroadCastLogin(Context context, String mail, String password) {
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent(SignInActivity.KEY_AUTO_LOGIN);
        intent.putExtra(SignInActivity.KEY_EMAIL, mail);
        intent.putExtra(SignInActivity.KEY_PASSWORD, password);
        broadcaster.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, Intent intent) {
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(context);
        broadcaster.sendBroadcast(intent);
    }

    public static void saveRememberAccount(Context context, Boolean value, String username, String password) {
        SharedPreferences pref = context.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_PRIVATE);
        if (value) {
            pref.edit()
                    .putBoolean(Constant.KEY_REMEMBER_ME, true)
                    .putString(Constant.KEY_USERNAME, username)
                    .putString(Constant.KEY_PASSWORD, password)
                    .apply();
        } else {
            pref.edit()
                    .clear()
                    .apply();

        }
    }

    public static boolean isNotWelcome(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(Constant.KEY_REMEMBER_ME, false);
    }

    public static String getEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_PRIVATE);
        mUsername = pref.getString(Constant.KEY_USERNAME, "null");
        return mUsername;
    }

    public static String getPassword(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_PRIVATE);
        mPassword = pref.getString(Constant.KEY_PASSWORD, "null");
        return mPassword;
    }

    public static void setEnable(View view, boolean visible) {
        view.setAlpha(1f);
        view.setEnabled(true);
        if (!visible) {
            view.setAlpha(0.5f);
            view.setEnabled(false);
        }
    }

    public static void showToast(Context context, int content, boolean isLongDelay) {
        Toast toast;
        if (isLongDelay) {
            toast = Toast.makeText(context, content, LONG_DELAY);
        } else {
            toast = Toast.makeText(context, content, SHORT_DELAY);
        }
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



    public static void handleErrorMessages(Context context, Response<?> response, int contentDialog) {
        ErrorMessageModel errorMessages = ErrorUtils.parseError(response);
        if (errorMessages.message() != null) {
            Utils.showAlertDialogOk(context,
                    context.getString(R.string.infor_txt),
                    errorMessages.message(),
                    (dialogInterface, i) -> dialogInterface.dismiss());
            Log.d("Error", errorMessages.message());
        } else {
            Utils.showAlertDialogOk(context,
                    context.getString(R.string.error_txt),
                    context.getString(contentDialog),
                    (dialogInterface, i) -> dialogInterface.dismiss());
        }
    }
    public static String cutSymb(String string){
        return string.replaceAll("[-+.^:,]","");
    }

    public static void loadCircleView(Context context, String img_url, ImageView imgView, int roundingRadius) {
        RequestOptions requestOptions = new RequestOptions()
                .transforms(new RoundedCorners(roundingRadius))
                .error(getDefaultImage())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(img_url)
                .apply(requestOptions)
                .into(imgView);

    }

    public static void loadView(Context context, String img_url, ImageView imgView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(getDefaultImage())
                .error(getDefaultImage())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(img_url)
                .apply(requestOptions)
                .into(imgView);

    }

    public static String changeNamePathImage(String namePath) {
        String[] arrNameFile = namePath.split("\\.");
        namePath = arrNameFile[0] + System.currentTimeMillis() + "." + arrNameFile[1];
        return namePath;
    }


//    public static List<String> getContent(List<String> content){
//            List<String> url;
//            for (int i, i < content.size()){
//                url.add( "url");
//            }
//            return content;
//    }


    public static String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
