package ch.oezcy.superfan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.oezcy.superfan.background.BeforeStartDataLoader;
import ch.oezcy.superfan.background.GamedayLoader;
import ch.oezcy.superfan.db.AppDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        new BeforeStartDataLoader(this, db).execute();


        /*
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    //load games (previous and coming games)
                    Thread.sleep(0);

                    //new GamedayLoader(db).execute();
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
        */
    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
