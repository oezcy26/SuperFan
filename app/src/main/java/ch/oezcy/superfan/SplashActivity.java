package ch.oezcy.superfan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.oezcy.superfan.background.BeforeStartDataLoader;
import ch.oezcy.superfan.db.AppDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        new BeforeStartDataLoader(this, db).execute();

    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
