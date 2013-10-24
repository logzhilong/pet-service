package com.momoplan.pet.framework.user.enums;
/**
 * 性别枚举
 * @author liangc
 */
public enum GenderType {
	
	//=============================================
	//== 枚举值 
	//=============================================
	MALE("男性","male"),
	FEMALE("女性","female");

    private GenderType(String name, String code) {  
        this.name = name;  
        this.code = code;  
    }
	
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (GenderType st : GenderType.values()) {  
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
