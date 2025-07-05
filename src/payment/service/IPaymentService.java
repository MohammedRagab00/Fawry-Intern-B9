package payment.service;

import cart.dto.TotalDto;
import cart.entity.Cart;
import user.User;

public interface IPaymentService {
    TotalDto processPayment(Cart cart);

    void chargeUser(User user, TotalDto amount);
}
