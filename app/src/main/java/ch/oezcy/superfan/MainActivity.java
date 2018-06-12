package ch.oezcy.superfan;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;


import ch.oezcy.superfan.background.ActualTableLoader;
import ch.oezcy.superfan.background.GamedayLoader;
import ch.oezcy.superfan.background.TeamComparer;
import ch.oezcy.superfan.databinding.ActivityMainBinding;
import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.utility.TeamSelection;

public class MainActivity extends AppCompatActivity {

    public static TeamSelection selection = new TeamSelection();

    private AppDatabase db;
    public static ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getDatabase(getApplicationContext());

        //IMPORTANT!: to change the value, the whole object must be replaced in binding. it is not sufficient to change only an atttribut
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //load the actual table
        TableLayout table = findViewById(R.id.table);
        try {
            List<Team> teams = new ActualTableLoader(db).execute().get();


                for(Team team : teams){
                    TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.tablerow, null);
                    ((TextView)tableRow.findViewById(R.id.teamName)).setText(team.name);
                    ((TextView)tableRow.findViewById(R.id.teamPoints)).setText(String.valueOf(team.teamPoints));
                    ((TextView)tableRow.findViewById(R.id.teamId)).setText(team.id);

                    tableRow.setOnLongClickListener(new TableOnLongClickListener(db));

                    table.addView(tableRow);
                }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



}
