package com.momoplan.pet.framework.servicestate.vo;

import java.util.List;


public class StateResponse {
	private StateView stateView;
	private ReplyView replyView;
	private List<StateView> stateViews;
	private List<ReplyView> ReplyViews;
	
	public List<StateView> getStateViews() {
		return stateViews;
	}
	public void setStateViews(List<StateView> stateViews) {
		this.stateViews = stateViews;
	}
	public List<ReplyView> getReplyViews() {
		return ReplyViews;
	}
	public void setReplyViews(List<ReplyView> replyViews) {
		ReplyViews = replyViews;
	}
	public StateView getStateView() {
		return stateView;
	}
	public void setStateView(StateView stateView) {
		this.stateView = stateView;
	}
	public ReplyView getReplyView() {
		return replyView;
	}
	public void setReplyView(ReplyView replyView) {
		this.replyView = replyView;
	}
}
