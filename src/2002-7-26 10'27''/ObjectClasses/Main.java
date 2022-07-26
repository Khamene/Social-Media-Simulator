package ObjectClasses;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        InputProcessor inputProcessor=new InputProcessor(scanner);
        inputProcessor.inputProcess(scanner);
    }
}
