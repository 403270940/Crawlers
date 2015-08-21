package com.extendbrain.dota.entity;

import com.extendbrain.dota.HeroesUtil;

public class MatchPlayer {
	private int account_id;
	private int player_slot;
	private int hero_id;
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getPlayer_slot() {
		return player_slot;
	}
	public void setPlayer_slot(int player_slot) {
		this.player_slot = player_slot;
	}
	public int getHero_id() {
		return hero_id;
	}
	public void setHero_id(int hero_id) {
		this.hero_id = hero_id;
	}
	@Override
	public String toString() {
		return "MatchPlayer [account_id=" + account_id + ", player_slot="
				+ player_slot + ", hero_id=" + hero_id+ ", hero_name=" + HeroesUtil.getName(hero_id)  + "]";
	}
	
	
//		"account_id": 134965269,
//		"player_slot": 0,
//		"hero_id": 25
//	}
}
