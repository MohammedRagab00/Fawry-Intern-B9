package cart.entity;

import product.Product;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        setQuantity(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            if (product.getQuantity() >= quantity) {
                this.quantity = quantity;
            } else {
                this.quantity = product.getQuantity();
                throw new IllegalArgumentException("Not enough quantity in stock");
            }
        } else {
            throw new IllegalArgumentException("Quantity should be positive");
        }
    }
}
