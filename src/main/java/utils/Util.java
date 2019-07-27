package utils;

import model.Product;
import model.Store;
import model.User;

public class Util {

    public static boolean isValidItemLine(String[] args) {
        if (args.length != 3) {
            return false;
        }
        return true;
    }

    public static boolean isUserLoggedIn(User user) {
        if (user != null) {
            return true;
        }
        return false;
    }

    public static boolean areEnoughQuantityOfItemInStore(Store store, Product product, int requiredQuantity) {
        Integer currentQuantity = store.getProducts().get(product);
        if (currentQuantity < requiredQuantity) {
            System.out.println("There are not enough quantity! Current quantity is: " + currentQuantity);
            return false;
        }
        return true;
    }

    public static Integer itemQuantityInStore(Store store, Product product) {
        Integer currentQuantity = store.getProducts().get(product);
        return currentQuantity;
    }

    public static boolean isNumber(String balanceStr) {
        boolean isANumber = false;
        if (balanceStr.charAt(0) == '-' || Character.isDigit(balanceStr.charAt(0))) {
            isANumber = Character.isDigit(balanceStr.charAt(0));
            for (int i = 1; i < balanceStr.length(); i++) {
                isANumber = Character.isDigit(balanceStr.charAt(i));
                if (isANumber == false) {
                    return false;
                }
            }
        }
        return isANumber;
    }
}
