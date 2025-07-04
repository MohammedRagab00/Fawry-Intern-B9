package cart.service;

import cart.dto.TotalDto;
import cart.entity.Cart;
import cart.entity.CartItem;
import product.property.Expirable;
import product.property.Shippable;

public class CartService implements ICartService {
    private final Cart cart;

    public CartService(Cart cart) {
        this.cart = cart;
    }

    @Override
    public void checkout() {
        checkStockAndExpiration();
        TotalDto total = getTotal();
        cart.getUser().withDraw(total.subTotal() + total.shipping());
        changeStock();

        StringBuilder response = new StringBuilder("** Shipment Notice **\n");
        double totalWeight = 0;

        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct() instanceof Shippable shippable) {
                double itemWeight = shippable.getWeight() * item.getQuantity();
                totalWeight += itemWeight;
                response.append(item.getQuantity())
                        .append("x\t")
                        .append(item.getProduct().getName())
                        .append("\t")
                        .append(itemWeight)
                        .append("g\n");
            }
        }

        response.append("Total package weight ")
                .append(String.format("%.1fkg\n", totalWeight / 1000));

        response.append("\n** Checkout receipt **\n");
        for (CartItem item : cart.getCartItems()) {
            double lineTotal = item.getQuantity() * item.getProduct().getPrice();

            response.append(item.getQuantity())
                    .append("x\t")
                    .append(item.getProduct().getName())
                    .append("\t")
                    .append(lineTotal)
                    .append("\n");
        }

        response.append("----------------------\n")
                .append("Subtotal\t")
                .append(String.format("%.2f", total.subTotal()))
                .append("\n")
                .append("Shipping\t")
                .append(total.shipping())
                .append("\n")
                .append("Amount\t\t")
                .append(String.format("%.2f", total.subTotal() + total.shipping()))
                .append("\n");

        System.out.println(response);
    }

    private void checkStockAndExpiration() {
        boolean problem = cart.getCartItems().stream().anyMatch(item -> item.getProduct().getQuantity() < item.getQuantity());
        if (problem) {
            cart.getCartItems().forEach(item -> item.setQuantity(item.getProduct().getQuantity()));
            throw new IllegalStateException("Not enough stock, check the cart and try again");
        }
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

    private TotalDto getTotal() {
        double subTotal = cart.getCartItems().stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        double shipping = cart.getCartItems().stream().mapToDouble(item -> {
            if (item.getProduct() instanceof Shippable shippable) {
                return shippable.getShippingCost() * item.getQuantity();
            }
            return 0;
        }).sum();
        return new TotalDto(subTotal, shipping);
    }

    private void changeStock() {
        cart.getCartItems().forEach(item -> item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity()));
    }
}
