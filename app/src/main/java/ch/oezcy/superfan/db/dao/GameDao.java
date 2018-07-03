package ch.oezcy.superfan.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import ch.oezcy.superfan.db.entity.Game;

@Dao
public abstract class GameDao {

    @Transaction
    @Insert
    public abstract void insertAll(Game... games);

    @Query("SELECT * FROM game ORDER BY gameday ASC")
    public abstract List<Game> loadAllGames();

    @Query("SELECT * FROM game g WHERE g.gameday = :nbr")
    public abstract List<Game> loadAllGamesByGameday(short nbr);

    @Query("DELETE FROM game")
    public abstract void deleteAll();

    @Query("DELETE FROM game WHERE gameday = :nbr")
    public abstract void deleteAllByGameday(short nbr);

    @Query("SELECT * FROM game " +
            "WHERE (home_id = :teamId1 AND guest_id = :teamId2) OR (home_id = :teamId2 AND guest_id = :teamId1) " +
            "ORDER BY gameday ASC")
    public abstract List<Game> getGamesForTeams(String teamId1, String teamId2);

    @Transaction
    public void replaceGamedayData(short gamedaynbr, Game[] games) {
        deleteAllByGameday(gamedaynbr);
        insertAll(games);
    }
}
