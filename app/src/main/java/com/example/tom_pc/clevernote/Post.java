package com.example.tom_pc.clevernote;

public class Post {
    private int id;
    private String name;
    private String post;
    private byte[] image;

    public Post(int id, String name, String post, byte[] image) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
