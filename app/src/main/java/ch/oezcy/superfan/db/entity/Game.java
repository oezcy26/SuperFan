package ch.oezcy.superfan.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"gameday", "home_id", "guest_id"}, unique = true)})
public class Game {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "gameday")
    public short gameday;

    @ColumnInfo(name = "home_id")
    public String homeId;

    @ColumnInfo(name = "guest_id")
    public String guestId;

    @ColumnInfo(name = "home_goals")
    public short homeGoals;

    @ColumnInfo(name = "guest_goals")
    public short guestGoals;

    public String winner; //teamId or null for draw.

    public Game(short gameday, String homeId, String guestId, short homeGoals, short guestGoals, String winner) {
        this.gameday = gameday;
        this.homeId = homeId;
        this.guestId = guestId;
        this.homeGoals = homeGoals;
        this.guestGoals = guestGoals;
        this.winner = winner;
    }

    @Override
    public String toString() {
        String div = "\t|\t";
        String str = gameday + div + homeId + div + guestId + div + homeGoals + div + guestGoals + div + winner;
        return str;
    }
}
