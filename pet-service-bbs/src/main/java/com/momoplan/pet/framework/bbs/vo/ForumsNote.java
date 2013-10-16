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
		// 今日新增帖子数
		private String sTodayNewNoteCount;
		// 所有帖子数
		private String sAllnoteCount;
		private String isAtt;
		
		
		// 所有回复数
		private String sAllnoteRelCount;
		
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
		public String getsTodayNewNoteCount() {
			return sTodayNewNoteCount;
		}
		public void setsTodayNewNoteCount(String sTodayNewNoteCount) {
			this.sTodayNewNoteCount = sTodayNewNoteCount;
		}
		public String getsAllnoteCount() {
			return sAllnoteCount;
		}
		public void setsAllnoteCount(String sAllnoteCount) {
			this.sAllnoteCount = sAllnoteCount;
		}
		public String getsAllnoteRelCount() {
			return sAllnoteRelCount;
		}
		public void setsAllnoteRelCount(String sAllnoteRelCount) {
			this.sAllnoteRelCount = sAllnoteRelCount;
		}

}
