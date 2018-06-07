package ch.oezcy.superfan.utility;

import ch.oezcy.superfan.db.entity.Team;

public class TeamSelection {

    private Team selection1;
    private Team selection2;

    //new object because otherwise binding not works
    public TeamSelection selectTeam(Team sel){
        TeamSelection newSelection = new TeamSelection();

        newSelection.selection1 = this.selection2;
        newSelection.selection2 = sel;

        return newSelection;
    }

    public Team getSelection1() {
        return selection1;
    }

    public void setSelection1(Team selection1) {
        this.selection1 = selection1;
    }

    public Team getSelection2() {
        return selection2;
    }

    public void setSelection2(Team selection2) {
        this.selection2 = selection2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(selection1 != null){
            sb.append(this.selection1.name + " ");
        }
        if(selection2 != null){
            sb.append(this.selection2.name);
        }
        return sb.toString();
    }


}
