package com.geektrust.backend.models;

import com.geektrust.backend.exceptions.InvalidAmountException;

public class MetroCard extends BaseModel{

    private final String cardNumber;
    private int balance;
    private static final int MIN_AMOUNT = 0;

    public MetroCard(String id, String cardNumber, int balance) {
        this(cardNumber, balance);
        this.id = id;
    }

    public MetroCard(String cardNumber, int balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public int getBalance() {
        return this.balance;
    }

    public void addAmount(int amount) {
        validateAmountForCredit(amount);
        this.balance += amount;
    }

    public void deductAmount(int amount) {
        validateAmountForDebit(amount);
        this.balance -= amount;
    }

    public boolean hasSufficientBalance(int amount) {
        return amount <= this.balance;
    }

    private void validateAmountForCredit(int amount) {
        if (amount <= MIN_AMOUNT)
            throw new InvalidAmountException();
    }

    private void validateAmountForDebit(int amount) {
        if ((amount <= MIN_AMOUNT) || (amount > this.balance))
            throw new InvalidAmountException();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;

        MetroCard other = (MetroCard) obj;
        return (this.cardNumber.equals(other.cardNumber)) && (this.balance == other.balance);
    }
}
