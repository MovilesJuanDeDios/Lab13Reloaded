package com.example.lab13_reloaded.view;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.lab13_reloaded.R;
import com.example.lab13_reloaded.utils.SharedPref;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_username_editText)
    EditText login_username_editText;
    @BindView(R.id.login_password_editText)
    EditText login_password_editText;
    @BindView(R.id.login_button)
    Button login_button;

    @BindView(R.id.username_lg_input_layout)
    TextInputLayout username_input;
    @BindView(R.id.password_lg_input_layout)
    TextInputLayout password_input;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private String username;
    private String password;

    private final String pass = "root";
    private final String user = "root";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(LoginActivity.this, StudentListActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

    public void login() {

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(pass) || !username.equals(user)) {
            Toast.makeText(getBaseContext(), getString(R.string.user_not_found), Toast.LENGTH_LONG).show();
            return;
        }

        onLoginSuccess();
    }

    public void onLoginSuccess() {
        SharedPref.saveString(this, getString(R.string.pref_username), username);

        Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, StudentListActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validate() {

        // Store values at the time of the login attempt.
        username = login_username_editText.getText().toString();
        password = login_password_editText.getText().toString();

        boolean valid = true;

        // Check for a valid password
        if (password.isEmpty()) {
            password_input.setError(getString(R.string.empty_password));
            password_input.requestFocus();
            valid = false;
        } else {
            password_input.setError(null);
        }

        // Check for a valid username
        if (username.isEmpty()) {
            username_input.setError(getString(R.string.empty_username));
            username_input.requestFocus();
            valid = false;
        } else {
            username_input.setError(null);
        }

        return valid;
    }
}