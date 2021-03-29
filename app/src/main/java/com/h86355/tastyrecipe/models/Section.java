package com.h86355.tastyrecipe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section {
    @SerializedName("position")
    @Expose
    private int position;

    @SerializedName("components")
    @Expose
    private List<SectionComponent> components;

    public Section(int position, List<SectionComponent> components) {
        this.position = position;
        this.components = components;
    }

    public Section() {
    }

    public int getPosition() {
        return position;
    }

    public List<SectionComponent> getComponents() {
        return components;
    }

    public class SectionComponent {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("raw_text")
        @Expose
        private String raw_text;

        @SerializedName("position")
        @Expose
        private int position;

        public SectionComponent(int id, String raw_text, int position) {
            this.id = id;
            this.raw_text = raw_text;
            this.position = position;
        }

        public SectionComponent() {
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRaw_text() {
            return raw_text;
        }

        public void setRaw_text(String raw_text) {
            this.raw_text = raw_text;
        }
    }
}


