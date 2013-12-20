package com.momoplan.pet.commons.repository.bbs;
/**
 * 帖子状态
 * @author liangc
 */
public enum NoteState {
	//=============================================
	//== 枚举值 
	//=============================================
	ACTIVE("正常","ACTIVE"),
	AUDIT("审核中","AUDIT"),
	PASS("审核通过","PASS"),
	REJECT("审核拒绝","REJECT"),
	DELETE("删除","DELETE"),
	REPORT("被举报","REPORT"),
	LAZZY("延迟执行","LAZZY");

    private NoteState(String name, String code) {
        this.name = name;  
        this.code = code;  
    }
	
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (NoteState st : NoteState.values()) {  
            if (code.equals(st.getCode())) {
                return st.getName();
            }  
        }
        return null;
    }  

    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
	
}
