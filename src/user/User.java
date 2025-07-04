package user;

import cart.Cart;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private double balance;
    private Cart cart;

    public User(String name, String email, String phoneNumber, double balance) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        setBalance(balance);
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

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("Balance should be zero or positive");
        }
    }

    public void addBalance(double amount) {
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
                throw new IllegalArgumentException("Insufficient balance");
            }
        } else {
            throw new IllegalArgumentException("Added amount should be positive");
        }
    }
}
