package com.manageyourpassword;

public class Item {
    private int id;
    private String name, username, password, url;

    public Item(int id, String name, String username, String password, String url) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public Item() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
