package com.mit.alefdev;

import java.util.ArrayList;

public class Globals {
    private static ArrayList<String> images;

    public static void setImg(ArrayList<String> imagesItems) {
        images = imagesItems;
    }
    public static ArrayList<String> getImg() {
        return images;
    }




}
