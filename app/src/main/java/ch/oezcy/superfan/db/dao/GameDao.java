package ch.oezcy.superfan.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import ch.oezcy.superfan.db.entity.Game;

@Dao
public interface GameDao {

    @Transaction
    @Insert
    void insertAll(Game... games);

    @Query("SELECT * FROM game")
    public Game[] loadAllGames();

    @Query("SELECT * FROM game g WHERE g.gameday_nbr = :nbr")
    List<Game> loadAllGamesByGameday(short nbr);

    @Query("DELETE FROM game WHERE gameday_nbr = :nbr")
    void deleteAllByGamedayId(short nbr);

    @Query("SELECT * FROM game WHERE (home_id = :teamId1 AND guest_id = :teamId2) OR (home_id = :teamId2 AND guest_id = :teamId1)")
    List<Game> getGamesForTeams(String teamId1, String teamId2);
}
