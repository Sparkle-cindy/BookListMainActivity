package com.jnu.booklistmainactivity.data;

import java.io.Serializable;

public class Book implements Serializable {

    private  String name;
    private  int bookId;

    public Book(String name, int bookId) {
        this.name=name;
        this.bookId=bookId;
    }

    public String getTitle(){
        return name;
    }

    public int getCoverResourceId(){
        return bookId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
