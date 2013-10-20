package com.momoplan.pet.framework.bbs.vo;

public class ForumsNote {
		//父级圈id
		private String  fFid;
		//父级圈子name
		private String  fForumName;
		//父级圈子描述
		private String 	fDescript;
		//父级圈子img
		private String	fLogoimg;
		
		//圈子id
		private String  sid; 
		//圈子name
		private String  sForumName;
		//圈子描述
		private String 	sDescript;
		//圈子图片
		private String	sLogoimg;

		private String isAtt;

		//圈子当天总帖数
		private Long totalToday = 0L;
		//圈子的总帖子数
		private Long totalCount = 0L;
		//圈子的总回复数
		private Long totalReply = 0L;		
		
		public String getfFid() {
			return fFid;
		}
		public void setfFid(String fFid) {
			this.fFid = fFid;
		}
		public String getfForumName() {
			return fForumName;
		}
		public void setfForumName(String fForumName) {
			this.fForumName = fForumName;
		}
		public String getfDescript() {
			return fDescript;
		}
		public void setfDescript(String fDescript) {
			this.fDescript = fDescript;
		}
		public String getfLogoimg() {
			return fLogoimg;
		}
		public void setfLogoimg(String fLogoimg) {
			this.fLogoimg = fLogoimg;
		}
		public String getIsAtt() {
			return isAtt;
		}
		public void setIsAtt(String isAtt) {
			this.isAtt = isAtt;
		}
		public String getSid() {
			return sid;
		}
		public void setSid(String sid) {
			this.sid = sid;
		}
		public String getsForumName() {
			return sForumName;
		}
		public void setsForumName(String sForumName) {
			this.sForumName = sForumName;
		}
		public String getsDescript() {
			return sDescript;
		}
		public void setsDescript(String sDescript) {
			this.sDescript = sDescript;
		}
		public String getsLogoimg() {
			return sLogoimg;
		}
		public void setsLogoimg(String sLogoimg) {
			this.sLogoimg = sLogoimg;
		}
		public Long getTotalToday() {
			return totalToday;
		}
		public void setTotalToday(Long totalToday) {
			this.totalToday = totalToday;
		}
		public Long getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(Long totalCount) {
			this.totalCount = totalCount;
		}
		public Long getTotalReply() {
			return totalReply;
		}
		public void setTotalReply(Long totalReply) {
			this.totalReply = totalReply;
		}

}
