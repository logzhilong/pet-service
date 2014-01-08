package com.momoplan.pet.framework.petservice.bbs.vo;
/**
 * @author liangc
 */
public enum SpecialSubjectState {
	//=============================================
	//== 枚举值 
	//=============================================
	NEW("新增","NEW"),
	WAIT("待推送","WAIT"),
	DELETE("删除","DELETE"),
	PUSHED("已推送","PUSHED");
    private SpecialSubjectState(String name, String code) {
        this.name = name;
        this.code = code; 
    }
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (SpecialSubjectState st : SpecialSubjectState.values()) {  
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
