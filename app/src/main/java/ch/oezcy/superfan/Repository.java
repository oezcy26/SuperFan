package ch.oezcy.superfan;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.dao.TeamDao;
import ch.oezcy.superfan.db.entity.Team;

public class Repository {

    private TeamDao teamDao;
    private LiveData<List<Team>> allTeams;

    Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        teamDao = db.teamDao();
        allTeams = teamDao.getAllTeams();
    }

    LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }


    public void insert(Team team) {
        new insertAsyncTask(teamDao).execute(team);
    }

    private static class insertAsyncTask extends AsyncTask<Team, Void, Void> {

        private TeamDao mAsyncTaskDao;

        insertAsyncTask(TeamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Team... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
