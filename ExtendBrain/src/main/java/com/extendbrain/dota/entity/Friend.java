package com.extendbrain.dota.entity;

import com.gargoylesoftware.htmlunit.javascript.host.Map;

public class Friend {
	private String steamId;
	private String name;
	/*
	 * 
    The user's current status. 
    0 - Offline, 1 - Online, 
    2 - Busy, 3 - Away, 
    4 - Snooze, 5 - looking to trade, 
    6 - looking to play. 
    If the player's profile is private, 
    this will always be "0", 
    except is the user has set his status to looking 
    to trade or looking to play, because a bug makes 
    those status appear even if the profile is private.
	 */
	private String status;

	private String lastLogOff;
	
	
	
	public Friend(String steamId, String name, int statusCode,
			String lastLogOff) {
		super();
		this.steamId = steamId;
		this.name = name;
		setStatus(statusCode);
		this.lastLogOff = lastLogOff;
	}
	public String getSteamId() {
		return steamId;
	}
	public void setSteamId(String steamId) {
		this.steamId = steamId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(int code) {
		switch (code) {
		case 0:
			status = "Offline";
			break;
		case 1:
			status = "Online";
			break;
		case 2:
			status = "Busy";
			break;
		case 3:
			status = "Away";
			break;
		case 4:
			status = "Snooze";
			break;
		case 5:
			status = "Looking to trade";
			break;
		case 6:
			status = "Looking to play";
			break;
		default:
			break;
		}
	}

	public String getLastLogOff() {
		return lastLogOff;
	}
	public void setLastLogOff(String lastLogOff) {
		this.lastLogOff = lastLogOff;
	}
	@Override
	public String toString() {
		return "Friend [steamId=" + steamId + ", name=" + name + ", status="
				+ status + ", lastLogOff=" + lastLogOff + "]";
	}
	

	
}
