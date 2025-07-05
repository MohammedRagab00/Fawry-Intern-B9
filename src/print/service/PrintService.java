package print.service;

import cart.dto.TotalDto;
import cart.entity.CartItem;
import product.property.Shippable;

import java.util.Set;

public class PrintService implements IPrintService {
    @Override
    public void printTheCheck(Set<CartItem> items, TotalDto total) {
        StringBuilder response = new StringBuilder();
        double totalWeight = 0;
        for (CartItem item : items)
            if (item.getProduct() instanceof Shippable shippable)
                totalWeight += shippable.getWeight() * item.getQuantity();

        if (totalWeight > 0) {
            response.append("** Shipment Notice **\n");

            for (CartItem item : items) {
                if (item.getProduct() instanceof Shippable shippable) {
                    double itemWeight = shippable.getWeight() * item.getQuantity();
                    response.append(item.getQuantity())
                            .append("x\t")
                            .append(item.getProduct().getName())
                            .append("\t")
                            .append(itemWeight)
                            .append("g\n");
                }
            }

            response.append("Total package weight ")
                    .append(String.format("%.2fkg\n", totalWeight / 1000));
        }
        response.append("\n** Checkout receipt **\n");
        for (CartItem item : items) {
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
}
