package com.example.refugio;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.refugio.MainActivity;
import com.example.refugio.MoodDatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText passwordInput;
    private MoodDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        passwordInput =
                findViewById(R.id.passwordInput);

        MaterialButton loginButton =
                findViewById(R.id.loginButton);

        TextView createAccountLink =
                findViewById(R.id.createAccountLink);

        databaseHelper =
                new MoodDatabaseHelper(this);

        loginButton.setOnClickListener(v -> {

            String password =
                    passwordInput.getText()
                            .toString()
                            .trim();

            if (password.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please enter your password",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            String savedEmail =
                    getSharedPreferences(
                            "RefugioData",
                            MODE_PRIVATE
                    ).getString(
                            "email",
                            ""
                    );

            if (savedEmail.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please create an account first",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            SQLiteDatabase db =
                    databaseHelper.getReadableDatabase();

            Cursor cursor =
                    db.rawQuery(
                            "SELECT name FROM users " +
                                    "WHERE email = ? AND password = ?",
                            new String[]{
                                    savedEmail,
                                    password
                            }
                    );

            if (cursor.moveToFirst()) {

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "name"
                                )
                        );

                cursor.close();
                db.close();

                getSharedPreferences(
                        "RefugioData",
                        MODE_PRIVATE
                )
                        .edit()
                        .putString(
                                "name",
                                name
                        )
                        .putString(
                                "email",
                                savedEmail
                        )
                        .apply();

                Toast.makeText(
                        this,
                        "Login successful",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(
                                LoginActivity.this,
                                DashBoardActivity.class
                        );

                startActivity(intent);
                finish();

            } else {

                cursor.close();
                db.close();

                Toast.makeText(
                        this,
                        "Incorrect password",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        createAccountLink.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            LoginActivity.this,
                            MainActivity.class
                    );

            startActivity(intent);
            finish();
        });
    }
}
