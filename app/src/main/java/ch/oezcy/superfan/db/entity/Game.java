package ch.oezcy.superfan.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"gameday_nbr", "home_id", "guest_id"}, unique = true)})
public class Game {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "played")
    public boolean played;

    @ColumnInfo(name = "gameday_nbr")
    public short gamedayNbr;

    @ColumnInfo(name = "home_id")
    public String homeId;

    @ColumnInfo(name = "home_name")
    public final String homeName;

    @ColumnInfo(name = "guest_id")
    public String guestId;

    @ColumnInfo(name = "guest_name")
    public final String guestName;

    @ColumnInfo(name = "home_goals")
    public short homeGoals;

    @ColumnInfo(name = "guest_goals")
    public short guestGoals;

    public String winner; //teamId or null for draw.

    public Game(short gamedayNbr, boolean played,  String homeId, String homeName, String guestId, String guestName) {
        this.gamedayNbr = gamedayNbr;
        this.homeId = homeId;
        this.homeName = homeName;
        this.guestId = guestId;
        this.guestName = guestName;
        this.played = played;

    }

    @Override
    public String toString() {
        String div = "\t|\t";
        String str = gamedayNbr + div + played + div + homeId + div + homeName + div + guestId + div + guestName + div + homeGoals + div + guestGoals + div + winner;
        return str;
    }
}
