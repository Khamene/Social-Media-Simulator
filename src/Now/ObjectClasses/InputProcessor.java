package ObjectClasses;

import Exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TreeMap;

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
                        ifThereIsNumber(splitedInput[1])) {
                    loginIntoApplication(splitedInput);
                }
                else{
                    loginFailed();
                }
            }
            else if(inputText.equalsIgnoreCase("-ForgotPassword")){
                System.out.println("your username: ");
                String username=scanner.nextLine();
                recoverPasswordProgress(username);
            }
            else if(inputText.startsWith("-CreateAccount")){
                createAccount();
                welcomeMenu();
            }
            else {
                try {
                    if(inputText.equalsIgnoreCase("-ChaneEmail")&&checkIfLoggedIn()){
                        System.out.print("New Email: ");
                        String newEmail=scanner.nextLine();
                        changeEmail(newEmail);
                    }
                    else if(inputText.equalsIgnoreCase("-ChangePrivacyMode")&&checkIfLoggedIn()){
                        System.out.println("do you want your account be private? : ");
                        String answer=scanner.nextLine();
                        changePrivacy(answer);
                    }
                    else if(inputText.startsWith("-ChangeBirthday")&&checkIfLoggedIn()){
                        System.out.println("ned birthday(yyyy/mm/dd): ");
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
                    else if(inputText.equalsIgnoreCase("-ChangeGender")&&checkIfLoggedIn()){
                        System.out.println("tell us your gender with 1 for male and 2 for female");
                        String gender=scanner.nextLine();
                        changeGender(gender);
                    }
                    else if(inputText.startsWith("-Follow")&&checkIfLoggedIn()){
                        follow(inputText.split("\\s"));
                    }
                    else if(inputText.equalsIgnoreCase("-CreatePost")&&checkIfLoggedIn()){
                        System.out.println("type your Text here: ");
                        String postText=scanner.nextLine();
                        postProgress(postText);
                    }
                    else if(inputText.equalsIgnoreCase("-DeletePost")&&checkIfLoggedIn()) {
                        System.out.print("Which post do you want to delete: ");
                        String postID = scanner.nextLine();
                        try {
                            User.untweet(postID);
                        }
                        catch (NoUserLoggedInException | UnauthorisedEditException | UserDoesNotExistException | PostDoesNotExistException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (inputText.equalsIgnoreCase("-SuggestUsers") && checkIfLoggedIn()) {
                        try {
                            User.suggestFollowings();
                        }
                        catch (UserDoesNotExistException e) {
                            e.printStackTrace();
                        }

                    }
                    else if(inputText.equalsIgnoreCase("-ShowLikes")&&checkIfLoggedIn()){
                        System.out.println("please say PostID to show Likes");
                        String postID=scanner.nextLine();
                        showLikes(postID);
                    }
                    else if(inputText.equalsIgnoreCase("-ShowStats")&&checkIfLoggedIn()){
                        showStats();
                    }
                    else if(inputText.equalsIgnoreCase("-EditMessage")&&checkIfLoggedIn()){
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
                        System.out.print("Enter postID to comment: ");
                        String postID = scanner.nextLine();

                        System.out.println("Now enter your comment: ");
                        String comment = scanner.nextLine();
                        commentingOnPost(postID, comment);
                    }
                    else if(inputText.startsWith("-ShowPage")&&checkIfLoggedIn()){
                        String userName=inputText.split("\\s",2)[1];
                        showPage(userName);
                    }
                    else if(inputText.equalsIgnoreCase("DeleteAccount")&&checkIfLoggedIn()){
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
                    else if(inputText.equalsIgnoreCase("-showRequests")&&checkIfLoggedIn()){
                        showRequests();
                    }
                    else if(inputText.startsWith("-AcceptRequest")&&checkIfLoggedIn()){
                        acceptFollowReq(inputText.split("\\s",2)[1]);
                    }
                    else if(inputText.equalsIgnoreCase("-AccountStats")&&checkIfLoggedIn()){
                        accountStats();
                    }
                    else if(inputText.equalsIgnoreCase("-SendDirectMessage")&&checkIfLoggedIn()){
                        System.out.print("userName of receiver: ");
                        String receiver=scanner.nextLine();
                        System.out.print("now type your text: ");
                        String messageText=scanner.nextLine();
                        sendDM(messageText,receiver);
                        messageHelp();
                    }
                    else if(inputText.equalsIgnoreCase("-GroupMessage")&&checkIfLoggedIn()){
                        System.out.print("GroupName receiving your message: ");
                        String receivingGroup=scanner.nextLine();
                        System.out.print("now type your text: ");
                        String messageText=scanner.nextLine();
                        sendGroupMessage(messageText,receivingGroup);
                    }
                    else if(inputText.equalsIgnoreCase("-ShowNewMessages")&&checkIfLoggedIn()){
                        try {
                            User.showNewMessages();
                        } catch (NoUserLoggedInException | UserDoesNotExistException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(inputText.equalsIgnoreCase("-ShowGroups")&&checkIfLoggedIn()){
                        try {
                            User.showMyGroups();
                            groupHelp();
                        } catch (NoUserLoggedInException | UserDoesNotExistException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (inputText.equalsIgnoreCase("-ShowGroupInfo") && checkIfLoggedIn() ) {
                        System.out.println("Enter your desired groupName: ");
                        String groupName = scanner.nextLine();

                        try {
                            String groupID = Group.getGroupID(groupName);
                            User.showGroupInfo(groupID);
                            groupHelp();
                        } catch (GroupDoesNotExistException | NoUserLoggedInException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(inputText.equalsIgnoreCase("-showGroupChat")&&checkIfLoggedIn()){
                        System.out.println("need groupID to show you last few group messages");
                        String groupID=scanner.nextLine();
                        showChatGP(groupID);
                    }
                    else if (inputText.equalsIgnoreCase("-searchMessage") && checkIfLoggedIn()) {
                        System.out.print("Type your search content: ");
                        String content = scanner.nextLine();
                        try {
                            User.searchMessage(content);
                            System.out.print("Which messageID did you mean particularly? ");
                            String messageID = scanner.nextLine();
                            Message.showMessage(messageID, User.getUserID(User.LoggedInUsername));
                        }
                        catch (NoUserLoggedInException | UserDoesNotExistException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (inputText.equalsIgnoreCase("-ShowFeed") && checkIfLoggedIn()) {
                        try {
                            User.mainFeed();
                        }
                        catch (NoUserLoggedInException | UserDoesNotExistException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (inputText.equalsIgnoreCase("-ShowComments") && checkIfLoggedIn()) {
                        System.out.print("Enter the postID for which you want to see the comments: ");
                        String postID = scanner.nextLine();
                        try {
                            User.showComments(postID);
                        }
                        catch (NoUserLoggedInException | UserDoesNotExistException | UnauthorisedEditException | PostDoesNotExistException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(inputText.startsWith("-DeleteComment")&&checkIfLoggedIn()){
                        deleteComment(inputText.split("\\s")[1]);
                    }///deleteComment postID
                    else if(inputText.equalsIgnoreCase("-CreateGroup")&&checkIfLoggedIn()){
                        System.out.print("give us a name for your group: ");
                        String groupName=scanner.nextLine();
                        createGroup(groupName);

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
                        System.out.println("in what group you want to remove Admin: ");
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
                    else if(inputText.startsWith("-BlockUser")&&checkIfLoggedIn()){
                        System.out.print("we also need username of user you want to block: ");
                        String userName=scanner.nextLine();
                        blockUser(userName);
                    }
                    else if(inputText.startsWith("-UnblockUser")&&checkIfLoggedIn()){
                        System.out.print("we also need username of user you want to unblock: ");
                        String userName=scanner.nextLine();
                        unblockUser(userName);
                    }
                    else if(inputText.equalsIgnoreCase("-LogOut")){
                        try {
                            User.logout();
                        } catch (NoUserLoggedInException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("invalid command");
                    }
                } catch (NoUserLoggedInException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //LITTLE BIT OF CHANGE IN THIS FUNCTION YOU MIGHT WANNA TAKE A LOOK
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void recoverPasswordProgress(String username){
        try {
            System.out.println(User.getSecurityQuestions(username));
            System.out.println("answer following question as you have done in createAccount time. be sure you answer them " +
                    "Respectively");
            System.out.println("first question answer: ");
            String answerQuestionOne=scanner.nextLine();
            System.out.println("second question answer: ");
            String answerQuestionTwo=scanner.nextLine();
            try {
                User.forgotPassword(username,answerQuestionOne,answerQuestionTwo);

                System.out.println("Enter your new desired password: ");
                String password1 = scanner.nextLine();
                System.out.println("Repeat your password once more: ");
                String password2 = scanner.nextLine();

                if (password1.equals(password2))
                    User.changePasswordAfterForgotPassword(username, password1);
                else
                    System.out.println("Entered passwords do not match. Failed to reset password...");
            } catch (WrongSecurityAnswerException e) {
                e.printStackTrace();
            }
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
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
    public boolean ifThereIsNoNumber(String needToGetCheck){
        char[] checkCharacters = needToGetCheck.toCharArray();
        for(char singleChar:checkCharacters){
            if(!Character.isDigit(singleChar)){
                return false;
            }
        }
        return true;
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
        System.out.println("if you cant remember your password just type -ForgotPassword");
        System.out.println("you can exit app typing -Exit");
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
    public void messageHelp(){
        System.out.println("Here other command related to messages: ");
        System.out.println("-showDirectMessage for shows the 5last chat with decided user");
        System.out.println("-DeleteMessage for delete message");
        System.out.println("-Edit Message for editing your messages");
        System.out.println("-forwardMessage for send a message from group/user to another group/user");
        System.out.println("-GroupMessage for send a group message");
        System.out.println("-showPage userName you can see other people page ");
    }
    public boolean checkIfLoggedIn() throws NoUserLoggedInException {
        if (User.getLoggedInUsername() != null)
            return true;
        else
            throw new NoUserLoggedInException("No user logged in yet...");
    }
    public void mainMenu(){
        System.out.println("hello, you are Logged in! you can use: ");
        System.out.println("-Create Post for posting");
        System.out.println("-SendDirectMessage for DM someone");
        System.out.println("-showNewMessages for find out what's new");
        System.out.println("-ChangeEmail/PrivacyMode/Birthday/UserName/Gender for changing your Email/PrivacyMode/Birthday/UserName/Gender");
        System.out.println("-ShowGroups for creating group");
        System.out.println("-SendDirectMessage to start direct message");
        System.out.println("-Follow for follow someone you know");
        System.out.println("-Comment postID text(as comment) for commenting on some post");
        System.out.println("-");
        System.out.println("-LogOut for log out and change your account");
        System.out.println("-Exit for closing app");
    }
    public void loginIntoApplication(String[] information){
        try {
            User.login(information[1],information[2]);
            mainMenu();

        } catch (UserDoesNotExistException | PasswordIncorrectException e) {
            e.printStackTrace();
        }
    }
    public void createAccount(){
        System.out.print("choose a username:(at least 7 character no space and at least one digit) ");
        String userName= scanner.nextLine();
        while(userName.length()<7||!ifThereIsNumber(userName)||userName.split("\\s").length>1) {
            System.out.print("choose a username:(at least 7 character no space and at least one digit) ");
            userName = scanner.nextLine();
        }
        String password="";
        while(password.length()<7||password.split("\\s").length>1) {
            password = pickingPassword(password);
        }
        String age="1";
        while(!ageCheckPoint(age)) {
            System.out.print("write your age: ");
            age = scanner.nextLine();
            ageCheckPoint(age);
        }
        System.out.print("firstname: ");
        String firstname=scanner.nextLine();
        System.out.print("lastname: ");
        String lastname=scanner.nextLine();
        System.out.print("Email Address: ");
        String emailAddress=scanner.nextLine();
        while (emailAddress.split("\\.").length!=2 || !emailAddress.endsWith(".com")) {
            System.out.println("Emalil Address: (format isn't right): ");
            emailAddress = scanner.nextLine();
        }
        String phoneNumber="00";
        while(phoneNumber.length()!=11){
            System.out.print("Phone Number(11 digit) : ");
            phoneNumber=scanner.nextLine();
        }
        System.out.print("Gender (1 for male,2 for female): ");
        String gender=scanner.nextLine();
        System.out.println("Do you want have a private Account?(yes/no): ");
        String isPrivate=scanner.nextLine();
        String birthday="Null";
        while(!pickingBirthdayAndCheckFormat(birthday)||birthday.equalsIgnoreCase("null")){
            System.out.println("Your Birthday (yyyy/mm/dd): ");
            birthday=scanner.nextLine();
        }
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
            PersonalUser.createNewPersonalAccount(userName,password,firstname,lastname,emailAddress,phoneNumber,!genderDetector(gender),
                    isPrivateAccount(isPrivate),dateInput(birthday),Integer.parseInt(answerNumber[0]),securityQuestion1,
                    Integer.parseInt(answerNumber[1]),securityQuestion2);
        }
        else {
            BusinessUser.createBusinessAccount(userName,password,firstname,lastname,emailAddress,phoneNumber,!genderDetector(gender),
                    isPrivateAccount(isPrivate),dateInput(birthday),Integer.parseInt(answerNumber[0]),securityQuestion1,
                    Integer.parseInt(answerNumber[1]),securityQuestion2);
        }
    }


    public boolean ageCheckPoint(String age){
        return ifThereIsNoNumber(age);
    }
    public String  pickingUserName(String userName){
        System.out.print("choose a username:(at least 7 character no space and at least one digit) ");
        userName=scanner.nextLine();
        return userName;
    }
    public String  pickingPassword(String password){
        System.out.print("choose a password:(at least 7 character no space) ");
        password=scanner.nextLine();
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
            User.changeGender(!genderDetector(gender));
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
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(userInput, dateFormat);
    }
    public void follow(String[] information){
        //information[1] gotFollowedUser
        try {
            User.sendFollowRequest(information[1]);
        } catch (UserAlreadyFollowedException | UserDoesNotExistException | NoUserLoggedInException | UnauthorisedEditException e) {
            e.printStackTrace();
        }
    }
    public void postProgress(String postText){
        System.out.print("Are you sure you want post this? ");
        String postExecution=scanner.nextLine();
        if(postExecution.equalsIgnoreCase("yes")){
            try {
                User.tweet(postText);
            } catch (NoUserLoggedInException | PostContentNullException | UserDoesNotExistException e) {
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
            System.out.print("please say PostID to show stats: ");
            String postID=scanner.nextLine();
            BusinessUser.viewPostStats(postID);
        } catch (NoUserLoggedInException | UserNotBusinessException | PostDoesNotExistException | UnauthorisedEditException e) {
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
        } catch (NoUserLoggedInException | UnauthorisedEditException | UserDoesNotExistException | MessageDoesNotExistException | MessageContentNullException e) {
            e.printStackTrace();
        }
    }
    public void showDirectMessages(String receiverID){
        try {
            User.showChat(receiverID);
        } catch (UserDoesNotExistException | NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void deleteMessage(String messageID){
        try {
            User.deleteMessage(messageID);
        } catch (NoUserLoggedInException | UnauthorisedEditException | UserDoesNotExistException | MessageDoesNotExistException e) {
            e.printStackTrace();
        }
        //       try {
        //           Message.deleteMessage(messageID);
        //       } catch (MessageDoesNotExistException e) {
        //           e.printStackTrace();
        //       }
    }
    public void commentingOnPost(String postID, String comment){
        try {
            try {
                User.commentOnTweet(postID, comment);
            } catch (UserDoesNotExistException | PostAlreadyCommentedException | PostDoesNotExistException | UnauthorisedEditException e) {
                e.printStackTrace();
            }
        } catch (NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void showPage(String userName){
        try {
            User.visitPage(userName);
        } catch (UserDoesNotExistException | NoUserLoggedInException | UnauthorisedEditException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(String password){
        try {
            User.deleteUser(password);
        } catch (PasswordIncorrectException | NoUserLoggedInException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void likeTweet(String postID){
        try {
            User.likeTweet(postID);
        } catch (NoUserLoggedInException | UserDoesNotExistException | PostDoesNotExistException | UnauthorisedEditException | PostAlreadyLikedException e) {
            e.printStackTrace();
        }
    }
    public void unLikeTweet(String postID){
        try {
            User.unlikeTweet(postID);
        } catch (NoUserLoggedInException | UserDoesNotExistException | PostNotLikedException | UnauthorisedEditException | PostDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void showRequests(){
        try {
            User.manageFollowRequest();
            ///we also need to show them to manage it by accpetRequest//
        } catch (NoUserLoggedInException | UserDoesNotExistException e) {
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
        System.out.print("Show stats for what username: ");
        String username = scanner.nextLine();
        try {
            BusinessUser.viewAccountStats(username);
        } catch (NoUserLoggedInException | UserNotBusinessException | UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    public void sendDM(String textDM,String receiver){
        try {
            System.out.print("do you want this as reply?Yes/No");
            String yesOrNo=scanner.nextLine();
            if(yesOrNo.equalsIgnoreCase("yes")){
                System.out.print("what is messageID you want to reply?");
                String messageID=scanner.nextLine();
                User.replyDMessage(messageID,textDM);
            }
            else if (yesOrNo.equalsIgnoreCase("no")){
                User.sendDirectMessage(receiver,textDM);
            }
        } catch (NoUserLoggedInException | UserDoesNotExistException | MessageContentNullException | UserWasBannedException | MessageDoesNotExistException | MessageNotIntendedForUserException | UnauthorisedEditException e) {
            e.printStackTrace();
        }
    }
    public void sendGroupMessage(String textGroupMessage,String receivingGroup){
        try {
            System.out.print("do you want this as reply?Yes/No");
            String yesOrNo=scanner.nextLine();
            if(yesOrNo.equalsIgnoreCase("yes")){
                System.out.print("what is messageID you want to reply?");
                String messageID=scanner.nextLine();
                User.replyGMessage(messageID,textGroupMessage);
            }
            else if (yesOrNo.equalsIgnoreCase("no")) {
                User.sendGroupMessage(Group.getGroupID(receivingGroup),textGroupMessage);
            }

        } catch (NoUserLoggedInException | GroupDoesNotExistException | MessageContentNullException | UserWasBannedException | UserDoesNotExistException | UserNotMemberException | MessageDoesNotExistException | MessageNotIntendedForUserException | UnauthorisedEditException e) {
            e.printStackTrace();
        }
    }
    public void deleteComment(String postID){
        try {
            User.deleteComment(postID);
        } catch (NoUserLoggedInException | PostNotCommentedException | UserDoesNotExistException | PostDoesNotExistException | UnauthorisedEditException e) {
            e.printStackTrace();
        }
    }
    public void createGroup(String name){
        try {
            User.createNewGroup(name);
        } catch (NoUserLoggedInException | UserDoesNotExistException | GroupAlreadyExists e) {
            e.printStackTrace();
        }
    }
    public void showChatGP(String groupID){
        try {
            User.showGroupMessages(groupID);
        } catch (GroupDoesNotExistException | UserDoesNotExistException | UserNotMemberException | NoUserLoggedInException e) {
            e.printStackTrace();
        }
    }
    public void addMember(String groupName){
        try {
            System.out.print("UserName to add: ");
            String getAddUserName=scanner.nextLine();
            Group.addMember(User.getUserID(getAddUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void removeMember(String groupName){
        try {
            System.out.println("UserName to remove: ");
            String getRemoveUserName=scanner.nextLine();
            Group.removeMember(User.getUserID(getRemoveUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void banMember(String groupName){
        try {
            System.out.println("UserName to ban: ");
            String getBanUserName=scanner.nextLine();
            Group.banMember(User.getUserID(getBanUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException | NullPointerException e) {
            e.printStackTrace();
        }

    }
    public void unbanMember(String groupName){
        try {
            System.out.println("UserName to ban: ");
            String getBanUserName=scanner.nextLine();
            Group.unbanMember(User.getUserID(getBanUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException | NullPointerException e) {
            e.printStackTrace();
        }

    }
    public void addAdmin(String groupName){
        try {
            System.out.print("UserName to admin: ");
            String adminSuggestUserName=scanner.nextLine();
            Group.addAdmin(User.getUserID(adminSuggestUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException | UserNotMemberException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void removeAdmin(String groupName){
        try {
            System.out.println("admin userName to remove: ");
            String removeFromAdminUserName=scanner.nextLine();
            Group.removeAdmin(User.getUserID(removeFromAdminUserName),User.getUserID(User.getLoggedInUsername()),groupName);
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException | NullPointerException | UserNotMemberException e) {
            e.printStackTrace();
        }
    }
    public void changeGroupName(String groupName){
        try {
            System.out.println("write new GroupName: ");
            String newGroupName=scanner.nextLine();
            Group.changeGroupName(groupName,newGroupName, User.getUserID(User.getLoggedInUsername()));
        } catch (UserNotAdminException | GroupDoesNotExistException | UserDoesNotExistException | NullPointerException e) {
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
                User.forwardGMessage(messageID,Group.getGroupID(groupname));
            } catch (NoUserLoggedInException | UserDoesNotExistException | MessageNotIntendedForUserException | GroupDoesNotExistException | MessageDoesNotExistException | MessageContentNullException | UserWasBannedException | UserNotMemberException | UnauthorisedEditException e) {
                e.printStackTrace();
            }
        }
        if(yesNoAnswer.equalsIgnoreCase("No")){
            System.out.print("userName: ");
            String userName=scanner.nextLine();
            try {
                User.forwardDMessage(messageID,User.getUserID(userName));
            } catch (NoUserLoggedInException | MessageNotIntendedForUserException | UserDoesNotExistException | MessageDoesNotExistException | MessageContentNullException | UserWasBannedException | UnauthorisedEditException e) {
                e.printStackTrace();
            }
        }
    }
    public void blockUser(String userName){
        try {
            User.blockUser(userName);
        } catch (NoUserLoggedInException | UserDoesNotExistException | UserAlreadyBlockedException e) {
            e.printStackTrace();
        }
    }
    public void unblockUser(String userName){
        try {
            User.unblockUser(userName);
        } catch (NoUserLoggedInException | UserDoesNotExistException | UserNotBlockedException e) {
            e.printStackTrace();
        }
    }
    public boolean  pickingBirthdayAndCheckFormat(String birthday){
        String[] birthdaySplited=birthday.split("/");
        if(birthdaySplited.length==3){
            char[] ch=birthdaySplited[0].toCharArray();
            char[] ch1=birthdaySplited[1].toCharArray();
            char[] ch2=birthdaySplited[2].toCharArray();
            for(char c:ch){
                if(!Character.isDigit(c)){
                    return false;
                }
            }
            for(char c:ch1){
                if(!Character.isDigit(c)){
                    return false;
                }
            }
            for(char c:ch2){
                if(!Character.isDigit(c)){
                    return false;
                }
            }
        }
        return true;
    }
}