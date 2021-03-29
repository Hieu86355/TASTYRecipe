package com.h86355.tastyrecipe.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchTags {
    private static List<String> tags;

    public static List<String> getTags() {
        tags = new ArrayList<>();
        tags.add("mexican"); tags.add("vegan"); tags.add("american"); tags.add("british"); tags.add("drinks");
        tags.add("happy_hour"); tags.add("under_30_minutes"); tags.add("healthy"); tags.add("breakfast");
        tags.add("lunch"); tags.add("dinner"); tags.add("snacks"); tags.add("bbq"); tags.add("seafood");
        tags.add("vietnamese"); tags.add("gluten_free"); tags.add("low_carb"); tags.add("thai"); tags.add("french");
        tags.add("desserts");
        Collections.shuffle(tags);
        return tags;
    }
}
