package cart.validator;

import cart.entity.Cart;
import product.property.Expirable;

public class ExpirationValidator implements CartValidator {
    @Override
    public void validate(Cart cart) {
/*
        boolean expired = cart.getCartItems().stream()
                .map(CartItem::getProduct)
                .filter(product -> product instanceof Expirable)
                .map(product -> (Expirable) product)
                .anyMatch(Expirable::isExpired);
*/
        boolean expired = cart.getCartItems().stream().anyMatch(item -> {
            if (item.getProduct() instanceof Expirable expirable) {
                return expirable.isExpired();
            }
            return false;
        });
        if (expired) {
            throw new IllegalStateException("Product has expired, check the cart and try again");
        }
    }
}
