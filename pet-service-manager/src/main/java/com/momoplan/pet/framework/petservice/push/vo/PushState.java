package com.momoplan.pet.framework.petservice.push.vo;
/**
 * @author liangc
 */
public enum PushState {
	//=============================================
	//== 枚举值 
	//=============================================
	NEW("未发送","NEW"),
	PUSHED("已经发送","PUSHED"),
	PENDING("发送中","PENDING");
    private PushState(String name, String code) {
        this.name = name;  
        this.code = code;  
    }
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (PushState st : PushState.values()) {  
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
