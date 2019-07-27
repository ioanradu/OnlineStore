package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AbstractMenu {

    public void displayMenu() {

        Integer option = Integer.MAX_VALUE;
        while (option != 0) {
            displayOption();
            option = readOption();
            executeOption(option);
        }
    }

    private Integer readOption() {
        System.out.println("Your option is: ");
        Scanner scanner = new Scanner(System.in);
        try {
            Integer option = scanner.nextInt();
            return option;
        } catch (InputMismatchException e) {
            return -1;
        }
    }

    protected abstract void executeOption(Integer option);

    protected abstract void displayOption();
}