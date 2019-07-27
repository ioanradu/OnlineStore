package service;

import model.Product;
import model.Store;
import model.User;
import utils.ApplicationConst;
import utils.TxtFileReader;
import utils.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class BuyService {

    private User user;
    private Store store;
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public BuyService(Store store, User user) {
        this.store = store;
        this.user = user;
    }

    public BuyService() {

    }

    public void displayAvailableProducts() {
        System.out.println("Products:");
        Map<Product, Integer> products = store.getProducts();
        if (products.isEmpty()) {
            System.out.println("No products in store!");
        } else {
            for (Product product : products.keySet()) {
                System.out.println("\t" + product.getName() + " " + products.get(product) + " " + product.getPrice());
            }
        }
        System.out.println();
    }

    public Map<Product, Integer> readAvailableProducts() {
        Map<Product, Integer> products = new HashMap<>();
        TxtFileReader txtFileReader = new TxtFileReader(ApplicationConst.PRODUCTS_FILE_PATH);
        ArrayList<String> lines = txtFileReader.read();
        for (String line : lines) {
            String[] args = line.split(" ");
            if (Util.isValidItemLine(args)) {
                String name = args[0];
                BigDecimal price = new BigDecimal(Integer.parseInt(args[2]));
                int quantity = Integer.parseInt(args[1]);
                Product product = new Product(name, price);
                products.put(product, quantity);
            }
        }
        return products;
    }

    public void displayShopingCart() {
        if (Util.isUserLoggedIn(user)) {
            System.out.println("User: " + user.getName());
            System.out.println("Shopping list: ");
            Map<Product, Integer> productsFromCart = user.getProductsInCart();
            if (productsFromCart.isEmpty()) {
                logger.info("You don't have any product in cart!");
            } else {
                for (Product product : productsFromCart.keySet()) {
                    BigDecimal priceForCurrentProduct = product.getPrice();
                    BigDecimal totalPrice = priceForCurrentProduct.multiply(new BigDecimal(productsFromCart.get(product)));
                    System.out.println("\t" + product.getName() + " " + productsFromCart.get(product) + " " + totalPrice);
                }
            }
            System.out.println();

        } else {
            logger.info("You have to login first!");
        }
    }

    public void buyProduct() {
        Scanner scanner = new Scanner(System.in);
        Product newProduct = null;
        if (Util.isUserLoggedIn(user)) {
            boolean isProduct = false;
            boolean areEnoughItems = false;
            String productStr = null;
            int numberOfItems = 0;
            int quantityInStore = 0;

            displayAvailableProducts();

            while (!isProduct || !areEnoughItems) {
                if (!isProduct) {
                    System.out.println("Choose a product: ");
                    productStr = scanner.nextLine();
                    isProduct = store.doesTheProductExist(productStr);

                    while (!isProduct) {
                        System.out.println("Please enter an available product!");
                        productStr = scanner.nextLine();
                        isProduct = store.doesTheProductExist(productStr);
                    }
                }

                if (!areEnoughItems) {
                    System.out.println("How many items do you want to buy?");

                    String numberOfItemsStr = scanner.nextLine();
                    boolean isANumber = Util.isNumber(numberOfItemsStr);

                    while (!isANumber) {
                        System.out.println("You should enter a number!");
                        numberOfItemsStr = scanner.nextLine();
                        isANumber = Util.isNumber(numberOfItemsStr);
                        System.out.println(isANumber);
                    }
                    numberOfItems = Integer.parseInt(numberOfItemsStr);
                    if (numberOfItems <= 0) {
                        System.out.println("The quantity must be greater than 0!");
                    }
                }


                BigDecimal priceOfProduct = store.getPrice(productStr);
                newProduct = new Product(productStr, priceOfProduct);
                areEnoughItems = Util.areEnoughQuantityOfItemInStore(store, newProduct, numberOfItems);
                quantityInStore = Util.itemQuantityInStore(store, newProduct);

                if (quantityInStore == 0) {
                    System.out.println("You cannot buy this item! Quantity is : 0");
                    break;
                }
            }

            if (numberOfItems < 1 || quantityInStore == 0) {
                System.out.println("The product has not been put in cart!\n" +
                        "You should put at least 1 product in cart to buy a product!");
            }

            if (numberOfItems == 1 && quantityInStore > 0) {
                System.out.println("The product has been put in cart!");
            }

            if (numberOfItems > 0 && quantityInStore > 0) {
                System.out.println("The products have been put in cart!");
            }

            if (numberOfItems > 0 && quantityInStore > 0) {
                updateQuantity(newProduct, numberOfItems);
            }

            while (numberOfItems > 0 && quantityInStore > 0) {
                user.addAndCountProducts(newProduct);
                numberOfItems--;
            }

        } else {
            logger.info("You have to login first!");
        }
    }

    private void updateQuantity(Product product, Integer quantity) {
        store.updateQuantityForAProduct(product, quantity);
    }
}