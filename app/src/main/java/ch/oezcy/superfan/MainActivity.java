package ch.oezcy.superfan;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ch.oezcy.superfan.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TeamSelection selection = new TeamSelection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        final User user = new User("Hallo", "Friend");
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //to change the value, the whole object must be replaced in binding. it is not sufficient to change only an atttribut
        binding.setUser(user);





        TableLayout table = (TableLayout) findViewById(R.id.table);

        try {
            Elements tablerows = new MainLoader().execute().get();

            if(tablerows != null){
                for(Element row : tablerows){
                    String teamName = getTeamNameFromRow(row);
                    int teamPoints = getTeamPointsFromRow(row);
                    //TODO we need id for team (searching for results).. id = link zur seite des teams z.B: /vereine/galatasaray-istanbul/2018/



                    TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.tablerow, null);
                    ((TextView)tableRow.findViewById(R.id.teamName)).setText(teamName);
                    ((TextView)tableRow.findViewById(R.id.teamPoints)).setText(String.valueOf(teamPoints));

                    tableRow.setOnLongClickListener(new TableSelectLongclickListener());

                    table.addView(tableRow);

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }





    }

    private String getTeamNameFromRow(Element row){
        String teamName = row.select("td[data-col-seq=2] a").text();
        return teamName;
    }

    private int getTeamPointsFromRow(Element row){
        String teamPoints = row.select("td[data-col-seq=6]").text();
        return Integer.valueOf(teamPoints);
    }

    private class TableSelectLongclickListener implements View.OnLongClickListener{
        @Override
        public boolean onLongClick(View v) {
            TableRow row = (TableRow)v;
            TextView textTeam = (TextView)row.getChildAt(0);
            Team newTeam = new Team("", textTeam.getText().toString());
            selection = selection.selectTeam(newTeam);
            System.out.println(selection.toString());
            return false;
        }
    }



    private class MainLoader extends AsyncTask<Void, Void, Elements>{

        @Override
        protected Elements doInBackground(Void... strings) {
            Elements tablerows = null;
            try {
                Document doc = Jsoup.connect("https://www.fussballdaten.de/tuerkei/").get();

                tablerows = doc.select("div#spieleWidgetTabelle233-container tbody tr");


            } catch (IOException e) {
                // TODO Fehlermeldung ausgeben, keine Verbindung zu livescores.
                e.printStackTrace();
            }

            return tablerows;
        }
    }
}
