package com.extendbrain.beans;

public class RentItem {
	private String imgURL;//图片地址
	private String destURL;//目标链接
	private String anchor;//链接文本
	private String area;//区
	private String village;//小区名称
	private String time;//发布时间
	private int charge;
	private String room;
	public RentItem(){
	}
	
	public RentItem(String imgURL,String destURL,String anchor,String area,
			String village,String time,int charge,String room){
		this.imgURL = imgURL;
		this.destURL = destURL;
		this.anchor = anchor;
		this.area = area;
		this.village = village;
		this.time = time;
		this.charge = charge;
		this.room = room;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "";
		str += "锚文本:" + anchor + "\n";
		str += "图片地址:" + imgURL + "\n";
		str += "链接地址:" + destURL + "\n";
		str += "区名:" + area + "\n";
		str += "小区:" + village + "\n";
		str += "发布时间:" + time + "\n";
		str += "价格:" + charge + "\n";
		str += "房间类型:" + room + "\n";
		return str;
	}
	
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getDestURL() {
		return destURL;
	}
	public void setDestURL(String destURL) {
		this.destURL = destURL;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getCharge(){
		return charge;
	}
	public void setCharge(int charge){
		this.charge = charge;
	}
	public String getRoom(){
		return room;
	}
	public void setRoom(String room){
		this.room = room;
	}
}
