package ch.oezcy.superfan;

public class User {
    private String firstName;
    private String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String firstname() {
        return this.firstName;
    }
    public String lastname() {
        return this.lastName;
    }

    //test
    public void setLastName(String value){
        this.lastName = value;
        this.firstName = value;
    }
}
