package Entities;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
    private String name;
    private Map<Ingredient,Integer> composition;
    private double amount;

    public Recipe(String name,Map<Ingredient,Integer> composition,double amount){
        this.name = name;
        this.composition = composition;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Ingredient, Integer> getComposition() {
        return composition;
    }

    public void setComposition(Map<Ingredient, Integer> composition) {
        this.composition = composition;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
