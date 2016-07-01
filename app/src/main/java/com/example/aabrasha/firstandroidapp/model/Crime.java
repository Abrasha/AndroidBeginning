package com.example.aabrasha.firstandroidapp.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Andrii Abramov on 6/30/16.
 */
public class Crime {
    private UUID id;
    private String title;
    private Date date;
    private boolean isSolved;

    public Crime() {
        this.id = UUID.randomUUID();
        date = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
