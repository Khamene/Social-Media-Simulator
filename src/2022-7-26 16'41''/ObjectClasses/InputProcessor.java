package ObjectClasses;

import Exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputProcessor {
    Scanner scanner;
    boolean running=true;
    InputProcessor(Scanner scanner){
        this.scanner=scanner;
    }
    public void inputProcess(Scanner scanner){
        welcomeMenu();
        while(running){
            String inputText=scanner.nextLine();
            if(inputText.equalsIgnoreCase("-Exit")){
                running=false;
            }
            else if(inputText.startsWith("-Login")){
                String[] splitedInput=inputText.split("\\s");
                if(splitedInput.length==3&&splitedInput[1].length()>=7&&splitedInput[2].length()>=7&&
                        ifThereIsNumber(splitedInput[0])) {
                    loginIntoApplication(splitedInput);
                    mainMenu();
                }
                else{
                    loginFailed();
                }
            }
            else if(inputText.startsWith("-CreateAccount")){
                createAccount();
                welcomeMenu();
            }
            else if(inputText.startsWith("-ChaneEmail")&&checkIfLoggedIn()){
                System.out.print("Ne Email: ");
                String newEmail=scanner.nextLine();
                changeEmail(newEmail);
            }
            else if(inputText.startsWith("-ChangePrivacyMode")&&checkIfLoggedIn()){
                System.out.println("do you want your account be private? : ");
                String answer=scanner.nextLine();
                changePrivacy(answer);
            }
            else if(inputText.startsWith("-ChangeBirthday")&&checkIfLoggedIn()){
                System.out.println("ned birthday(mm/dd/yyyy): ");
                String birthday=scanner.nextLine();
                changeBirthday(birthday);
            }
            else if(inputText.startsWith("-ChangeUserName")&&checkIfLoggedIn()){
                String newUserName="a a";
                while(newUserName.split("\\s").length==1) {
                    System.out.print("new UserName(Can't use space): ");
                    newUserName = scanner.nextLine();
                }
                changeUserName(newUserName);
            }
            else if(inputText.startsWith("-ChangeGender")&&checkIfLoggedIn()){
                System.out.println("tell us your gender with 1 for male and 2 for female");
                String gender=scanner.nextLine();
                changeGender(gender);
            }
            else if(inputText.startsWith("-Follow")&&checkIfLoggedIn()){
                follow(inputText.split("\\s"));
            }
            else if(inputText.equalsIgnoreCase("-CREATE POST")&&checkIfLoggedIn()){
                System.out.println("type your Text here: ");
                String postText=scanner.nextLine();
                postProgress(postText);
            }
            else if(inputText.equalsIgnoreCase("-show Comments")&&checkIfLoggedIn()){
                System.out.println("please say PostID to show Comments");
                String postID=scanner.nextLine();
                showComments(postID);
            }
            else if(inputText.equalsIgnoreCase("-show Likes")&&checkIfLoggedIn()){
                System.out.println("please say PostID to show Likes");
                String postID=scanner.nextLine();
                showLikes(postID);
            }
            else if(inputText.equalsIgnoreCase("-showStats")&&checkIfLoggedIn()){
                showStats();
            }
            else if(inputText.startsWith("-Direct Message")){
                System.out.println("who is going to see your message: ");
                String receiverID=scanner.nextLine();
                directMessage(receiverID);
            }
            else if(inputText.equalsIgnoreCase("Edit Message")&&checkIfLoggedIn()){
                System.out.println("write down messageID to edit: ");
                String messageID=scanner.nextLine();
                editMessage(messageID);
            }
            else if(inputText.equalsIgnoreCase("-showDirectMessage")&&checkIfLoggedIn()){
                System.out.println("who do you want to see your message(receiverUserName) : ");
                String secondUserID=scanner.nextLine();
                showDirectMessages(secondUserID);
            }
            else if(inputText.equalsIgnoreCase("-DeleteMessage")&&checkIfLoggedIn()){
                String messageID=scanner.nextLine();
                deleteMessage(messageID);
            }
            else if(inputText.startsWith("-Comment")&&checkIfLoggedIn()){
                commentingOnPost(inputText.split("\\s",3));
            }
            else if(inputText.startsWith("-ShowPage")&&checkIfLoggedIn()){
                String userName=inputText.split("\\s",2)[1];
                showPage(userName);
            }
            else if(inputText.startsWith("DeleteAccount")&&checkIfLoggedIn()){
                System.out.println("you need to tell us the password for being sure about it");
                System.out.print("password: ");
                String password=scanner.nextLine();
                deleteUser(password);
            }
            else if(inputText.startsWith("-Like")&&checkIfLoggedIn()){
                String postID=inputText.split("\\s",2)[1];
                likeTweet(postID);
            }
            else if(inputText.startsWith("-Unlike")&&checkIfLoggedIn()){
                String postID=inputText.split("\\s",2)[1];
                unLikeTweet(postID);
            }
            else if(inputText.startsWith("-showRequests")&&checkIfLoggedIn()){
                showRequests();
            }
            else if(inputText.startsWith("-AcceptRequest")&&checkIfLoggedIn()){
                acceptFollowReq(inputText.split("\\s",2)[1]);
            }
            else if(inputText.startsWith("-AccountStat")&&checkIfLoggedIn()){
                accountStats();
            }
            else if(inputText.startsWith("-SendDirectMessage")&&checkIfLoggedIn()){
                System.out.print("userName of receiver: ");
                String receiver=scanner.nextLine();
                System.out.print("now type your text: ");
                String messageText=scanner.nextLine();
                sendDM(messageText,receiver);
            }
            else if(inputText.startsWith("-GroupMessage")&&checkIfLoggedIn()){
                System.out.print("GroupName receiving your message: ");
                String receivingGroup=scanner.nextLine();
                System.out.print("now type your text: ");
                String messageText=scanner.nextLine();
                sendGroupMessage(messageText,receivingGroup);
            }
            else if(inputText.startsWith("-DeleteComment")&&checkIfLoggedIn()){
                deleteComment(inputText.split("\\s")[1]);
            }///deleteComment postID
            else if(inputText.equalsIgnoreCase("-CreateGroup")&&checkIfLoggedIn()){
                System.out.print("give us a name for your group: ");
                String groupName=scanner.nextLine();
                createGroup(groupName);
                groupHelp();
            }//just type -command
            else if(inputText.equalsIgnoreCase("-AddMember")&&checkIfLoggedIn()){
                System.out.print("in what group you want to add: ");
                String groupName=scanner.nextLine();
                addMember(groupName);
            }//just type -command
            else if(inputText.equalsIgnoreCase("-RemoveMember")&&checkIfLoggedIn()){
                System.out.print("in what group you want to remove: ");
                String groupName=scanner.nextLine();
                removeMember(groupName);
            }//just type -command
            else if(inputText.equalsIgnoreCase("-BanMember")&&checkIfLoggedIn()){
                System.out.print("in what group you want to ban: ");
                String groupName=scanner.nextLine();
                banMember(groupName);
            }//just type -command
            else if(inputText.equalsIgnoreCase("-UnbanMember")&&checkIfLoggedIn()){
                System.out.print("in what group you want to unban: ");
                String groupName=scanner.nextLine();
                unbanMember(groupName);
            }//just type -command
            else if(inputText.equalsIgnoreCase("-AddAdmin")&&checkIfLoggedIn()){
                System.out.println("in what group you want to add Admin: ");
                String groupName=scanner.nextLine();
                addAdmin(groupName);
            }//just type -command
            else if(inputText.equalsIgnoreCase("-RemoveAdmin")&&checkIfLoggedIn()){
                System.out.println("in what group you want to add Admin: ");
                String groupName=scanner.nextLine();
                removeAdmin(groupName);
            }//just type -command
            else if(inputText.equalsIgnoreCase("-ChangeGroupName")&&checkIfLoggedIn()){
                System.out.println("in what group you want to change name: ");
                String groupName=scanner.nextLine();
                changeGroupName(groupName);
            }//just type -command
            else if(inputText.equalsIgnoreCase("-forwardMessage")&&checkIfLoggedIn()) {
                System.out.println("messageID you want to forward: ");
                String messageID=scanner.nextLine();
                forwardMessageProgress(messageID);
            }
            else{
                System.out.println("invalid command");
            }

        }
    }
    public boolean ifThereIsNumber(String needToGetCheck){
        char[] checkCharacters = needToGetCheck.toCharArray();
        for(char singleChar:checkCharacters){
            if(Character.isDigit(singleChar)){
                return true;
            }
        }
        return false;
    }
    public void loginFailed(){
        System.out.println("be sure you are not clicking space more than 2 time :).");
        System.out.println("be sure you have no space and have at least one single digit in your username");
        System.out.println("be sure you have no space and at least 7 character in your username and password");
    }
    public void welcomeMenu(){
        System.out.println("Hello, welcome to our social media application.");
        System.out.println("you can Login with typing -login username password ");
        System.out.println("Or if you don't have an account you can create with typing -CreateAccount and " +
                "answer following questions");
    }
    public void groupHelp(){
        System.out.println("you can change the group by using :");
        System.out.println("-AddMember for add new a member");
        System.out.println("-RemoveMember for remove a member");
        System.out.println("-BanMember for ban a user");
        System.out.println("UnbanMember a user which was banned");
        System.out.println("-AddAdmin for promote a member to admin");
        System.out.println("-RemoveAdmin for demote a admin to member");
        System.out.println("-ChangeGroupName for change the group name");
    }
    public boolean checkIfLoggedIn(){
        return User.getLoggedInUsername() != null;
    }
    public void mainMenu(){
        System.out.println("hello, you are Logged in! you can use: ");
        System.out.println("-Create Post for posting");
        System.out.println("-Direct Message for DM someone");
        System.out.println("-ChangeEmail/PrivacyMode/Birthday/UserName/Gender for changing your Email/PrivacyMode/Birthday/UserName/Gender");
        System.out.println("-CreateGroup for creating group");
        System.out.println("-Follow for follow someone you know");
        System.out.println("-Comment postID text(as comment) for commenting on some post");
    }
    public void loginIntoApplication(String[] information){
        try {
            User.login(information[1],information[2]);

        } catch (UserDoesNotExistException | PasswordIncorrectException e) {
            e.printStackTrace();
        }
    }
    public void createAccount(){
        String userName=pickingUserName();
        String password=pickingPassword();
        System.out.print("write your age: ");
        String age=scanner.nextLine();
        System.out.print("firstname: ");
        String firstname=scanner.nextLine();
        System.out.print("lastname: ");
        String lastname=scanner.nextLine();
        System.out.print("Email Address: ");
        String emailAddress=scanner.nextLine();
        String phoneNumber="00";
        while(phoneNumber.length()!=11){
            System.out.print("Phone Number(11 digit) : ");
            phoneNumber=scanner.nextLine();
        }
        System.out.print("Gender (1 for male,2 for female): ");
        String gender=scanner.nextLine();
        System.out.println("Do you want have a private Account?(yes/no): ");
        String isPrivate=scanner.nextLine();
        String birthday=pickingBirthdayAndCheckFormat();
        System.out.println("Commercial or Normal?(answer with C for commercial or N for Normal)");
        String accountType=scanner.nextLine();
        System.out.println("choose what question you want answer?(write down number,example:1 2)");
        System.out.println("1-WHAT WAS THE NAME OF YOUR CHILDHOOD PET?");
        System.out.println("2-WHERE DID YOUR PARENTS MEET IN THE FIRST TIME?");
        System.out.println("3-WHAT IS THE NAME OF YOUR FIRST ELEMENTARY TEACHER?");
        System.out.println("4-WHAT IS YOUR FAVORITE SPORTS CLUB?");
        System.out.println("5-WHAT WAS YOUR CHILDHOOD DREAM JOB?");
        String answer=scanner.nextLine();
        String[] answerNumber=answer.split("\\s");
        System.out.println("Answer to question "+answerNumber[0]);
        String securityQuestion1=scanner.nextLine();
        System.out.println("Answer to question "+answerNumber[1]);
        String securityQuestion2=scanner.nextLine();
        if(userType(accountType)){
            PersonalUser.createNewPersonalAccount(userName,password,firstname,lastname,emailAddress,phoneNumber,genderDetector(gender),
                    isPrivateAccount(isPrivate),dateInput(birthday),Integer.parseInt(answerNumber[0]),securityQuestion1,
                    Integer.parseInt(answerNumber[1]),securityQuestion2);
        }
        else {
            BusinessUser.createBusinessAccount(userName,password,firstname,lastname,emailAddress,phoneNumber,genderDetector(gender),
                    isPrivateAccount(isPrivate),dateInput(birthday),Integer.parseInt(answerNumber[0]),securityQuestion1,
                    Integer.parseInt(answerNumber[1]),securityQuestion2);
        }
        }
    public String  pickingUserName(){
        System.out.print("choose a username:(at least 7 character no space and at least one digit) ");
        String userName=scanner.nextLine();
        if(userName.length()<7||!ifThereIsNumber(userName)||userName.split("\\s").length>1){
            pickingUserName();
        }
        return userName;
    }
    public String  pickingPassword(){
        System.out.print("choose a password:(at least 7 character no space) ");
        String password=scanner.nextLine();
        if(password.length()<7||password.split("\\s").length>1){
            pickingPassword();
        }
        return password;
    }
    public void changeEmail(String newEmail){
        try {
            User.changeEmail(newEmail);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void changePrivacy(String answer){
        if(answer.equalsIgnoreCase("yes")){
            try {
                User.changePrivacyMode(true);
            } catch (NoUserLoggedInException e) {
                e.printStackTrace();
            }
        }
        else if(answer.equalsIgnoreCase("No")){
            try {
                User.changePrivacyMode(false);
            } catch (NoUserLoggedInException e) {
                e.printStackTrace();
            }
        }
    }
    public void changeBirthday(String birthday){
        try {
            User.changeBirthday(dateInput(birthday));
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void changeUserName(String newUserName){
        try {
            User.changeUsername(newUserName);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void changeGender(String gender){
        try {
            User.changeGender(genderDetector(gender));
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    private boolean genderDetector(String gender){
        return gender.equalsIgnoreCase("1");
    }
    private boolean isPrivateAccount(String isPrivate){
        return isPrivate.equalsIgnoreCase("yes");
    }
    private boolean userType(String userType){
        return userType.equalsIgnoreCase("N");
    }
    public static LocalDate dateInput(String userInput) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(userInput, dateFormat);
    }
    public void follow(String[] information){
        //information[1] gotFollowedUser
        try {
            User.sendFollowRequest(information[1]);
        } catch (UserAlreadyFollowedException | UserDoesNotExistException | NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void postProgress(String postText){
        System.out.print("Are you sure you want post this? ");
        String postExecution=scanner.nextLine();
        if(postExecution.equalsIgnoreCase("yes")){
            try {
                User.tweet(postText);
            } catch (NoUserLoggedInException e) {
                e.printStackTrace();
            }
     //       try {
     //           /// we need take UserID of the user who is posting
     //           Post.publishPost("Need take User ID",postText);
     //       } catch (PostContentNullException e) {
     //           e.printStackTrace();
     //      }
        }

    }
    public void showComments(String postID){
//show from sql
    }
    public void showLikes(String postID){
//show from sql
    }
    public void showStats(){
        try {
            System.out.println("please say PostID to show stats");
            String postID=scanner.nextLine();
            BusinessUser.viewPostStats(postID);
        } catch (NoUserLoggedInException | UserNotBusinessException | PostDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void directMessage(String receiverUserName){
        String messageText=scanner.nextLine();
        try {
            User.sendDirectMessage(receiverUserName,messageText);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }

    }
    public void editMessage(String messageID){
        System.out.println("type your Edited Text: ");
        String content=scanner.nextLine();
  //      try {
  //          Message.editMessage(messageID,content);
  //      } catch (MessageDoesNotExistException | MessageContentNullException e) {
  //          e.printStackTrace();
  //      }
  //      }
        try {
            User.editMessage(messageID,content);
        } catch (NoUserLoggedInException | UnauthorisedEditException e) {
            e.printStackTrace();
        }
    }
    public void showDirectMessages(String receiverID){

    }///////////////
    public void deleteMessage(String messageID){
        try {
            User.deleteMessage(messageID);
        } catch (NoUserLoggedInException | UnauthorisedEditException e) {
            e.printStackTrace();
        }
 //       try {
 //           Message.deleteMessage(messageID);
 //       } catch (MessageDoesNotExistException e) {
 //           e.printStackTrace();
 //       }
    }
    public void commentingOnPost(String[] information){
        try {
            User.commentOnTweet(information[1],information[2]);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void showPage(String userName){
        try {
            User.visitPage(userName);
        } catch (UserDoesNotExistException | NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(String password){
        try {
            User.deleteUser(password);
        } catch (PasswordIncorrectException | NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void likeTweet(String postID){
   //     try {
   //         Post.likePost(inActionUserName,postID);
   //     } catch (PostDoesNotExistException e) {
   //         e.printStackTrace();
   //     }
   //     try {
   //         Like.likePost(inActionUserName,postID);
   //     } catch (PostAlreadyLikedException e) {
   //        e.printStackTrace();
   //     }
        try {
            User.likeTweet(postID);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void unLikeTweet(String postID){
 //            try {
 //           Post.unlikePost(inActionUserName,postID);
 //      } catch (PostDoesNotExistException e) {
 //         e.printStackTrace();
 //        }
        try {
            User.unlikeTweet(postID);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
  //      try {
  //          Like.unlikePost(inActionUserName,postID);
  //      } catch (PostNotLikedException e) {
  //          e.printStackTrace();
  //      }
    }
    public void showRequests(){
        try {
            User.manageFollowRequest();
            ///we also need to show them to manage it by accpetRequest//
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void acceptFollowReq(String followerID){
        try {
            User.acceptFollowRequest(followerID);
        } catch (NoUserLoggedInException | UserDoesNotExistException | FollowRequestDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void accountStats(){
        try {
            BusinessUser.viewAccountStats();
        } catch (NoUserLoggedInException | UserNotBusinessException e) {
            e.printStackTrace();
        }
    }
    public void sendDM(String textDM,String receiver){
        try {
            User.sendDirectMessage(User.getUserID(receiver),textDM);
        } catch (NoUserLoggedInException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void sendGroupMessage(String textGroupMessage,String receivingGroup){
        try {
            User.sendGroupMessage(Group.getGroupID(receivingGroup),textGroupMessage);
        } catch (NoUserLoggedInException | GroupDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void deleteComment(String postID){
        try {
            User.deleteComment(postID);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void createGroup(String name){
        try {
            User.createNewGroup(name);
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void addMember(String groupName){
        try {
            System.out.print("UserName to add: ");
            String getAddUserName=scanner.nextLine();
            Group.addMember(User.getUserID(getAddUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void removeMember(String groupName){
        try {
            System.out.println("UserName to remove: ");
            String getRemoveUserName=scanner.nextLine();
            Group.removeMember(User.getUserID(getRemoveUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void banMember(String groupName){
        try {
            System.out.println("UserName to ban: ");
            String getBanUserName=scanner.nextLine();
            Group.banMember(User.getUserID(getBanUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException e) {
            e.printStackTrace();
        }

    }
    public void unbanMember(String groupName){
        try {
            System.out.println("UserName to ban: ");
            String getBanUserName=scanner.nextLine();
            Group.unbanMember(User.getUserID(getBanUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException e) {
            e.printStackTrace();
        }

    }
    public void addAdmin(String groupName){
        try {
            System.out.print("UserName to admin: ");
            String adminSuggestUserName=scanner.nextLine();
            Group.addAdmin(User.getUserID(adminSuggestUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void removeAdmin(String groupName){
        try {
            System.out.println("admin userName to remove: ");
            String removeFromAdminUserName=scanner.nextLine();
            Group.removeAdmin(User.getUserID(removeFromAdminUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void changeGroupName(String groupName){
        try {
            System.out.println("write new GroupName: ");
            String newGroupName=scanner.nextLine();
            Group.changeGroupName(groupName,newGroupName);
        } catch (UserNotAdminException | GroupDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void forwardMessageProgress(String messageID){
        System.out.print("Do you want to send it to the group (yes or no,no for direct message)?");
        String yesNoAnswer=scanner.nextLine();
        if(yesNoAnswer.equalsIgnoreCase("yes")){
            System.out.print("groupName: ");
            String groupname=scanner.nextLine();
            try {
                User.forwardMessage(messageID,Group.getGroupID(groupname));
            } catch (NoUserLoggedInException | UserDoesNotExistException | MessageNotIntendedForUserException |
                    GroupDoesNotExistException e) {
                e.printStackTrace();
            }
        }
        if(yesNoAnswer.equalsIgnoreCase("No")){
            System.out.print("userName: ");
            String userName=scanner.nextLine();
            try {
                User.forwardMessage(messageID,User.getUserID(userName));
            } catch (NoUserLoggedInException | MessageNotIntendedForUserException | UserDoesNotExistException e) {
                e.printStackTrace();
            }
        }
    }
    public String  pickingBirthdayAndCheckFormat(){
        System.out.println("Your Birthday (mm/dd/yyyy): ");
        String birthday=scanner.nextLine();
        String[] birthdaySplited=birthday.split("/");
        if(birthdaySplited.length==3){
            char[] ch=birthdaySplited[0].toCharArray();
            char[] ch1=birthdaySplited[0].toCharArray();
            char[] ch2=birthdaySplited[0].toCharArray();
            for(char c:ch){
                if(!Character.isDigit(c)){
                    return pickingBirthdayAndCheckFormat();
                }
            }
            for(char c:ch1){
                if(!Character.isDigit(c)){
                    return pickingBirthdayAndCheckFormat();
                }
            }
            for(char c:ch2){
                if(!Character.isDigit(c)){
                    return pickingBirthdayAndCheckFormat();
                }
            }
        }
        return birthday;
    }
}
