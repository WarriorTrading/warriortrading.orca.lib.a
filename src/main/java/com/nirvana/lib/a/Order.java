package com.nirvana.lib.a;

public class Order {
    private Long id;
    private Double amount;
    private String symbol = "AAPL";

    public Order() {}

    public Order(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String toString() {
        return "Order{" +
                "id=" + id +
                ", amount=" + amount + ", symbol='" + symbol + "'}";
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}