package ch.oezcy.superfan.db.accessor;

import android.os.AsyncTask;

import java.util.List;

import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.utility.TeamSelection;

public class GameForTeamsSelector extends AsyncTask<TeamSelection, Void, List<Game>> {

    private final AppDatabase db;

    public GameForTeamsSelector(AppDatabase db){
        this.db = db;
    }

    @Override
    protected List<Game> doInBackground(TeamSelection... teamSelections) {
        if(teamSelections.length > 1){
            throw new IllegalStateException("Not more then one selection possible");
        }

        TeamSelection selection = teamSelections[0];

        //DEBUGGING: see all games in console
        List<Game> allGames = db.gameDao().loadAllGames();
        for(Game g : allGames){
            System.out.println(g);
        }
        System.out.println();
        System.out.println("Searching for : " + selection.getSelectedTeam1().id +" "+ selection.getSelectedTeam2().id);


        List<Game> games = db.gameDao().getGamesForTeams(selection.getSelectedTeam1().id, selection.getSelectedTeam2().id);

        return games;

    }
}
