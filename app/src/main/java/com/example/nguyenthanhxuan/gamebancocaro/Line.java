package com.example.nguyenthanhxuan.gamebancocaro;

/**
 * Created by Nguyen Thanh Xuan on 3/21/2018.
 */

public class Line {
    private Point startP, endP;

    public Line(Point startP, Point endP) {
        this.startP = startP;
        this.endP = endP;
    }

    public Point getStartP() {
        return startP;
    }

    public void setStartP(Point startP) {
        this.startP = startP;
    }

    public Point getEndP() {
        return endP;
    }

    public void setEndP(Point endP) {
        this.endP = endP;
    }
}
