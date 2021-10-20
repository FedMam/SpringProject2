package com.fedormamaevv.SpringProject1;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    public String name;
    public List<Comment> comments = new ArrayList<>();

    public Topic(String name) {
        this.name = name;
    }

    public Topic(String name, List<Comment> commentList) {
        this.name = name;
        this.comments = commentList;
    }

    public Topic() { }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public Comment getComment(Integer index) { return comments.get(index); }
    public void addComment(Comment comment) { comments.add(comment); }
    public void updateComment(Integer index, Comment comment) { comments.set(index, comment); }
    public void deleteComment(Integer index) { comments.remove((int) index); }
}
