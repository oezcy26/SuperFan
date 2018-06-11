package ch.oezcy.superfan.background;

import android.os.AsyncTask;

import ch.oezcy.superfan.db.AppDatabase;

public class GamesLoader extends AsyncTask<Void,Void,Void> {
    private AppDatabase db;
    public GamesLoader(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new GamedayLoader(db).execute((short)1);
        return null;
    }
}
