package com.extendbrain.dota.entity;

import java.util.ArrayList;
import java.util.List;

import javassist.expr.Instanceof;

public class Match {
	private int match_id;
	private int match_seq_num;
	private int start_time;
	private int lobby_type;
	private int radiant_team_id;
	private int dire_team_id;
	private List<MatchPlayer> players = new ArrayList<MatchPlayer>();
	public int getMatch_id() {
		return match_id;
	}
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}
	public int getMatch_seq_num() {
		return match_seq_num;
	}
	public void setMatch_seq_num(int match_seq_num) {
		this.match_seq_num = match_seq_num;
	}
	public int getStart_time() {
		return start_time;
	}
	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}
	public int getLobby_type() {
		return lobby_type;
	}
	public void setLobby_type(int lobby_type) {
		this.lobby_type = lobby_type;
	}
	public int getRadiant_team_id() {
		return radiant_team_id;
	}
	public void setRadiant_team_id(int radiant_team_id) {
		this.radiant_team_id = radiant_team_id;
	}
	public int getDire_team_id() {
		return dire_team_id;
	}
	public void setDire_team_id(int dire_team_id) {
		this.dire_team_id = dire_team_id;
	}
	public List<MatchPlayer> getPlayers() {
		return players;
	}
	public void addPlayer(MatchPlayer player) {
		players.add(player);
	}
	@Override
	public String toString() {
		return "Match [match_id=" + match_id + ", match_seq_num="
				+ match_seq_num + ", start_time=" + start_time
				+ ", lobby_type=" + lobby_type + ", radiant_team_id="
				+ radiant_team_id + ", dire_team_id=" + dire_team_id
				+ ", players=" + players + "]";
		
	}

@Override
public int hashCode() {
	// TODO Auto-generated method stub
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + match_id;
	  result = prime * result + start_time;
	  return result;
}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Match){
			Match dest = (Match)obj;
			if(dest.getMatch_id() == match_id)
				return true;
			else return false;
		}else{
			return false;
		}

	}

}
