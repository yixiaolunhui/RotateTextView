package com.dalong.rotatetextview;

/**
 * Created by zhouweilong on 16/6/1.
 */

public class RotateEntity {

    public String name;
    public int rotate;

    public RotateEntity() {
    }
    public RotateEntity(String name, int rotate) {
        this.name = name;
        this.rotate = rotate;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }
}
