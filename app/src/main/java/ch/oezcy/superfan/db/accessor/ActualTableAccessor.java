package ch.oezcy.superfan.db.accessor;

import android.os.AsyncTask;

import java.util.List;

import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Team;

public class ActualTableAccessor extends AsyncTask<Void,Void,List<Team>> {
    private AppDatabase db;

    public ActualTableAccessor(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected List<Team> doInBackground(Void... voids) {
        List<Team> teams = db.teamDao().getTeamOrderPoints();
        return teams;

    }
}
