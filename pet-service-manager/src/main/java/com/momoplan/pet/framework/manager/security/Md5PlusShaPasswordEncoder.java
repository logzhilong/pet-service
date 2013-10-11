package com.momoplan.pet.framework.manager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class Md5PlusShaPasswordEncoder implements PasswordEncoder {
    
    private static Logger logger = LoggerFactory.getLogger(Md5PlusShaPasswordEncoder.class);
    
    private Md5PasswordEncoder md5PwdEncoder;
    private ShaPasswordEncoder shaPwdEncoder;

    public Md5PlusShaPasswordEncoder(Md5PasswordEncoder md5PwdEncoder, ShaPasswordEncoder shaPwdEncoder){
        this.md5PwdEncoder = md5PwdEncoder;
        this.shaPwdEncoder = shaPwdEncoder;
    }
    @Override
    public String encodePassword(String rawPass, Object salt) throws DataAccessException {
        String md5Encoded = md5PwdEncoder.encodePassword(rawPass, salt);
        String res = shaPwdEncoder.encodePassword(md5Encoded, salt);
        logger.debug("encodePassword? " + rawPass + " -> " + res);
        return res;
    }
    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) throws DataAccessException {
        String md5Encoded = md5PwdEncoder.encodePassword(rawPass, salt);
        boolean res = shaPwdEncoder.isPasswordValid(encPass, md5Encoded, salt);
        logger.debug("isPasswordValid? " + rawPass + " -> " + encPass + " : " + res);
        return res;
    }

    public static void main(String[] args) {
        /*
        <beans:bean id="md5PasswordEncoder" class="org.springframework.security.authentication.encoding.Md5PasswordEncoder">
    		<beans:property name="encodeHashAsBase64" value="true" />
    	</beans:bean>
    	<beans:bean id="shaPasswordEncoder" class="org.springframework.security.authentication.encoding.ShaPasswordEncoder">
    		<beans:constructor-arg type="int" value="256" />
    		<beans:property name="encodeHashAsBase64" value="true" />
    	</beans:bean>
    	*/
    	Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    	md5PasswordEncoder.setEncodeHashAsBase64(true);
    	ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
    	shaPasswordEncoder.setEncodeHashAsBase64(true);
    	
    	PasswordEncoder passwordEncoder = new Md5PlusShaPasswordEncoder(md5PasswordEncoder,shaPasswordEncoder);
    	
    	String pwd = "abc123";
    	pwd = passwordEncoder.encodePassword(pwd, null);
    	
    	System.out.println(pwd);
    	System.out.println(passwordEncoder.isPasswordValid(pwd,"123", null));
    	
    }
    
}
