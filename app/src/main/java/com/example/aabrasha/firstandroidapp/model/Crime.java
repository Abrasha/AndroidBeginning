package com.example.aabrasha.firstandroidapp.model;

import java.util.UUID;

/**
 * Created by Andrii Abramov on 6/30/16.
 */
public class Crime {
    private UUID id;
    private String title;

    public Crime(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }

    public Crime() {
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
}
