package com.momoplan.pet.framework.bbs.vo;
/**
 * 获取列表的动作
 * @author liangc
 */
public enum ConditionType {
	//=============================================
	//== 枚举值 
	//=============================================
	NOTE_NAME("帖子名称查找，默认","NOTE_NAME"),
	I_CREATE("我发过的帖子","I_CREATE"),
	I_REPLY("某人参与过回复的帖子","I_REPLY");
    private ConditionType(String name, String code) {
        this.name = name;  
        this.code = code;  
    }
	
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (ConditionType st : ConditionType.values()) {  
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
