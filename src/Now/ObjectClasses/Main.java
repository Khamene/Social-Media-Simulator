package ObjectClasses;

import Functionality.SQLManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SQLManager.initialize();

        Scanner scanner=new Scanner(System.in);
        InputProcessor inputProcessor=new InputProcessor(scanner);
        inputProcessor.inputProcess(scanner);

        SQLManager.finalizeForOnce();
    }
}
