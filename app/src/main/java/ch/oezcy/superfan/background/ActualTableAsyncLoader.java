package ch.oezcy.superfan.background;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.utility.ParseHelper;

/**
 * Loads current table from 'fussballdaten' and stores Team
 */

@SuppressLint("StaticFieldLeak")
public class ActualTableAsyncLoader extends AsyncTask<Void, Void, List<Team>> {
    private AppDatabase db;

    public ActualTableAsyncLoader(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected List<Team> doInBackground(Void... strings) {
        List<Team> teams = new ActualTableLoader(db).doIt();
        return teams;
    }
}
