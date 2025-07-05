import cart.entity.Cart;
import cart.entity.CartItem;
import cart.service.CartService;
import cart.service.ICartService;
import cart.validator.ExpirationValidator;
import cart.validator.StockValidator;
import payment.service.PaymentService;
import print.service.PrintService;
import product.ExpirableProduct;
import product.ExpirableShippableProduct;
import product.Product;
import product.ShippableProduct;
import user.User;

import java.time.LocalDateTime;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        /*
         * I want to make the example working,
         * so for exception cases you can try the following:

         * For insufficient balance,
         * you can lower the user balance when initializing.

         * For expiration, you can't add an expired product to the products we have,
         * so can remove the condition when testing or add it in a date coming soon.

         * For not enough stocks you can try adding too many to the cart
         * or try adding at first to two different users and check out one after another.

         * For checking out when the cart is empty, try checking out after making the user.
         */

        Product cheese = new ExpirableShippableProduct("Cheese", 10.99, 10
                , LocalDateTime.of(2026, 10, 1, 0, 0, 0), 21, 2);
        Product TV = new ShippableProduct("TV", 9999.99, 2, 1000, 10);


        User user = new User("Mohammed Ragab", "<EMAIL>", "+201556070011", 40000);
        user.getCart().addCartItem(new CartItem(cheese, 4));
        user.getCart().addCartItem(new CartItem(TV, 1));

        var cartService = getCartService(user.getCart());
        cartService.checkout();
        System.out.printf("%s current balance is: %.2f\n", user.getName(), user.getBalance());

        System.out.print("\n---- ---- ---- Another test ---- ---- ----\n");

        Product meat = new ExpirableProduct("Cheese", 16.99, 6
                , LocalDateTime.of(2026, 1, 1, 0, 0, 0));
        Product giftCard = new Product("Gift Cart", 500, 2);

        user.getCart().addCartItem(new CartItem(meat, 4));
        user.getCart().addCartItem(new CartItem(giftCard, 2));

        cartService.checkout();
        System.out.printf("%s current balance is: %.2f\n", user.getName(), user.getBalance());
    }

    public static ICartService getCartService(Cart cart) {
        return new CartService(cart, new PrintService(), new PaymentService(),
                Set.of(new ExpirationValidator(), new StockValidator())
        );
    }
}