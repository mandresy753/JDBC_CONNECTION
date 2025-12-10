package Exo;

import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private double price;
    private Instant creationDatetime;
    private Category category;

    public Product(int id, String name, Instant creationDatetime, Category category) {
        this.id = id;
        this.name = name;
        this.creationDatetime = creationDatetime;
        this.category = category;
    }

    public String getCategoryName() {
        if (category == null) {
            return null;
        }
        return category.getName();
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {return this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDatetime() {
        return creationDatetime;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", creationDateTime=" + creationDatetime +
                ", category=" + (category != null ? category.getName() : "null") +
                '}';
    }

}
