package com.extendbrain.dota.entity;

public class Ability_upgrade {
	private int ability;
	private int time;
	private int level;
	
	public Ability_upgrade(int ability, int time, int level) {
		this.ability = ability;
		this.time = time;
		this.level = level;
	}
	
	public int getAbility() {
		return ability;
	}
	public void setAbility(int ability) {
		this.ability = ability;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "ability_upgrades [ability=" + ability + ", time=" + time
				+ ", level=" + level + "]";
	}
	
	
	
}
