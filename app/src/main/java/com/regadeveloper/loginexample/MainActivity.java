package com.regadeveloper.loginexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText uNem, pW;
    private Button btnLog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uNem = findViewById(R.id.unem);
        pW = findViewById(R.id.pw);
        btnLog = findViewById(R.id.lgin);
        progressBar = findViewById(R.id.probar);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", uNem.getText().toString());
                    jsonObject.put("password", pW.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post("https://reqres.in/api/login")
                        .addJSONObjectBody(jsonObject)
                        .setTag("loginrequest")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                try {
                                    builder.setTitle("Sukses Login").setMessage("Token : "+ response.getString("token")).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT);
                            }
                        });
            }
        });

    }
}
