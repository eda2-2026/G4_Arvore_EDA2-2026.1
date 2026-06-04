package excecoes;

public class RestauranteFechadoException extends RuntimeException {
    public RestauranteFechadoException() {
        super("O restaurante está fechado no momento");
    }
}