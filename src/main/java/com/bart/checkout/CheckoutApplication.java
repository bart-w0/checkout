package com.bart.checkout;

import java.math.BigDecimal;

public class CheckoutApplication {

        public static void main(String[] args) {

        UIHandler.printWelcome();

        boolean scanning = true;
        while(scanning) {
            BigDecimal total = SkuHandler.calculateTotal();
            UIHandler.printTotal(total);
            String option = UIHandler.printMenu();
            switch(option) {
                case "1":
                    String sku = UIHandler.getProductSKU();
                    SkuHandler.scanProduct(sku);
                    break;
                case "2":
                    SkuHandler.reset();
                    break;
                case "3":
                    String pricingRules = UIHandler.getPricingRules();
                    SkuHandler.scanPricingRules(pricingRules);
                    break;
                case "4":
                    scanning = false;
                    break;
                default:
                    UIHandler.printErrorOption();
            }
        }

        UIHandler.printGoodbye();

    }
}
