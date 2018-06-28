package ch.oezcy.superfan.background;

import android.support.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Game;

public class GameDataLoader {

    private final short HOMETEAM_COLUMN = 1;
    private final short GUESTTEAM_COLUMN = 3;

    private AppDatabase db;
    private Elements gamedayTables;

    public GameDataLoader(AppDatabase db) {
        this.db = db;
    }

    public void doIt() {

        // extract rows containing a
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.fussballdaten.de/tuerkei/spielplan/").get();
            gamedayTables = doc.select("div#page-content div.row.padding-cols.row-flex table.table-small-spiel");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert gamedayTables.size() == 33;

        // TODO catch case that gamedayTable==null  (no connection)
        for (int i = 0; i < gamedayTables.size(); i++){
            processGameday((short) i, gamedayTables.get(i));
        }

    }

    private void processGameday(short gamedaynbr, Element gameday) {
        //select rows containing the games
        Elements gamerows = gameday.select("tr[data-key]");

        //array including all games for bulk insert.
        Game[] games = new Game[gamerows.size()];

        boolean gamedayStarted = false;

        // iteration over all games
        for(int i = 0; i < gamerows.size(); i++){
            String homeId = getTeamId(gamerows.get(i), HOMETEAM_COLUMN);
            String homeName = db.teamDao().getNameById(homeId);
            String guestId = getTeamId(gamerows.get(i), GUESTTEAM_COLUMN);
            String guestName = db.teamDao().getNameById(guestId);
            //boolean played = isGamePlayed(gamerows.get(i));
            boolean played = true;


            Game newGame;

            if(played){
                newGame = extractResults(gamedaynbr, gamerows.get(i), homeId, homeName, guestId, guestName, played);
                gamedayStarted = true;
            }else{
                newGame = new Game(gamedaynbr, played, homeId, homeName, guestId, guestName);
            }

            games[i] = newGame;

            if(!gamedayStarted){
                //TODO abbrechen, da keine Daten verfügbar, aber nur Rohdaten vorhanden. Einmal muss es durchlaufen.
            }
        }

        db.gameDao().insertAll(games);

    }

    public static String getTeamId(Element row, short teamColumn){
        Elements link = row.select("td[data-col-seq=" + teamColumn + "] a");
        String teamId = link.attr("href");

        //extract name
        String[] parts = teamId.split("/");

        return parts[2];
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

    public String getScore(Element row){
        String score = row.select("td[data-col-seq=4] a").text();
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
