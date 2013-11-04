package com.momoplan.pet.framework.bbs.vo;
/**
 * 获取列表的动作
 * @author liangc
 */
public enum Action {
	//"action":"ALL 全部；EUTE 精华；NEW_ET 最新回复；NEW_CT 最新发布；SEARCH 查询"
	//=============================================
	//== 枚举值 
	//=============================================
	ALL("全部","ALL"),
	EUTE("精华","EUTE"),
	NEW_ET("最新回复","NEW_ET"),
	NEW_CT("最新发布","NEW_CT"),
	SEARCH("查询","SEARCH");

    private Action(String name, String code) {
        this.name = name;  
        this.code = code;  
    }
	
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (Action st : Action.values()) {  
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
