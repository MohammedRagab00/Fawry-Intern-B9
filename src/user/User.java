package user;

import cart.entity.Cart;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private double balance;
    private final Cart cart;

    public User(String name, String email, String phoneNumber, double balance) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        setBalance(balance);
        cart = new Cart(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("Balance should be zero or positive");
        }
    }

    public void addBalance(double amount) {
        if (Double.MAX_VALUE - amount < balance) {
            throw new IllegalArgumentException("Your balance is full, contact the admin");
        }
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Added amount should be positive");
        }
    }

    public void withDraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
            } else {
                throw new IllegalArgumentException("Insufficient balance, check your account and try again");
            }
        } else {
            throw new IllegalArgumentException("Added amount should be positive");
        }
    }

    public Cart getCart() {
        return cart;
    }
}
