package Entities;

import java.util.Map;

public class PurchaseOrder {
    private double amount;

    private Map<Ingredient,Integer> composition;

    public PurchaseOrder(double amount, Map<Ingredient, Integer> composition) {
        this.amount = amount;
        this.composition = composition;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Map<Ingredient, Integer> getComposition() {
        return composition;
    }

    public void setComposition(Map<Ingredient, Integer> composition) {
        this.composition = composition;
    }
}
