package com.h86355.tastyrecipe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credit {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    public Credit(int id, String name, String type, String slug, String image_url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.slug = slug;
        this.image_url = image_url;
    }

    public Credit() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
