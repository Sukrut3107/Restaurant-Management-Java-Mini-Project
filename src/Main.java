import Entities.*;

import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

        private List<Sales> salesList;
        private List<Expense> expenseList;
        private double availableMoney;
        private List<Ingredient> ingredientList;
        private List<Recipe> recipeList;

    public static void main(String[] args) {
        //Event loop is infinite loop
        CommandType currentCommand = CommandType.NO_COMMAND;
        while (true){
        if(currentCommand == CommandType.NO_COMMAND){
            displayPrompt();
        }
        if(currentCommand == CommandType.EXIT){
            System.exit(0);
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


}