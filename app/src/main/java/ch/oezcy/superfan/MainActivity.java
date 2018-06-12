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

    private TeamSelection selection = new TeamSelection();
    private Game game1;
    private Game game2;

    private AppDatabase db;
    private ActivityMainBinding binding;

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

                    tableRow.setOnLongClickListener(new TableSelectLongclickListener(binding));

                    table.addView(tableRow);
                }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private class TableSelectLongclickListener implements View.OnLongClickListener{
        final ActivityMainBinding binding;
        TableSelectLongclickListener(ActivityMainBinding binding) {
            this.binding = binding;
        }

        @Override
        public boolean onLongClick(View v) {
            // extract team-infos from row
            TableRow row = (TableRow)v;
            TextView teamidV = (TextView)row.getChildAt(0);
            TextView teamNameV = (TextView)row.getChildAt(1);
            TextView teamPointsV = (TextView)row.getChildAt(2);

            String teamid = teamidV.getText().toString();
            String teamName = teamNameV.getText().toString();
            short teamPoints = Short.parseShort(teamPointsV.getText().toString());

            // make new Team and set selection
            Team newTeam = new Team(teamid, teamName, teamPoints);
            selection = selection.selectTeam(newTeam);
            this.binding.setSelection(selection);

            // load data for games
            if(selection.getSelectedTeam1() != null && selection.getSelectedTeam2() != null){
                try {
                    List<Game> games = new TeamComparer(db).execute(selection).get();
                    binding.setGame1(games.get(0));
                    binding.setGame2(games.get(1));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }else{
                binding.setGame1(null);
                binding.setGame2(null);
            }
            return false;
        }
    }

}
