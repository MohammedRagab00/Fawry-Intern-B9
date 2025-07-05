package print.service;

import cart.dto.TotalDto;
import cart.entity.CartItem;

import java.util.Set;

public interface IPrintService {
    void printTheCheck(Set<CartItem> items, TotalDto total);
}
