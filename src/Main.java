import Entities.*;
import exceptions.IngredientNofFoundException;
import exceptions.InsufficientMoneyException;
import service.AccountHandler;
import service.IngredientHandler;

import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

        private static List<Sales> salesList;
        private static List<Expense> expenseList;
        private static double availableMoney;
        private static List<Ingredient> ingredientList;
        private static List<Recipe> recipeList;
        //for Ingredient Handler
        private static IngredientHandler ingredientHandler;
        private static AccountHandler accountHandler;

    public static void main(String[] args) {
        //Event loop is infinite loop
        CommandType currentCommand = CommandType.NO_COMMAND;
        Ingredient selectedIngredient = null;
        double ingredientQty = 0;
        while (true) {
            try {
                if (currentCommand == CommandType.NO_COMMAND) {
                    int selectedNumber = displayPrompt();
                    currentCommand = CommandType.values()[selectedNumber];
                } else if (currentCommand == CommandType.VIEW_AVAILABLE_INGREDIENTS) {
                    ingredientHandler.viewIngredients(ingredientList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.ORDER_SPECIFIC_INGREDIENTS) {
                     selectedIngredient = selectIngredient();
                    currentCommand = CommandType.INPUT_INGREDIENT_QTY;
                }
                else if(currentCommand == CommandType.INPUT_INGREDIENT_QTY){
                    ingredientQty = inputIngredientQty();
                    if(ingredientHandler.isPossibleToOrderIngredient(selectedIngredient,ingredientQty,availableMoney)) {
                        System.out.println("Order Successfully Placed");

                        updateIngredientQty(selectedIngredient, ingredientQty);
                        currentCommand = CommandType.NO_COMMAND;
                    }
                    else {
                        throw new InsufficientMoneyException();
                    }
                }
                else if (currentCommand == CommandType.VIEW_TOTAL_SALES) {
                    accountHandler.printSales(salesList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_TOTAL_EXPENSES) {
                    accountHandler.printExpenses(expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_TOTAL_PROFIT) {
                    accountHandler.printProfit(salesList, expenseList);
                    currentCommand = CommandType.NO_COMMAND;
                }

                if (currentCommand == CommandType.EXIT) {
                    System.exit(0);
                }
            }
            catch (InsufficientMoneyException ex){
                System.out.println(ex.getMessage());
                currentCommand = CommandType.NO_COMMAND;

            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static int displayPrompt(){
        System.out.println("***Please Select one of the following Commands***");
        System.out.println("1. View Available Ingredients: ");
        System.out.println("2. Order Specific Ingredients: ");
        System.out.println("3. View Total Sales: ");
        System.out.println("4. View Total Expenses: ");
        System.out.println("5. View Net Profit: ");
        System.out.println("6. Place Order: ");
        System.out.println("7. Exit from the Program: ");

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static Ingredient selectIngredient(){
        Scanner sc = new Scanner(System.in);
        String ingredientName = sc.nextLine();
        for (int i = 0; i < ingredientList.size(); i++) {
            if(ingredientList.get(i).getName().equals(ingredientName)){
                return ingredientList.get(i);
            }
        }
        throw new IngredientNofFoundException(" Ingredient "+ingredientName+" not found ");
    }

    public static double inputIngredientQty(){
        Scanner sc = new Scanner(System.in);
        return sc.nextDouble();
    }

    public static void updateIngredientQty(Ingredient ingredientOrdered, double qtyOrdered){
        for (int i = 0; i < ingredientList.size(); i++) {
            if(ingredientList.get(i).getName().equals(ingredientOrdered.getName())){
                double oldQty = ingredientList.get(i).getQty();
                ingredientList.get(i).setQty(oldQty+qtyOrdered);
            }
        }
    }


}