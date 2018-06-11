package ch.oezcy.superfan.background;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Game;

/**
 * Loads one gameday and stores in DB
 */

public class GamedayLoader extends AsyncTask<Short,Void,Boolean> {

    private final short HOMETEAM_COLUMN = 2;
    private final short GUESTTEAM_COLUMN = 4;
    private final String URLPATTERN = "https://www.fussballdaten.de/tuerkei/2018/%d/";
    private final AppDatabase db;

    public GamedayLoader(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected Boolean doInBackground(Short... gamedaynbr) {
        Elements tablerows;
        short gameday = gamedaynbr[0];

        if(gamedaynbr.length == 1){

            String url = String.format(URLPATTERN, gamedaynbr);

            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
                tablerows = doc.select("table.table-leistungsdaten");
                Elements gamerows = tablerows.select("tr[data-key]");

                for(Element game : gamerows){
                    String homeId = getTeamId(game, HOMETEAM_COLUMN);
                    String guestId = getTeamId(game, GUESTTEAM_COLUMN);

                    String score = getScore(game);
                    String[] scores = score.split(":");

                    short homeGoals = Short.valueOf(scores[0]);
                    short guestGoals = Short.valueOf(scores[1]);
                    String winner = null;

                    if(homeGoals > guestGoals){
                        winner = homeId;
                    }else if(guestGoals > homeGoals){
                        winner = guestId;
                    }
                    // String stays null for draw
                    Game newGame = new Game(gameday, homeId, guestId, homeGoals, guestGoals, winner);
                    db.gameDao().insertAll(newGame);



                }

                Game[] games = db.gameDao().loadAllGames();
                for(Game g : games){
                    System.out.println(g.toString() + "\n");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }else{
            return false;
        }
    }


    public static String getTeamId(Element row, short teamColumn){
        Elements link = row.select("td[data-col-seq=" + teamColumn + "] a");
        String teamId = link.attr("href");

        //extract name
        String[] parts = teamId.split("/");

        return parts[2];
    }

    public String getScore(Element row){
        String score = row.select("td[data-col-seq=3] a span[id]").text();
        return score;
    }


}
