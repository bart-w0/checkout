package com.bart.checkout;

import java.math.BigDecimal;
import java.util.*;

public class SkuHandler {

    public static String DEFAULT_PRICING_RULE = "A, 1, 50, 3, 130; B, 1, 30, 2, 45; C, 1, 20; D, 1, 15";
    private static Map<String, Integer> products;                       //SKU -> Number of items
    private static Map<String, Map<Integer, BigDecimal>> pricing;       //SKU -> (Units -> Price)

    static {
        products = new HashMap<>();
        pricing = new HashMap<>();
        scanPricingRules(DEFAULT_PRICING_RULE);
    }

    public static void scanProduct(String sku) {
        if(pricing.containsKey(sku)) {
            if(products.containsKey(sku)) {
                products.put(sku, products.get(sku) + 1);
            } else {
                products.put(sku, 1);
            }
        } else {
            UIHandler.printErrorSku(sku);
        }
    }

    public static BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for(String sku : products.keySet()) {
            int count = products.get(sku);
            Map<Integer, BigDecimal> prices = pricing.get(sku);
            BigDecimal totalSku = calculateSkuTotal(count, prices);
            total = total.add(totalSku);
        }
        return total;
    }

    private static BigDecimal calculateSkuTotal(int count, Map<Integer, BigDecimal> prices) {
        List<List<Integer>> combinations = new ArrayList<>();
        calculateCombinations(new ArrayList<>(prices.keySet()), count, new ArrayList<>(), combinations);
        BigDecimal total = null;
        for(List<Integer> combination : combinations) {
            BigDecimal partialTotal = new BigDecimal(0);
            for(Integer c : combination) {
                partialTotal = partialTotal.add(prices.get(c));
            }
            if(total == null || partialTotal.compareTo(total) < 0) {
                total = partialTotal;
            }
        }
        return total;
    }

    private static void calculateCombinations(List<Integer> numbers, int count, List<Integer> partial, List<List<Integer>> combinations) {
        int s = 0;
        for (int x : partial) {
            s += x;
        }
        if (s == count) {
            combinations.add(new ArrayList<>(partial));
        }
        if (s >= count) {
            return;
        }
        for(int i=0;i<numbers.size();i++) {
            int n = numbers.get(i);
            ArrayList<Integer> partialRec = new ArrayList<>(partial);
            partialRec.add(n);
            calculateCombinations(numbers,count,partialRec, combinations);
        }
    }

    public static void reset() {
        products = new HashMap<>();
    }

    public static void scanPricingRules(String pricingRules) {
        String[] skuPricing = pricingRules.split(";");
        for(String skuPrice : skuPricing) {
            String sku = skuPrice.substring(0, skuPrice.indexOf(",")).trim();
            skuPrice = skuPrice.substring(skuPrice.indexOf(",")+1);
            String[] prices = skuPrice.split(",");
            Map<Integer, BigDecimal> pricesMap = new HashMap<>();

            for(int i = 0; i < prices.length;) {
                pricesMap.put(Integer.parseInt(prices[i].trim()), new BigDecimal(Double.parseDouble(prices[i+1].trim())));
                i = i+2;
            }
            pricing.put(sku, pricesMap);
        }
    }
}
