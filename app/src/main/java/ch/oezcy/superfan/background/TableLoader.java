package ch.oezcy.superfan.background;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.utility.ParseHelper;

@SuppressLint("StaticFieldLeak")
public class TableLoader extends AsyncTask<Void, Void, Elements> {

    private AppDatabase db;

    public TableLoader(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected Elements doInBackground(Void... strings) {
        Elements tablerows = null;
        try {
            Document doc = Jsoup.connect("https://www.fussballdaten.de/tuerkei/").get();

            tablerows = doc.select("div#spieleWidgetTabelle233-container tbody tr");

            if(tablerows != null) {
                db.teamDao().deleteAll();
                for (Element row : tablerows) {
                    String teamId = ParseHelper.getTeamIdFromRow(row);
                    String teamName = ParseHelper.getTeamNameFromRow(row);
                    short teamPoints = ParseHelper.getTeamPointsFromRow(row);

                    Team t = new Team(teamId,teamName, teamPoints);
                    db.teamDao().insertAll(t);
                }
            }

        } catch (IOException e) {
            // TODO Fehlermeldung ausgeben, keine Verbindung zu livescores.
            e.printStackTrace();
        }

        return tablerows;
    }
}
