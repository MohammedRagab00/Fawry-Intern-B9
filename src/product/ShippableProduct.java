package product;

import product.property.Shippable;

public class ShippableProduct extends Product implements Shippable {
    private double weight;
    private double shipFee;

    public ShippableProduct(String name, double price, int quantity, double weight, double shipFee) {
        super(name, price, quantity);
        setWeight(weight);
        setShipFee(shipFee);
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
