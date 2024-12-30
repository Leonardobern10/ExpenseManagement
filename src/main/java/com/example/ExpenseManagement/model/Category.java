package com.example.ExpenseManagement.model;

public class Category {
    private String name;
    private Double limit;
    private Double totalValue;

    public Category (String name, Double totalValue) {
        this.name = name;
        this.limit = null;
        this.totalValue = totalValue;
    }

    public Category (){};

    public Category (String name) {
        this.name = name;
        this.limit = null;
        this.totalValue = null;
    }

    public Category (String name, Double totalValue, Double limit) {
        this.name = name;
        this.limit = limit;
        this.totalValue = totalValue;
    }

    public Double getTotalValue () {
        return totalValue;
    }

    public void setTotalValue (Double value) {
        totalValue = value;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Double getLimit () {
        return limit;
    }

    public void setLimit (Double limit) {
        this.limit = limit;
    }
}
