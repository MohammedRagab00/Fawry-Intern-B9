package product;

import product.property.Expirable;

import java.time.LocalDateTime;

public class ExpirableProduct extends Product implements Expirable {
    private LocalDateTime expiryDate;

    public ExpirableProduct(String name, double price, int quantity, LocalDateTime expiryDate) {
        super(name, price, quantity);
        setExpiryDate(expiryDate);
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
}
