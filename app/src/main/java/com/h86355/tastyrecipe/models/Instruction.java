package com.h86355.tastyrecipe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instruction {
    @SerializedName("display_text")
    @Expose
    private String display_text;

    @SerializedName("position")
    @Expose
    private int position;

    public Instruction() {
    }

    public Instruction(String display_text, int position) {
        this.display_text = display_text;
        this.position = position;
    }

    public String getDisplay_text() {
        return display_text;
    }

    public void setDisplay_text(String display_text) {
        this.display_text = display_text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
