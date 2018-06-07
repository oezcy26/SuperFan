package ch.oezcy.superfan.utility;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
    Some static methods to help parse the html
 */

public class ParseHelper {

    public static String getTeamNameFromRow(Element row){
        return row.select("td[data-col-seq=2] a").text();
    }

    public static short getTeamPointsFromRow(Element row){
        String teamPoints = row.select("td[data-col-seq=6]").text();
        return Short.valueOf(teamPoints);
    }

    public static String getTeamIdFromRow(Element row){
        Elements link = row.select("td[data-col-seq=2] a");
        String teamHomepage = link.attr("href");

        //extract name
        String[] parts = teamHomepage.split("/");

        return parts[2];
    }
}
