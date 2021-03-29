package com.h86355.tastyrecipe.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {
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



    public Recipe(int id, String name, String thumbnail_url, String video_url,
                  int num_servings, int prep_time_minutes, int cook_time_minutes,
                  String language, List<Section> sections, List<Instruction> instructions,
                  List<Credit> credits) {
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
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        thumbnail_url = in.readString();
        video_url = in.readString();
        num_servings = in.readInt();
        prep_time_minutes = in.readInt();
        cook_time_minutes = in.readInt();
        language = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public int getNum_servings() {
        return num_servings;
    }

    public int getPrep_time_minutes() {
        return prep_time_minutes;
    }

    public int getCook_time_minutes() {
        return cook_time_minutes;
    }

    public String getLanguage() {
        return language;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public List<Credit> getCredits() {
        return credits;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setNum_servings(int num_servings) {
        this.num_servings = num_servings;
    }

    public void setPrep_time_minutes(int prep_time_minutes) {
        this.prep_time_minutes = prep_time_minutes;
    }

    public void setCook_time_minutes(int cook_time_minutes) {
        this.cook_time_minutes = cook_time_minutes;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(thumbnail_url);
        dest.writeString(video_url);
        dest.writeInt(num_servings);
        dest.writeInt(prep_time_minutes);
        dest.writeInt(cook_time_minutes);
        dest.writeString(language);
    }
}
