package com.example.tom_pc.clevernote;

public class NewsPost {
    private int id;
    private String title;
    private String description;
    private String url;

    public NewsPost() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NewsPost(String title, String description, String url, int id) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
