package com.jsc.firebase_exercise;

import com.google.firebase.firestore.DocumentId;

public class products {

    @DocumentId
    String documentId;

    String name;



    Boolean isAvailable;
    Long price;

    public products() {
    }

    public products(String name, Boolean isAvailable, Long price) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "products{" +
                "name='" + name + '\'' +
                ", isAvailable=" + isAvailable +
                ", price=" + price +
                '}';
    }
}
