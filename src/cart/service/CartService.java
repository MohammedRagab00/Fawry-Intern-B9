package cart.service;

import cart.dto.TotalDto;
import cart.entity.Cart;
import cart.validator.CartValidator;
import payment.service.IPaymentService;
import print.service.IPrintService;

import java.util.Set;

public class CartService implements ICartService {
    private final Cart cart;
    private final IPrintService printService;
    private final IPaymentService paymentService;
    private final Set<CartValidator> validators;

    public CartService(Cart cart, IPrintService printService, IPaymentService paymentService, Set<CartValidator> validators) {
        this.cart = cart;
        this.printService = printService;
        this.paymentService = paymentService;
        this.validators = validators;
    }

    @Override
    public void checkout() {
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty, add items to checkout");
        }
        validateCart();
        TotalDto total = paymentService.processPayment(cart);
        paymentService.chargeUser(cart.getUser(), total);
        changeStock();

        printService.printTheCheck(cart.getCartItems(), total);
        cart.getCartItems().clear();
    }

    private void validateCart() {
        validators.forEach(validator -> validator.validate(cart));
    }

    private void changeStock() {
        cart.getCartItems().forEach(item -> item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity()));
    }
}
