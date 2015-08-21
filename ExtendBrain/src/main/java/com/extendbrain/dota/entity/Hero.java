package com.extendbrain.dota.entity;

public class Hero {
	private String name;
	private int id;
	private String localized_name;
	public Hero(){}
	public Hero(int id,String name,String localized_name){
		this.id = id;
		this.name = name;
		this.localized_name = localized_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocalized_name() {
		return localized_name;
	}
	public void setLocalized_name(String localized_name) {
		this.localized_name = localized_name;
	}
	@Override
	public String toString() {
		return "Hero [name=" + name + ", id=" + id + ", localized_name="
				+ localized_name + "]";
	}
	
	
}
