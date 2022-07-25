import java.time.LocalDate;

public class PersonalUser extends User{
    static int idCounter = 0;

    public PersonalUser(String userID, String firstName, String lastName, String emailAddress,
                        String phoneNumber , String username , String password, boolean gender, LocalDate date, boolean isPrivate){
        super(userID,firstName,lastName,emailAddress,false,phoneNumber,username,password,gender,date, isPrivate);
    }

    public static void createNewPersonalAccount(String username , String password) {
        String userID = assignID();

        //---------------------
    }

    public static String assignID() {
        return String.format("#PU" + idCounter++);
    }

    public static void congratulateOnBirthDay(){
        LocalDate date = LocalDate.now();
    }
}
