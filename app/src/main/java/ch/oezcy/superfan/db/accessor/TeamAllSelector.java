package ch.oezcy.superfan.db.accessor;

import android.os.AsyncTask;

import java.util.List;

import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Team;

public class TeamAllSelector extends AsyncTask<Void,Void,List<Team>> {
    private AppDatabase db;

    public TeamAllSelector(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected List<Team> doInBackground(Void... voids) {
        List<Team> teams = db.teamDao().getTeamsInRanking();
        return teams;

    }
}
