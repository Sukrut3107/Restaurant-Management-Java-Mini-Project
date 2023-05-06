package service;

import Entities.Expense;
import Entities.Sales;

import java.util.List;

public class AccountHandler {
    public void printSales(List<Sales> salesList){
        for (Sales sales:salesList){
            System.out.println(sales.toString());
        }
    }
    public void printExpenses(List<Expense> expenseList){
        for (Expense expense: expenseList){
            System.out.println(expense.toString());
        }
    }

    public void printProfit(List<Sales> salesList, List<Expense> expenseList){
        double netProfit = 0;
        for (Sales sales: salesList){
            netProfit += sales.getAmount();
        }

        for(Expense expense: expenseList){
            netProfit -= expense.getAmount();
        }

        System.out.println("Profit So far is: "+netProfit+"₹");
    }
}
