package io;

import Entities.Ingredient;
import Entities.Recipe;
import exceptions.InvalidIngredientException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeIO {
    public List<Recipe> readAllRecipes(String filepath, List<Ingredient> ingredientList) throws FileNotFoundException {
        List<String> lines = CustomFileReader.readFile(filepath);
        List<Recipe> recipeList = new ArrayList<>();

        for(String line :lines){
                String [] splitLine = line.split("");
                String recipeName = splitLine[0];
                double amount = Double.parseDouble(splitLine[1]);
                Map<Ingredient, Double> composition = new HashMap<>();

        for(int i=1; i<splitLine.length; i +=2){
            String ingredientName = splitLine[i];
            double qty = Double.parseDouble(splitLine[i+1]);
            boolean flag = false;

        for (int j=0; j<ingredientList.size();j++) {
            if (ingredientList.get(j).getName().equals(ingredientName)) ;
            {
                flag = true;
                composition.put(ingredientList.get(i),qty);
                break;
            }
        }
        if (flag == false){
            new InvalidIngredientException(" Ingredient "+ ingredientName +" not Found !! ");
        }


        }
        Recipe recipe = new Recipe(recipeName,composition,amount);
        recipeList.add(recipe);
        }
        System.out.println(" Read "+ recipeList.size()+" recipes ");
        return recipeList;
    }
}
