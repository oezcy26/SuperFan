package ch.oezcy.superfan.background;

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

public class ActualTableLoader {
    private AppDatabase db;

    public ActualTableLoader(AppDatabase db) {
        this.db = db;
    }

    protected List<Team> doIt() {
        Elements tablerows = null;
        List<Team> teams = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.fussballdaten.de/tuerkei/").get();

            tablerows = doc.select("div#spieleWidgetTabelle233-container tbody tr");


            if (tablerows != null) {
                db.teamDao().deleteAll();
                for (Element row : tablerows) {
                    String teamId = ParseHelper.getTeamIdFromRow(row);
                    String teamName = ParseHelper.getTeamNameFromRow(row);
                    short teamPoints = ParseHelper.getTeamPointsFromRow(row);

                    Team t = new Team(teamId, teamName, teamPoints);
                    teams.add(t);
                    //TODO alle gleichzeitig einfügen -> eine Methode im Dao mit transaction welche löscht und einfügt gleichzeitig. atomar.
                    db.teamDao().insertAll(t);
                }
            }


        } catch (IOException e) {
            // TODO Fehlermeldung
            e.printStackTrace();
        }

        return teams;
    }
}
