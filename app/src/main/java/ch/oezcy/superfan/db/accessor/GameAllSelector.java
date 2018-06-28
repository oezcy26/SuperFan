package ch.oezcy.superfan.db.accessor;

import android.os.AsyncTask;

import java.util.List;

import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Team;

public class GameAllSelector extends AsyncTask<Void, Void, List<Game>>{
    private AppDatabase db;

    public GameAllSelector(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected List<Game> doInBackground(Void... voids) {
        List<Game> games = db.gameDao().loadAllGames();
        return games;

    }
}
