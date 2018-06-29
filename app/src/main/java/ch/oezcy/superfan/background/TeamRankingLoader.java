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

/**
 * Loads the actual ranking from website and stores in DB.
 */
public class TeamRankingLoader {
    private AppDatabase db;

    public TeamRankingLoader(AppDatabase db) {
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
                for (int i = 0; i < tablerows.size(); i++) {
                    String teamId = ParseHelper.getTeamIdFromRow(tablerows.get(i));
                    String teamName = ParseHelper.getTeamNameFromRow(tablerows.get(i));
                    short teamPoints = ParseHelper.getTeamPointsFromRow(tablerows.get(i));

                    Team t = new Team(teamId, teamName, teamPoints, (short)(i + 1));
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
