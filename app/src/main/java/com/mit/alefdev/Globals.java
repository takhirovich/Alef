package com.mit.alefdev;

import java.util.ArrayList;

public class Globals {
    private static ArrayList<String> images;
    static final String API_URL = "http://dev-tasks.alef.im/task-m-001/";

    public static void setImg(ArrayList<String> imagesItems) {
        images = imagesItems;
    }
    public static ArrayList<String> getImg() {
        return images;
    }




}
