package ch.oezcy.superfan.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Gameday {

    @PrimaryKey
    public short nbr;

    @NonNull
    public boolean finished;


    public Gameday(short nbr){
        this.nbr = nbr;
        this.finished = false;
    }

    @Override
    public String toString() {
        return this.nbr + "\t" + this.finished;
    }
}


