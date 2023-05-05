package Entities;

public class Ingredient {
    private String name;
    private double qty;
    private double rate;

    public Ingredient(String name,double qty, double rate){
        this.name = name;
        this.qty = qty;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
