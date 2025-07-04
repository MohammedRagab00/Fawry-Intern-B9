package product.property;

public interface Shippable {
    double getWeight();

    default double getShippingCost() {
        return getWeight() * 1.1;
    }
}
