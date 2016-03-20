package pucelann.spectrumapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private Button staffLogin;
    private Button patientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);

        staffLogin = (Button) findViewById(R.id.StaffLoginBtn);
        patientLogin = (Button) findViewById(R.id.PatientLoginBtn);

        staffLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(LoginActivity.this, StaffHome.class);
                startActivity(intent);
            }
        });

        patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PatientHome.class);
                startActivity(intent);
            }
        });
    }
}
