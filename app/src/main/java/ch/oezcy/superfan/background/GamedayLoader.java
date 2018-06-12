package ch.oezcy.superfan.background;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.oezcy.superfan.ConfigConstants;
import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Gameday;

public class GamedayLoader extends AsyncTask<Void,Void,Void> {
    private AppDatabase db;
    public GamedayLoader(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {



        for (int i = 0; i < ConfigConstants.GAMEDAY_AMOUNT; i++) {
            Gameday gameday = db.gamedayDao().selectById((short) (i + 1));
            if(gameday == null){
                gameday = new Gameday((short) (i + 1));

                /*
                try {
                    // to no request to fast
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */


                loadDataFor(gameday);
            }else if(!gameday.finished){
                deleteDataFor(gameday);
                loadDataFor(gameday);
                //break !

            }

            System.out.println("Gameday loaded : " + gameday.nbr);
            /*  //test
            List<Game> loadedGames = db.gameDao().loadAllGamesByGameday(gameday.nbr);
            for(Game g : loadedGames){
                System.out.println(g.toString() + "\n");
            }
            */
        }

        List<Gameday> gamedays = db.gamedayDao().selectAllGamedays();
        for(Gameday gd : gamedays){
            System.out.println(gd.toString() + "\n");
        }

        return null;
    }

    private void loadDataFor(Gameday gd){

            Boolean notAllGamesPlayed = new GameLoader(db).loadGamesForGameday(gd.nbr);
            if(notAllGamesPlayed){
                gd.finished = false;

            }else{
                gd.finished = true;
            }
            db.gamedayDao().insertAll(gd);
    }

    private void deleteDataFor(Gameday gd){
        db.gameDao().deleteAllByGamedayId(gd.nbr);
    }
}
