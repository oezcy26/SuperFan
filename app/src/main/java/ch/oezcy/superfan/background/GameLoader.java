package ch.oezcy.superfan.background;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

public class GameLoader{

    private final short HOMETEAM_COLUMN = 2;
    private final short GUESTTEAM_COLUMN = 4;
    private final String URLPATTERN = "https://www.fussballdaten.de/tuerkei/2018/%d/";
    private final AppDatabase db;

    public GameLoader(AppDatabase db) {
        this.db = db;
    }


    protected Boolean loadGamesForGameday(short gamedaynbr) {
        Elements tablerows;


        String url = String.format(URLPATTERN, gamedaynbr);
        boolean notAllGamesPlayed = false; //

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            tablerows = doc.select("table.table-leistungsdaten");
            Elements gamerows = tablerows.select("tr[data-key]");

            Game[] games = new Game[gamerows.size()];


            for(int i = 0; i < gamerows.size(); i++){

                String homeId = getTeamId(gamerows.get(i), HOMETEAM_COLUMN);
                String homeName = db.teamDao().getNameById(homeId);
                String guestId = getTeamId(gamerows.get(i), GUESTTEAM_COLUMN);
                String guestName = db.teamDao().getNameById(guestId);
                boolean played = isGamePlayed(gamerows.get(i));


                Game newGame;

                if(played){
                    newGame = extractResults(gamedaynbr, gamerows.get(i), homeId, homeName, guestId, guestName, played);
                }else{
                    newGame = new Game(gamedaynbr, played, homeId, homeName, guestId, guestName);
                    notAllGamesPlayed = true;
                }

                games[i] = newGame;
            }

            db.gameDao().insertAll(games);




        } catch (IOException e) {
            e.printStackTrace();
        }

        return notAllGamesPlayed;

    }

    @NonNull
    private Game extractResults(short gameday, Element game, String homeId, String homename, String guestId, String guestName, boolean played) {
        Game newGame;
        String score = getScore(game);
        String[] scores = score.split(":");

        short homeGoals = Short.valueOf(scores[0]);
        short guestGoals = Short.valueOf(scores[1]);
        String winner = null;

        if(homeGoals > guestGoals){
            winner = homeId;
        }else if(guestGoals > homeGoals){
            winner = guestId;
            // String stays null for draw
        }
        newGame = new Game(gameday, played, homeId, homename, guestId, guestName);
        newGame.homeGoals = homeGoals;
        newGame.guestGoals = guestGoals;
        newGame.winner = winner;
        return newGame;
    }

    private void extractResults(Game game){

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

    public boolean isGamePlayed(Element row){
        Elements link = row.select("td[data-col-seq=3]");
        String aClass = link.attr("class");

        if(aClass.equals("ergebnis")){
            //otherwise 'zeit'
            return true;
        }

        return false;
    }
}
