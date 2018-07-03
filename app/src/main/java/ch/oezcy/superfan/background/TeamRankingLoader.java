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

    protected void doIt() {
        Elements tablerows = null;
        try {
            Document doc = Jsoup.connect("https://www.fussballdaten.de/tuerkei/").get();

            tablerows = doc.select("div#spieleWidgetTabelle233-container tbody tr");


            if (tablerows != null) {
                //array for bulk insert
                Team[] teams = new Team[tablerows.size()];

                for (int i = 0; i < tablerows.size(); i++) {
                    String teamId = ParseHelper.getTeamIdFromRow(tablerows.get(i));
                    String teamName = ParseHelper.getTeamNameFromRow(tablerows.get(i));
                    short teamPoints = ParseHelper.getTeamPointsFromRow(tablerows.get(i));

                    Team t = new Team(teamId, teamName, teamPoints, (short)(i + 1));
                    teams[i] = t;
                }
                db.teamDao().replaceTeams(teams);

            }


        } catch (IOException e) {
            // TODO Fehlermeldung
            e.printStackTrace();
        }

    }
}
