package com.h86355.tastyrecipe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeCollection {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnail_url;

    @SerializedName("video_url")
    @Expose
    private String video_url;

    @SerializedName("num_servings")
    @Expose
    private int num_servings;

    @SerializedName("prep_time_minutes")
    @Expose
    private int prep_time_minutes;

    @SerializedName("cook_time_minutes")
    @Expose
    private int cook_time_minutes;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("sections")
    @Expose
    private List<Section> sections;

    @SerializedName("instructions")
    @Expose
    private List<Instruction> instructions;

    @SerializedName("credits")
    @Expose
    private List<Credit> credits;

    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes;

    public RecipeCollection(int id, String name, String thumbnail_url, String video_url, int num_servings, int prep_time_minutes, int cook_time_minutes, String language, List<Section> sections, List<Instruction> instructions, List<Credit> credits, List<Recipe> recipes) {
        this.id = id;
        this.name = name;
        this.thumbnail_url = thumbnail_url;
        this.video_url = video_url;
        this.num_servings = num_servings;
        this.prep_time_minutes = prep_time_minutes;
        this.cook_time_minutes = cook_time_minutes;
        this.language = language;
        this.sections = sections;
        this.instructions = instructions;
        this.credits = credits;
        this.recipes = recipes;
    }

    public RecipeCollection() {
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

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getNum_servings() {
        return num_servings;
    }

    public void setNum_servings(int num_servings) {
        this.num_servings = num_servings;
    }

    public int getPrep_time_minutes() {
        return prep_time_minutes;
    }

    public void setPrep_time_minutes(int prep_time_minutes) {
        this.prep_time_minutes = prep_time_minutes;
    }

    public int getCook_time_minutes() {
        return cook_time_minutes;
    }

    public void setCook_time_minutes(int cook_time_minutes) {
        this.cook_time_minutes = cook_time_minutes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
