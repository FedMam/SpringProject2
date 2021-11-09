package com.fedormamaevv.SpringProject1;

import java.util.Date;

public class Comment {
    public String text;
    public String username;
    public Date created;
    public Date updated;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Comment() { }

    public Comment(String text, String username) {
        this.text = text;
        this.username = username;
    }
}
