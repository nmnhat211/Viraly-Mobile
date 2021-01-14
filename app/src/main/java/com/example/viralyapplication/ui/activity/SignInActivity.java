package com.example.viralyapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viralyapplication.repository.api.LoginApi;
import com.example.viralyapplication.repository.model.LoginModel;
import com.example.viralyapplication.repository.model.EmailVerifyModel;
import com.example.viralyapplication.R;
import com.example.viralyapplication.ui.event.SigInEvent;
import com.example.viralyapplication.utility.Constant;
import com.example.viralyapplication.utility.EventBus;
import com.example.viralyapplication.utility.NetworkProfile;
import com.example.viralyapplication.utility.Utils;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends BaseFragmentActivity {

    private Button btnLogin, btBottom;
    private LinearLayout rlParent;
    private CheckBox cbxRememberMe;
    private TextView tvSignUpNow, tvForgotPassword;
    private TextInputLayout mTextInputEmail, mTextInputPassword;
    private EditText edtEmail, edtPassword;
    private boolean isMailEmpty = true, isPasswordEmpty = true;
    private SharedPreferences mPerPreferences;
    private String mUsername, mPassword;
    public static final String KEY_AUTO_LOGIN = "key_auto_login";
    public static final String KEY_EMAIL = "signIn_activity_email";
    public static final String KEY_PASSWORD = "signIn_activity_password";

    private BroadcastReceiver mBroadcastDataSignUp;


    private void handleBroadcast() {
        mBroadcastDataSignUp = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initView();
                String email = intent.getExtras().getString(KEY_EMAIL);
                String password = intent.getExtras().getString(KEY_PASSWORD);
                checkAutoLogin(email, password);
            }
        };
        registerBroadcast(mBroadcastDataSignUp, KEY_AUTO_LOGIN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
        unregisterBroadcast(mBroadcastDataSignUp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getInstance().register(this);
        setContentView(R.layout.activity_sign_in_layout);
        handleBroadcast();
        loadData();
        initEvent();
        EmptyEditText();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initView() {
        rlParent = findViewById(R.id.root_login);
        tvSignUpNow = findViewById(R.id.text_view_sign_up_now);
        tvForgotPassword = findViewById(R.id.text_view_forgot_password);
        mTextInputEmail = findViewById(R.id.text_input_mail);
        mTextInputPassword = findViewById(R.id.text_input_password);
        edtEmail = findViewById(R.id.edit_text_email);
        edtPassword = findViewById(R.id.edit_text_password);
        btnLogin = findViewById(R.id.button_login_account);
        btBottom = findViewById(R.id.button_of_bottom_welcome_screen);

        tvSignUpNow.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btBottom.setOnClickListener(this);
        edtEmail.addTextChangedListener(edtWatcher);
        edtPassword.addTextChangedListener(edtWatcher);
    }

    private void loadData() {
        initView();
        btBottom.setText(getText(R.string.title_sign_up));
        Utils.setEnable(btnLogin, false);
        boolean isNotShow = Utils.isNotWelcome(SignInActivity.this);
        if (isNotShow) {
            checkAutoLogin(Utils.getEmail(this), Utils.getPassword(this));
        }
    }

    public void checkAutoLogin(String email, String password) {
        edtEmail.setText(email);
        edtPassword.setText(password);
        Utils.setEnable(btnLogin, true);
        loginAuthAccount(email, password);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_login_account:
                handledLogin();
                break;
            case R.id.text_view_forgot_password:
                break;
            case R.id.text_view_sign_up_now:
            case R.id.button_of_bottom_welcome_screen:
                Utils.goToSigUpActivity(this);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
//        Remove focus for EditText
        rlParent.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (edtEmail.isFocused() || edtPassword.isFocused()) {
                    Rect outRect = new Rect();
                    edtEmail.getGlobalVisibleRect(outRect);
                    edtPassword.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        edtEmail.clearFocus();
                        edtPassword.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
            return false;
        });

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    handledLogin();
                    return true;
                }
                return false;
            }
        });

    }

    private TextWatcher edtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!isMailEmpty && !isPasswordEmpty) {
                Utils.setEnable(btnLogin, true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void EmptyEditText() {
        edtEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtEmail.getText().toString().trim().length() == 0) {
                    mTextInputEmail.setError(getText(R.string.empty_email));
                    isMailEmpty = true;
                }
            } else {
                isMailEmpty = false;
                mTextInputEmail.setError(null);
            }
        });

        edtPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (edtPassword.getText().toString().trim().length() == 0) {
                    mTextInputPassword.setError(getText(R.string.empty_password));
                    isPasswordEmpty = true;
                }
            } else {
                isPasswordEmpty = false;
                mTextInputPassword.setError(null);
            }
        });
    }

    private void handledLogin() {

        String mEmail, mPassword;
        mEmail = edtEmail.getText().toString();
        mPassword = edtPassword.getText().toString();
        loginAuthAccount(mEmail, mPassword);
//        if (!Utils.isValidEmail(mEmail)) {
//            Utils.showAlertDialogOk(SignInActivity.this,
//                    getString(R.string.error_txt),
//                    getString(R.string.invalid_email_txt), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//        } else {
//
//        }
        edtEmail.clearFocus();
        edtPassword.clearFocus();
    }


    private void loginAccount(String username, String password) {
        showProgressDialog();
//        CallApi.loginAuthAccount(username, password, TAG);
    }

    @Subscribe
    public void getDataLogin(SigInEvent event) {
        if (!checkFilterName(event)) return;
        dismissProgressDialog();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (event.getStatus()) {
                    if (event.getUserData() != null) {
                        Log.e("ss", "aaaa");
                        Toast.makeText(mContext, "Login", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, event.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void loginAuthAccount(String mail, String password) {
        showProgressDialog();
        LoginApi mLoginApi = NetworkProfile.getRetrofitInstance().create(LoginApi.class);

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", mail);
        requestBody.put("password", password);

        Call<LoginModel> callRequest = mLoginApi.loginAccount(requestBody);
        callRequest.enqueue(new Callback<LoginModel>() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                dismissProgressDialog();
                if (response.code() == Constant.IS_SUCCESS) {
                    Utils.saveRememberAccount(SignInActivity.this, true, mail, password);
                    if (response.body().getAccount() != null) {
                        Utils.setUid(Utils.cutSymb(response.body().getAccount().getUid()+""));
                        Log.e("uid", Utils.getUid());
                        Intent intent = new Intent(SignInActivity.this, MainToolbarActivity.class);
                        startActivity(intent);
                        Utils.showToast(SignInActivity.this, R.string.login_successfully, true);
                    } else {
                        Utils.showAlertDialogOk(SignInActivity.this, getString(R.string.error_txt),
                                getString(R.string.server_error), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                    }
                } else {
                    Utils.handleErrorMessages(SignInActivity.this, response, R.string.unknown_account);
                }
                Log.e(String.format(getResources().getString(R.string.status_code), response.code()), "" + response.message());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                dismissProgressDialog();
                Utils.showAlertDialogOk(SignInActivity.this,
                        getString(R.string.error_txt),
                        t.getMessage(),
                        (dialogInterface, i) -> dialogInterface.dismiss());
                Log.e("onFailure ", "" + t.getMessage());
            }
        });
    }

    private void checkVerify() {
        LoginApi mCheckVerify = NetworkProfile.getRetrofitInstance().create(LoginApi.class);
        Call<EmailVerifyModel> call = mCheckVerify.getVerify();
        call.enqueue(new Callback<EmailVerifyModel>() {
            @Override
            public void onResponse(Call<EmailVerifyModel> call, Response<EmailVerifyModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(SignInActivity.this, R.string.successfully_verify_status, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, R.string.failed_verify_status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmailVerifyModel> call, Throwable t) {
                Toast.makeText(SignInActivity.this, R.string.cant_connect_to_server_status, Toast.LENGTH_SHORT).show();
                Log.e("status", "" + t.getMessage());
            }
        });
    }

}