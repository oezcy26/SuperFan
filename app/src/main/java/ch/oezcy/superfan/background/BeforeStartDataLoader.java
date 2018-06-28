package ch.oezcy.superfan.background;

import android.content.Intent;
import android.os.AsyncTask;

import ch.oezcy.superfan.MainActivity;
import ch.oezcy.superfan.SplashActivity;
import ch.oezcy.superfan.db.AppDatabase;

/**
 * Loads the need data before app starts, while SPLASH-ACTIVITY
 */

public class BeforeStartDataLoader extends AsyncTask<Void, Void, Void> {
    private SplashActivity current;
    private AppDatabase db;

    public BeforeStartDataLoader(SplashActivity splashActivity, AppDatabase db) {
        current = splashActivity;
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //some heavy processing resulting in a Data String

        new ActualTableLoader(db).doIt();

        new GameDataLoader(db).doIt();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        current.startMainActivity();

    }
}
