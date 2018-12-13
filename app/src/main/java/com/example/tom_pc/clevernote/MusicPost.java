package com.example.tom_pc.clevernote;

public class MusicPost {
    private int id;
    private String name;
    private String category;

    public MusicPost(String name, String category,int id) {
        this.name = name;
        this.category = category;
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
