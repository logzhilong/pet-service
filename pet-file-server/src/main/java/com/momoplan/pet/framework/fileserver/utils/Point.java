package com.momoplan.pet.framework.fileserver.utils;

public class Point {

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(double x, double y,double w,double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public double x, y, w, h;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
