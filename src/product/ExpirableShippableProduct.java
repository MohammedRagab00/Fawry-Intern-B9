package product;

import product.property.Expirable;
import product.property.Shippable;

import java.time.LocalDateTime;

public class ExpirableShippableProduct extends Product implements Expirable, Shippable {
    private LocalDateTime expiryDate;
    private double weight;
    private double shipFee;

    public ExpirableShippableProduct(String name, double price, int quantity, LocalDateTime expiryDate, double weight, double shipFee) {
        super(name, price, quantity);
        setExpiryDate(expiryDate);
        setWeight(weight);
        setShipFee(shipFee);
    }

    @Override
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        if (expiryDate.isAfter(LocalDateTime.now())) {
            this.expiryDate = expiryDate;
        } else {
            throw new IllegalArgumentException("Expiry date should be in the future");
        }
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Weight should be positive");
        }
    }

    @Override
    public double getShippingCost() {
        return weight * shipFee;
    }

    public void setShipFee(double shipFee) {
        if (shipFee >= 0) {
            this.shipFee = shipFee;
        } else {
            throw new IllegalArgumentException("Ship fee should be zero or positive");
        }
    }
}
