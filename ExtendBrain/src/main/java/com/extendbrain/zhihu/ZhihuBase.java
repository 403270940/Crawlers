package com.extendbrain.zhihu;

import java.util.List;

public interface ZhihuBase {
	//return zhihu object
	public abstract boolean login(String userName,String password) throws Exception;
	
	public abstract List<Object> getQuestionsByTag(String tag);
	
	public abstract List<Answer> getAllAnswerOfQuestion(int questionId);
	
	public abstract boolean upAnswer(int questionId,int answerId);
	
	public abstract boolean downAnswer(int questionId,int answerId);
	
	public abstract boolean commentAnswer(int questionId,int answerId,String comment);
	
	//return answer comments 
	public abstract List<Object> getAnswerComments(int questionId,int answerId);
	
	public abstract Object getUserInfoById(String userId);
	
	
	
}
