package com.momoplan.pet.framework.petservice.timer.vo;
/**
 * @author liangc
 */
public enum TimerState {
	//=============================================
	//== 枚举值 
	//=============================================
	NEW("新增","NEW"),
	FINISHED("完成","FINISHED"),
	CANCEL("取消","CANCEL");
	
    private TimerState(String name, String code) {
        this.name = name;  
        this.code = code;  
    }
	//=============================================
	//== 固定写法 
	//=============================================
    private String name;
    private String code;
    
    public static String getName(String code) {  
        for (TimerState st : TimerState.values()) {  
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
