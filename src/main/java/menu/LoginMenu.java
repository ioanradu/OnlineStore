package menu;

import model.Store;
import model.User;
import service.BuyService;
import service.LoginService;

import java.util.Scanner;
import java.util.logging.Logger;

public class LoginMenu extends AbstractMenu {

    private Store store;
    private User user;
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public LoginMenu(Store store) {
        this.store = store;
    }

    public void displayOption() {
        System.out.println("========== LOGIN MENU ==========");
        System.out.println(" 1->Login");
        System.out.println(" 2->Display Available Products");
        System.out.println(" 3->Display Shopping Cart");
        System.out.println(" 4->Buy Product");
        System.out.println(" 0->Exit");
        System.out.println("================================");
    }

    public void executeOption(Integer option) {
        LoginService loginService = new LoginService(user);
        BuyService buyService = new BuyService(store, user);
        switch (option) {
            case 1:
                Scanner scanner = new Scanner(System.in);
                System.out.println("User: ");
                String userId = scanner.nextLine();
                System.out.println("Password: ");
                String password = scanner.nextLine();

                User user = loginService.loginUser(userId, password);
                if (user != null) {
                    logger.info("Welcome, " + userId);
                    BuyMenu buyMenu = new BuyMenu(user, store);
                    buyMenu.displayMenu();
                } else {
                    logger.warning("Invalid username / password!");
                }

                break;
            case 2:
                buyService.displayAvailableProducts();
                break;
            case 3:
                System.out.println("You have to login first, before start buying products!");
                break;
            case 4:
                System.out.println("You have to login first, before start buying products!");
                break;
            case 0:
                logger.info("Exiting...");
                break;
            default:
                logger.warning("Invalid option!");
                break;
        }
    }
}