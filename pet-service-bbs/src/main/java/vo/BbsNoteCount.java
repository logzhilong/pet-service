package vo;

public class BbsNoteCount {
	private String id;
	//图片
	private String imgId;
	// 名字
	private String name;
	// 是否关注
	private String isattention;
	// 今日新增帖子数
	private String todayNewNoteCount;
	// 所有帖子数
	private String noteCount;
	// 所有回复数
	private String noteRelCount;

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTodayNewNoteCount() {
		return todayNewNoteCount;
	}

	public void setTodayNewNoteCount(String todayNewNoteCount) {
		this.todayNewNoteCount = todayNewNoteCount;
	}

	public String getNoteCount() {
		return noteCount;
	}

	public void setNoteCount(String noteCount) {
		this.noteCount = noteCount;
	}

	public String getNoteRelCount() {
		return noteRelCount;
	}

	public void setNoteRelCount(String noteRelCount) {
		this.noteRelCount = noteRelCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsattention() {
		return isattention;
	}

	public void setIsattention(String isattention) {
		this.isattention = isattention;
	}

}
