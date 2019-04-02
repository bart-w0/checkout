package com.bart.checkout;

import java.math.BigDecimal;
import java.util.Scanner;

public class UIHandler {

    private static Scanner keyboard = new Scanner(System.in);

    public static void printWelcome() {
        System.out.println("Welcome in Simple Checkout application!");
    }

    public static void printGoodbye() {
        System.out.println("Good bye!");
    }

    public static String printMenu() {
        System.out.println("");
        System.out.println("Please select option:");
        System.out.println("1) Scan product");
        System.out.println("2) Reset / new customer");
        System.out.println("3) Submit pricing rules");
        System.out.println("4) Finish");
        return keyboard.nextLine();
    }

    public static void printErrorOption() {
        System.out.println("Incorrect option selected. Valid options are: 1, 2, 3, 4, 5");
    }

    public static String getProductSKU() {
        System.out.println("Input product SKU: ");
        return keyboard.nextLine();
    }

    public static void printErrorSku(String sku) {
        System.out.println("Unknown SKU: " + sku);
    }

    public static void printTotal(BigDecimal total) {
        System.out.println("Total: " + total);
    }

    public static String getPricingRules() {
        System.out.println("Please provide pricing rules in format: SKU1,Count1,Price1,Count2,Price2,...;SKU2,Count1,Price1,Count2,Price2;...");
        System.out.println("In example: A, 1, 50, 3, 130; B, 1, 30, 2, 45; C, 1, 20; D, 1, 15");
        System.out.println("Pricing rules: (empty for default)");
        String pricingRules = keyboard.nextLine();
        if(pricingRules.equals("")) {
            pricingRules = SkuHandler.DEFAULT_PRICING_RULE;
        }
        return pricingRules;
    }
}
