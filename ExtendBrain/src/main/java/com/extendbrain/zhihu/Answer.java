package com.extendbrain.zhihu;

public class Answer {
	private int upCount = 0;
	private int comentCount = 0;
	private String answerText = "";
	private String answerTime = "";
	private String userName = "";
	private String userInfo = "";
	private String userURL = "";
	public Answer(){
		
	}
	
	public Answer(int upCount, int comentCount, String answerText, String anserTime) {
		super();
		this.upCount = upCount;
		this.comentCount = comentCount;
		this.answerText = answerText;
		this.answerTime = answerTime;
	}

	public int getUpCount() {
		return upCount;
	}

	public void setUpCount(int upCount) {
		this.upCount = upCount;
	}

	public int getComentCount() {
		return comentCount;
	}

	public void setComentCount(int comentCount) {
		this.comentCount = comentCount;
	}



	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}



	public String getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getUserURL() {
		return userURL;
	}

	public void setUserURL(String userURL) {
		this.userURL = userURL;
	}

	@Override
	public String toString() {
		return "Answer [upCount=" + upCount + ", comentCount=" + comentCount
				+ ", answerText=" + answerText + ", answerTime=" + answerTime
				+ ", userName=" + userName + ", userInfo=" + userInfo
				+ ", userURL=" + userURL + "]";
	}


	
	
	
}
