package ObjectClasses;

import Exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputProcessor {
    Scanner scanner;
    boolean running=true;
    boolean business;
    InputProcessor(Scanner scanner){
        this.scanner=scanner;
    }
    public void inputProcess(Scanner scanner){
        while(running){
            String inputText=scanner.nextLine();
            if(inputText.equalsIgnoreCase("-Exit")){
                running=false;
            }
            else if(inputText.startsWith("-Login")){
                loginIntoApplication(inputText.split("\\s",3));
            }
            else if(inputText.startsWith("-CreateAccount")){
                createAccount();
            }
            else if(inputText.startsWith("-ChaneEmail")){
                System.out.print("Ne Email: ");
                String newEmail=scanner.nextLine();
                changeEmail(newEmail);
            }
            else if(inputText.startsWith("-ChangePrivacyMode")){
                System.out.println("do you want your account be private? : ");
                String answer=scanner.nextLine();
                changePrivacy(answer);
            }
            else if(inputText.startsWith("-ChangeBirthday")){
                System.out.println("ned birthday(mm/dd/yyyy): ");
                String birthday=scanner.nextLine();
                changeBirthday(birthday);
            }
            else if(inputText.startsWith("-ChangeUserName")){
                String newUserName="a a";
                while(newUserName.split("\\s").length==1) {
                    System.out.print("new UserName(Can't use space): ");
                    newUserName = scanner.nextLine();
                }
                changeUserName(newUserName);
            }
            else if(inputText.startsWith("-ChangeGender")){
                System.out.println("tell us your gender with 1 for male and 2 for female");
                String gender=scanner.nextLine();
                changeGender(gender);
            }
            else if(inputText.startsWith("-Follow")){
                follow(inputText.split("\\s"));
            }
            else if(inputText.equalsIgnoreCase("-CREATE POST")){
                System.out.println("type your Text here: ");
                String postText=scanner.nextLine();
                postProgress(postText);
            }
            else if(inputText.equalsIgnoreCase("-show Comments")){
                System.out.println("please say PostID to show Comments");
                String postID=scanner.nextLine();
                showComments(postID);
            }
            else if(inputText.equalsIgnoreCase("-show Likes")){
                System.out.println("please say PostID to show Likes");
                String postID=scanner.nextLine();
                showLikes(postID);
            }
            else if(inputText.equalsIgnoreCase("-showStats")){
                showStats();
            }
            else if(inputText.startsWith("-Direct Message")){
                String receiverID=scanner.nextLine();
                directMessage(receiverID);
            }
            else if(inputText.equalsIgnoreCase("Edit Message")){
                System.out.println("write down messageID to edit: ");
                String messageID=scanner.nextLine();
                editMessage(messageID);
            }
            else if(inputText.equalsIgnoreCase("-showDirectMessage")){
                System.out.println("who do you want to see your message(receiverUserName) : ");
                String secondUserID=scanner.nextLine();
                showDirectMessages(secondUserID);
            }
            else if(inputText.equalsIgnoreCase("-DeleteMessage")){
                String messageID=scanner.nextLine();
                deleteMessage(messageID);
            }
            else if(inputText.startsWith("-Comment")){
                commentingOnPost(inputText.split("\\s",3));
            }
            else if(inputText.startsWith("-ShowPage")){
                String userName=inputText.split("\\s",2)[1];
                showPage(userName);
            }
            else if(inputText.startsWith("DeleteAccount")){
                System.out.println("you need to tell us the password for being sure about it");
                System.out.print("password: ");
                String password=scanner.nextLine();
                deleteUser(password);
            }
            else if(inputText.startsWith("-Like")){
                String postID=inputText.split("\\s",2)[1];
                likeTweet(postID);
            }
            else if(inputText.startsWith("-Unlike")){
                String postID=inputText.split("\\s",2)[1];
                unLikeTweet(postID);
            }
            else if(inputText.startsWith("-showRequests")){
                showRequests();
            }
            else if(inputText.startsWith("-AcceptRequest")){
                acceptFollowReq(inputText.split("\\s",2)[1]);
            }
            else if(inputText.startsWith("-AccountStat")){
                accountStats();
            }
            else if(inputText.startsWith("-SendDirectMessage")){
                System.out.print("userName of receiver: ");
                String receiver=scanner.nextLine();
                System.out.print("now type your text: ");
                String messageText=scanner.nextLine();
                sendDM(messageText,receiver);
            }
            else if(inputText.startsWith("-DeleteComment")){
                deleteComment(inputText.split("\\s")[1]);
            }///deleteComment postID
            else if(inputText.startsWith("-CreateGroup")){
                System.out.print("give us a name for you group: ");
                String groupName=scanner.nextLine();
                createGroup(groupName);
            }
            else{
                System.out.println("invalid command");
            }

        }
    }
    public void loginIntoApplication(String[] information){
        try {
            User.login(information[1],information[2]);
        } catch (UserDoesNotExistException | PasswordIncorrectException e) {
            e.printStackTrace();
        }
    }
    public void createAccount(){
        System.out.print("choose a username: ");
        String userName=scanner.nextLine();
        System.out.print("choose a password: ");
        String password=scanner.nextLine();
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
        System.out.println("Your Birthday (mm/dd/yyyy): ");
        String birthday=scanner.nextLine();
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
            PersonalUser.createNewPersonalAccount(userName,password);
        }
        else {
            BusinessUser.createBusinessAccount(userName,password);
        }
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

    }
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
}
