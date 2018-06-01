package ch.oezcy.superfan;

public class TeamSelection {

    //TODO zwei object 'Team' -> erstelle ein objekt Team, welche abgefüllt werden beim Laden.
    //TODO die id des Team wird dana verwendet um die Datenbank zu durchsuchen, denn in Spielabläufen steht nicht der korrekte name , vgl Basaksehir.

    private Team selection1;
    private Team selection2;

    //new object because otherwise binding not works
    public TeamSelection selectTeam(Team sel){
        TeamSelection newSelection = new TeamSelection();

        newSelection.selection1 = this.selection2;
        newSelection.selection2 = sel;

        return newSelection;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(selection1 != null){
            sb.append(this.selection1.getName() + " ");
        }
        if(selection2 != null){
            sb.append(this.selection2.getName());
        }
        return sb.toString();
    }


}
