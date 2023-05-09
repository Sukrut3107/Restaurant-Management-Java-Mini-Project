import Entities.*;
import exceptions.IngredientNofFoundException;
import exceptions.InsufficientIngredientException;
import exceptions.InsufficientMoneyException;
import exceptions.RecipeNotFoundException;
import service.AccountHandler;
import service.IngredientHandler;
import service.RecipeHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        private static RecipeHandler recipeHandler;

    public static void main(String[] args) {
        //Event loop is infinite loop
        CommandType currentCommand = CommandType.NO_COMMAND;
        Ingredient selectedIngredient = null;
        double ingredientQty = 0;
        Recipe selectedRecipe = null;
        Map<Ingredient, Double> insufficientIngredients = null;
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
                    else if(currentCommand == CommandType.PLACE_ORDER){
                        selectedRecipe = selectRecipe();
                        recipeHandler.checkIfPossibleToPrepareRecipe(selectedRecipe,ingredientList);
                }
                    else if(currentCommand == CommandType.ORDER_MULTIPLE_INGREDIENTS){
                        ingredientHandler.isPossibleToOrderIngredients(insufficientIngredients,availableMoney);
                        purchaseIngredients(insufficientIngredients);
                        currentCommand = CommandType.FINALIZE_ORDER;
                }
                    else if(currentCommand == CommandType.FINALIZE_ORDER){
                        finalizeOrder(selectedRecipe);

                }
                if (currentCommand == CommandType.EXIT) {
                    System.exit(0);
                }
            }
            catch (InsufficientIngredientException ex){
                insufficientIngredients = ex.getInsufficientIngredients();
                currentCommand = CommandType.ORDER_MULTIPLE_INGREDIENTS;

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

    public static Recipe selectRecipe(){
        Scanner sc = new Scanner(System.in);
        String recipeName = sc.nextLine();
        for (int i = 0; i < recipeList.size(); i++) {
            if(recipeList.get(i).getName().equals(recipeName)){
                return recipeList.get(i);
            }
        }
        throw new RecipeNotFoundException("Recipe "+recipeName+" not found !");
    }

    public static void purchaseIngredient(Ingredient ingredientOrdered, double qtyOrdered){
        for (int i=0; i<ingredientList.size();i++){
            if(ingredientList.get(i).getName().equals(ingredientOrdered.getName())){
                double oldQty = ingredientList.get(i).getQty();
                ingredientList.get(i).setQty(oldQty+qtyOrdered);
            }
        }

        double totalAmount = ingredientOrdered.getRate()*qtyOrdered;
        Map<Ingredient, Double> composition = new HashMap<>();
        composition.put(ingredientOrdered,qtyOrdered);
        PurchaseOrder po = new PurchaseOrder(totalAmount,composition);
        expenseList.add(new Expense(totalAmount,po,ExpenseType.PURCHASE));
        availableMoney -= totalAmount;
    }

    public static void  purchaseIngredients(Map<Ingredient,Double> ingredientsToOrder){
        double moneySpent = 0.0;
            for (int i=0; i<ingredientList.size();i++){
                if(ingredientsToOrder.containsValue(ingredientList.get(i))){
                    double oldQty = ingredientList.get(i).getQty();
                    double qtyToOrder = ingredientsToOrder.get(ingredientList.get(i));
                    moneySpent += ingredientList.get(i).getRate()*qtyToOrder;
                    ingredientList.get(i).setQty(oldQty+qtyToOrder);
                }
            }
            PurchaseOrder po = new PurchaseOrder(moneySpent, ingredientsToOrder);
            Expense expense = new Expense(moneySpent,po,ExpenseType.PURCHASE);
            expenseList.add(expense);
            availableMoney -= moneySpent;

    }
        //Finalize Order we are recording sale and decrement the quantity according to the order
    public static void finalizeOrder(Recipe recipe){
        Map<Ingredient, Double> composition = recipe.getComposition();
        for (int i=0; i<ingredientList.size();i++){
            Ingredient currentIngredient = ingredientList.get(i);
            if(composition.containsKey(currentIngredient)){
                double oldQty = currentIngredient.getQty();
                ingredientList.get(i).setQty(oldQty-composition.get(currentIngredient));
            }
        }
        Order order = new Order(recipe,recipe.getAmount());
        Sales sale = new Sales(order,recipe.getAmount());

        salesList.add(sale);
        availableMoney += recipe.getAmount();
    }


}