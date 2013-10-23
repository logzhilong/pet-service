package com.momoplan.pet.framework.user.enums;
/**
 * 好友关系表用到此枚举
 * @author liangc
 */
public enum SubscriptionType {
	
	//=============================================
	//== 枚举值 
	//=============================================
	SUB("好友","0");

    private SubscriptionType(String name, String code) {  
        this.name = name;  
        this.code = code;  
    }
	
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (SubscriptionType st : SubscriptionType.values()) {  
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
