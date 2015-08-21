package com.extendbrain.dota.entity;

import java.util.ArrayList;
import java.util.List;

public class MatchDetail {
	
	private List<MatchDetailPlayer> players = new ArrayList<MatchDetailPlayer>();
	private boolean radiant_win;
	private int duration;
	private int start_time;
	private int match_id;
	private int match_seq_num;
	private int tower_status_radiant;
	private int tower_status_dire;
	private int barracks_status_radiant;
	private int barracks_status_dire;
	private int cluster;
	private int first_blood_time;
	private int lobby_type;
	private int human_player;
	private int leagueid;
	private int positive_votes;
	private int negative_votes;
	private int game_mode;
	private int engine;
	public List<MatchDetailPlayer> getPlayers() {
		return players;
	}
	public void addPlayers(MatchDetailPlayer player){
		players.add(player);
	}
	public void setPlayers(List<MatchDetailPlayer> players) {
		this.players = players;
	}
	public boolean isRadiant_win() {
		return radiant_win;
	}
	public void setRadiant_win(boolean radiant_win) {
		this.radiant_win = radiant_win;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getStart_time() {
		return start_time;
	}
	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}
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
	public int getTower_status_radiant() {
		return tower_status_radiant;
	}
	public void setTower_status_radiant(int tower_status_radiant) {
		this.tower_status_radiant = tower_status_radiant;
	}
	public int getTower_status_dire() {
		return tower_status_dire;
	}
	public void setTower_status_dire(int tower_status_dire) {
		this.tower_status_dire = tower_status_dire;
	}
	public int getBarracks_status_radiant() {
		return barracks_status_radiant;
	}
	public void setBarracks_status_radiant(int barracks_status_radiant) {
		this.barracks_status_radiant = barracks_status_radiant;
	}
	public int getBarracks_status_dire() {
		return barracks_status_dire;
	}
	public void setBarracks_status_dire(int barracks_status_dire) {
		this.barracks_status_dire = barracks_status_dire;
	}
	public int getCluster() {
		return cluster;
	}
	public void setCluster(int cluster) {
		this.cluster = cluster;
	}
	public int getFirst_blood_time() {
		return first_blood_time;
	}
	public void setFirst_blood_time(int first_blood_time) {
		this.first_blood_time = first_blood_time;
	}
	public int getLobby_type() {
		return lobby_type;
	}
	public void setLobby_type(int lobby_type) {
		this.lobby_type = lobby_type;
	}
	public int getHuman_player() {
		return human_player;
	}
	public void setHuman_player(int human_player) {
		this.human_player = human_player;
	}
	public int getLeagueid() {
		return leagueid;
	}
	public void setLeagueid(int leagueid) {
		this.leagueid = leagueid;
	}
	public int getPositive_votes() {
		return positive_votes;
	}
	public void setPositive_votes(int positive_votes) {
		this.positive_votes = positive_votes;
	}
	public int getNegative_votes() {
		return negative_votes;
	}
	public void setNegative_votes(int negative_votes) {
		this.negative_votes = negative_votes;
	}
	public int getGame_mode() {
		return game_mode;
	}
	public void setGame_mode(int game_mode) {
		this.game_mode = game_mode;
	}
	public int getEngine() {
		return engine;
	}
	public void setEngine(int engine) {
		this.engine = engine;
	}
	@Override
	public String toString() {
		return "MatchDetail [players=" + players + ", radiant_win="
				+ radiant_win + ", duration=" + duration + ", start_time="
				+ start_time + ", match_id=" + match_id + ", match_seq_num="
				+ match_seq_num + ", tower_status_radiant="
				+ tower_status_radiant + ", tower_status_dire="
				+ tower_status_dire + ", barracks_status_radiant="
				+ barracks_status_radiant + ", barracks_status_dire="
				+ barracks_status_dire + ", cluster=" + cluster
				+ ", first_blood_time=" + first_blood_time + ", lobby_type="
				+ lobby_type + ", human_player=" + human_player + ", leagueid="
				+ leagueid + ", positive_votes=" + positive_votes
				+ ", negative_votes=" + negative_votes + ", game_mode="
				+ game_mode + ", engine=" + engine + "]";
	}
	
	
}
