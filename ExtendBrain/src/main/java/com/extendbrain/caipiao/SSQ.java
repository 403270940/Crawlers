package com.extendbrain.caipiao;

public class SSQ {
	private int edition;
	private String openTime;
	private int red1;
	private int red2;
	private int red3;
	private int red4;
	private int red5;
	private int red6;
	private int blue;
	private int money;
	public SSQ(int edition, String openTime, int red1, int red2, int red3,
			int red4, int red5, int red6, int blue, int money) {
		super();
		this.edition = edition;
		this.openTime = openTime;
		this.red1 = red1;
		this.red2 = red2;
		this.red3 = red3;
		this.red4 = red4;
		this.red5 = red5;
		this.red6 = red6;
		this.blue = blue;
		this.money = money;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public int getRed1() {
		return red1;
	}
	public void setRed1(int red1) {
		this.red1 = red1;
	}
	public int getRed2() {
		return red2;
	}
	public void setRed2(int red2) {
		this.red2 = red2;
	}
	public int getRed3() {
		return red3;
	}
	public void setRed3(int red3) {
		this.red3 = red3;
	}
	public int getRed4() {
		return red4;
	}
	public void setRed4(int red4) {
		this.red4 = red4;
	}
	public int getRed5() {
		return red5;
	}
	public void setRed5(int red5) {
		this.red5 = red5;
	}
	public int getRed6() {
		return red6;
	}
	public void setRed6(int red6) {
		this.red6 = red6;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "SSQ [edition=" + edition + ", openTime=" + openTime + ", red1="
				+ red1 + ", red2=" + red2 + ", red3=" + red3 + ", red4=" + red4
				+ ", red5=" + red5 + ", red6=" + red6 + ", blue=" + blue
				+ ", money=" + money + "]";
	}
	
	
	
	
	
}
