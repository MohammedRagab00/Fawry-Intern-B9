package payment.service;

import cart.dto.TotalDto;
import cart.entity.Cart;
import product.property.Shippable;
import user.User;

public class PaymentService implements IPaymentService {
    @Override
    public TotalDto processPayment(Cart cart) {
        double subTotal = cart.getCartItems().stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        double shipping = cart.getCartItems().stream().mapToDouble(item -> {
            if (item.getProduct() instanceof Shippable shippable) {
                return shippable.getShippingCost() * item.getQuantity();
            }
            return 0;
        }).sum();
        return new TotalDto(subTotal, shipping);
    }

    @Override
    public void chargeUser(User user, TotalDto amount) {
        user.withDraw(amount.subTotal() + amount.shipping());
    }
}
