package com.gcash.parcel.model;

public class ParcelResponse {
    private double cost;
    private double discount;
    private double totalCost;
    private String message;

    public ParcelResponse(double cost, double discount, double totalCost, String message) {
        this.cost = cost;
        this.discount = discount;
        this.totalCost = totalCost;
        this.message = message;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
