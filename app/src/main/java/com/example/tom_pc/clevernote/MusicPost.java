package com.example.tom_pc.clevernote;

public class MusicPost {
    private int id;
    private String name;
    private String category;
    private String path;

    public MusicPost(String name, String category, String path, int id) {
        this.name = name;
        this.category = category;
        this.path = path;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
