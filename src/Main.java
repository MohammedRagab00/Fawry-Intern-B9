import cart.entity.CartItem;
import cart.service.CartService;
import product.ExpirableShippableProduct;
import product.Product;
import product.ShippableProduct;
import user.User;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Product cheese = new ExpirableShippableProduct("Cheese", 10.99, 10
                , LocalDateTime.of(2026, 10, 1, 0, 0, 0), 21, 2);
        Product TV  = new ShippableProduct("TV", 9999.99, 2, 1000, 10);


        User user = new User("Mohammed Ragab", "<EMAIL>", "+201556070011", 50000);
        user.getCart().addCartItem(new CartItem(cheese, 4));
        user.getCart().addCartItem(new CartItem(TV, 1));

        new CartService(user.getCart()).checkout();
    }
}