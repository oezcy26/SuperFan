package ch.oezcy.superfan;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


import ch.oezcy.superfan.adapter.TeamRankingAdapter;
import ch.oezcy.superfan.databinding.ActivityMainBinding;
import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.accessor.GameAllSelector;
import ch.oezcy.superfan.db.accessor.TeamAllSelector;

import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.listener.TableOnLongClickListener;
import ch.oezcy.superfan.utility.TeamSelection;

public class MainActivity extends AppCompatActivity {

    public static TeamSelection selection = new TeamSelection();

    private AppDatabase db;
    public static ActivityMainBinding binding;
    private TeamRankingAdapter teamRankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getDatabase(getApplicationContext());

        //IMPORTANT!: to change the value, the whole object must be replaced in binding. it is not sufficient to change only an atttribut
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //load the actual table
        ListView listview = findViewById(R.id.lv_teamrankings);

        try {
            List<Team> teams = new TeamAllSelector(db).execute().get();
            teamRankingAdapter = new TeamRankingAdapter(this, teams);

            listview.setAdapter(teamRankingAdapter);
            listview.setOnItemLongClickListener(new TableOnLongClickListener(this, db));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void logGames(View v){
        try {
            List<Game> games = new GameAllSelector(db).execute().get();
            for(Game g: games){
                System.out.println(g);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public void logTeams(View v){
        List<Team> teams = null;
        try {
            teams = new TeamAllSelector(db).execute().get();
            for(Team t : teams){
                System.out.println(t);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
