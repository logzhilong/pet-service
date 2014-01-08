package com.momoplan.pet.framework.petservice.bbs.vo;
/**
 * @author liangc
 */
public enum SpecialSubjectType {
	//=============================================
	//== 枚举值 
	//=============================================
	F("第一个","F"),
	N("第N个","N");
    private SpecialSubjectType(String name, String code) {
        this.name = name;  
        this.code = code;  
    }
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (SpecialSubjectType st : SpecialSubjectType.values()) {  
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
