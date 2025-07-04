package cart.entity;

import user.User;

import java.util.HashSet;
import java.util.Set;

public class Cart {
    private final User user;
    private final Set<CartItem> cartItems;

    public Cart(User user) {
        this.user = user;
        cartItems = new HashSet<>();
    }

    public User getUser() {
        return user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void addCartItem(CartItem item) {
        cartItems.add(item);
    }
}
