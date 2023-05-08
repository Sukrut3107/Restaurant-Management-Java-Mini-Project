package service;

import Entities.Ingredient;
import Entities.Recipe;
import exceptions.InsufficientIngredientException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeHandler {
    public void checkIfPossibleToPrepareRecipe(Recipe recipe, List<Ingredient> ingredientList){
        Map<Ingredient,Double> composition = recipe.getComposition();
        Map<Ingredient,Double> insufficientIngredient = new HashMap<>();

        for (Ingredient ing: ingredientList){
            if(composition.containsKey(ing)){
                double qtyUsed = composition.get(ing);

                if(qtyUsed>ing.getQty()){
                    insufficientIngredient.put(ing,qtyUsed-ing.getQty());
                }
            }

        }
        if(ingredientList.size()>0){
            throw new InsufficientIngredientException(insufficientIngredient);
        }
    }
}
