package ch.oezcy.superfan.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import ch.oezcy.superfan.db.entity.Gameday;

@Dao
public interface GamedayDao {

    @Transaction
    @Insert
    void insertAll(Gameday... teams);

    @Query("SELECT * FROM gameday g ORDER BY g.nbr ASC ")
    List<Gameday> selectAllGamedays();

    @Query("SELECT * FROM gameday g WHERE g.nbr = :nbr")
    Gameday selectById(short nbr);
}
