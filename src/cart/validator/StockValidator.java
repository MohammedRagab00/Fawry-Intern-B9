package cart.validator;

import cart.entity.Cart;

public class StockValidator implements CartValidator {
    @Override
    public void validate(Cart cart) {
        boolean underStocked = cart.getCartItems().stream().anyMatch(item -> item.getProduct().getQuantity() < item.getQuantity());
        if (underStocked) {
            cart.getCartItems().forEach(item -> item.setQuantity(item.getProduct().getQuantity()));
            throw new IllegalStateException("Not enough stock, check the cart and try again");
        }
    }
}
