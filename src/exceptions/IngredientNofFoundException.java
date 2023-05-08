package exceptions;

public class IngredientNofFoundException extends RuntimeException{
    public IngredientNofFoundException(String message){
        super(message);
    }
}
