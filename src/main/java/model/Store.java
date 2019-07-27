package model;

import service.BuyService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Store {
    private String name;
    private String address;
    private Map<Product, Integer> products;

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
        this.products = new HashMap<>();
    }

    public boolean doesTheProductExist(String productToCheck) {
        for (Product product : products.keySet()) {
            if (product.getName().equals(productToCheck)) {
                return true;
            }
        }
        System.out.println("The product does not exist!");
        return false;
    }

    public void updateQuantityForAProduct(Product product, Integer quntityToSubstract) {
        Integer currentQuatity = products.get(product);
        products.put(product, currentQuatity - quntityToSubstract);
    }

    public BigDecimal getPrice(String nameOfProduct) {
        for (Product product : products.keySet()) {
            if (product.getName().equals(nameOfProduct)) return product.getPrice();
        }
        return null;
    }


    public void buildProductList() {
        BuyService buyService = new BuyService();
        setProducts(buyService.readAvailableProducts());
        if (products.isEmpty()) {
            System.out.println("No products in store!");
        }
    }

    public void addProduct(Product nameOfProduct, Integer quantity) {
        products.put(nameOfProduct, quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : getProducts().keySet()) {
            sb.append("Name: " + product + ", " + "quantity: " + getProducts().get(product));
        }
        return sb.toString();
    }
}