package com.aula.gridpedido.model;

public class ProdutoJson {
    private long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ProdutoJson() {}

    public ProdutoJson(long id, String title, double price, String description, String category, String image, Rating rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rating = rating;
    }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public double getPrice() { return price; }
    public void setPrice(double value) { this.price = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getCategory() { return category; }
    public void setCategory(String value) { this.category = value; }

    public String getImage() { return image; }
    public void setImage(String value) { this.image = value; }

    public Rating getRating() { return rating; }
    public void setRating(Rating value) { this.rating = value; }
}
