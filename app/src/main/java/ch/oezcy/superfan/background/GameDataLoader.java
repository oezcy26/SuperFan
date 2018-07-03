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

    private static final int GAMEDAY_AMOUNT = 34;
    private static final int GAMES_AMOUNT = 9;

    private final short HOMETEAM_COLUMN = 1;
    private final short GUESTTEAM_COLUMN = 3;

    private AppDatabase db;
    private Elements gamedayTables;

    public GameDataLoader(AppDatabase db) {
        this.db = db;
    }

    // Performace-Optimierungen
    //TODO Falls in DB 9 Spiele für diesen Tag existieren und alle gespielt worden sind, kann das Aktualieren der daten für diesen Spieltag übersprungen werden
    //TODO Falls alle Daten schon einmal eingefügt wurden (34 tage a 9 spiele vorhanden) - flag setzen-, dann kann nach 2 aufeinanderfolgenden tagen wo noch kein spiel gespielt wurde die Aktualisierung für die restlichen spieltage abgebrochen werden. Es müssen natürlich aber alle einmal eingefügt sein, darum der flag.

    public void doIt() {
        //db.gameDao().deleteAll();

        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.fussballdaten.de/tuerkei/spielplan/").get();
            gamedayTables = doc.select("div#page-content div.row.padding-cols.row-flex table.table-small-spiel");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // gamedayTable is the div representing one gameday (with 9 games)

        if(gamedayTables.size() != GAMEDAY_AMOUNT){
            throw new IllegalStateException("UNGÜLTIGE ANZAHL GAMEDAYS. Es wurden nicht 34 Spieltage gezählt");
        }else if(gamedayTables == null){
            throw new IllegalStateException("KEINE GAMEDAYS GELADEN. gamedayTables ist NULL");
        }

        // go though gamedays ..
        for (int i = 0; i < gamedayTables.size(); i++){
            boolean gamedayStarted = processGameday((short)(i + 1), gamedayTables.get(i));
        }

    }

    private boolean processGameday(short gamedaynbr, Element gameday) {
        //a gamerow contains data for one game
        Elements gamerows = gameday.select("tr[data-key]");

        if(gamerows.size() != GAMES_AMOUNT){
            throw new IllegalStateException("UNGÜLTIGE ANZAHL SPIELE AM SPIELTAG. Bei 18 Mannschaften sollten 9 Spiele stattfinden");
        }

        //array including all games for bulk insert.
        Game[] games = new Game[gamerows.size()];

        boolean gamedayStarted = false;

        // iterate over all games and extract the data.
        for(int i = 0; i < gamerows.size(); i++){
            String homeId = getTeamId(gamerows.get(i), HOMETEAM_COLUMN);
            String homeName = db.teamDao().getNameById(homeId);
            String guestId = getTeamId(gamerows.get(i), GUESTTEAM_COLUMN);
            String guestName = db.teamDao().getNameById(guestId);
            boolean played = isGamePlayed(gamerows.get(i));

            Game newGame = new Game(gamedaynbr, played, homeId, homeName, guestId, guestName);

            if(played){
                short[] goals = getGoals(gamerows.get(i));
                newGame.homeGoals = goals[0];
                newGame.guestGoals = goals[1];
                newGame.winnerId = getWinner(newGame);

                gamedayStarted = true;
            }

            games[i] = newGame;

        }

        db.gameDao().replaceGamedayData(gamedaynbr, games);

        return gamedayStarted;
    }

    public static String getTeamId(Element row, short teamColumn){
        Elements link = row.select("td[data-col-seq=" + teamColumn + "] a");
        String teamId = link.attr("href");

        //extract name
        String[] parts = teamId.split("/");

        return parts[2];
    }

    public short[] getGoals(Element game){
        short[] scores = new short[2];
        String score = game.select("td[data-col-seq=4] a").text();
        //scores is a string like 3:4 ..

        String[] scoresStr = score.split(":");
        if(scoresStr.length != 2){
            throw new IllegalStateException("UNGÜLTIGE ANZAHL TOR-WERTE. Es müssten 2 Werte für die 2 Teams vorhanden sein");
        }

        scores[0] = Short.valueOf(scoresStr[0]); //homeGoals
        scores[1] = Short.valueOf(scoresStr[1]); //guestGoals

        return scores;
    }

    private String getWinner(Game newGame) {
        String winnerId = null;

        if(newGame.homeGoals > newGame.guestGoals){
            winnerId = newGame.homeId;
        }else if(newGame.guestGoals > newGame.homeGoals){
            winnerId = newGame.guestId;
            // String stays null for draw
        }

        return winnerId;
    }


    public boolean isGamePlayed(Element row){
        Elements link = row.select("td[data-col-seq=4] a");
        String aClass = link.attr("class");

        if(aClass.equals("text")){
            //otherwise 'zeit'
            return true;
        }

        return false;
    }
}
