package com.example.appsale18022022.presentation.views.authentications.sign_in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.appsale18022022.R;
import com.example.appsale18022022.presentation.views.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText inputEdtEmail , inputEdtPassword;
    TextView tvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        toolbar = findViewById(R.id.toolbarLogin);
        inputEdtEmail = findViewById(R.id.textEditEmail);
        inputEdtPassword = findViewById(R.id.textEditPassword);
        tvSignUp = findViewById(R.id.textViewSignUp);

        setStatusBar();
    }

    private void setStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.blue));
    }
}
