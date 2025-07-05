package cart.service;

import cart.dto.TotalDto;
import cart.entity.Cart;
import print.service.IPrintService;
import product.property.Expirable;
import product.property.Shippable;

public class CartService implements ICartService {
    private final Cart cart;
    private final IPrintService printService;

    public CartService(Cart cart, IPrintService printService) {
        this.cart = cart;
        this.printService = printService;
    }

    @Override
    public void checkout() {
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty, add items to checkout");
        }
        checkStockAndExpiration();
        TotalDto total = getTotal();
        cart.getUser().withDraw(total.subTotal() + total.shipping());
        changeStock();

        printService.printTheCheck(cart.getCartItems(), total);
        cart.getCartItems().clear();
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
