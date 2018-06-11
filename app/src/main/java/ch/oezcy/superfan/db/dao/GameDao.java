package ch.oezcy.superfan.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import ch.oezcy.superfan.db.entity.Game;

@Dao
public interface GameDao {

    @Insert
    void insertAll(Game... games);

    @Query("SELECT * FROM game")
    public Game[] loadAllGames();
}
