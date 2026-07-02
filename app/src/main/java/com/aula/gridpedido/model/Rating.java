package com.aula.gridpedido.model;

public class Rating {
    private double rate;
    private long count;

    public Rating() {}
    public Rating(double rate, long count) {
        this.rate = rate;
        this.count = count;
    }
    public double getRate() { return rate; }
    public void setRate(double value) { this.rate = value; }

    public long getCount() { return count; }
    public void setCount(long value) { this.count = value; }
}

