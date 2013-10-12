package com.momoplan.pet.commons.spring;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import javax.servlet.ServletContext;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 初始化 spring 容器上下文 
 * @author liangc
 */
public class BootstrapContextLoaderListener extends ContextLoaderListener {
	
	@Override
    protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext applicationContext) {
		Bootstrap.setAppContext(applicationContext);
		try {
			String port = findPort();
			Bootstrap.http_port = Integer.parseInt(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * 在war包所在的目录下，逐级向上寻找一个存放端口的配置文件，port.txt
	 * 如果是 tomcat 就把文件放到 webapps 的上级即可，如果是本地测试，就扔到应用的根目录下
	 * 例如 开发测试时可以用 pet-service-manager/port.txt 指明端口 
	 * @return 默认返回 8080
	 * @throws Exception
	 */
	private String findPort() throws Exception{
		URL curl = this.getClass().getClassLoader().getResource("");
		String dir = curl.getFile();
		String[] arr = dir.split("/");
		for(int i=arr.length-1;i>0;i--){
			String lastDir = arr[i];
			int last = dir.lastIndexOf(lastDir);
			String path = dir.substring(0, last)+Bootstrap.PORT_FILE;
			File file = new File(path);
			System.out.println(path);
			if(file.exists()){
				char[] cbuf = new char[128];
				FileReader fr = new FileReader(file);
				fr.read(cbuf);
				String port = new String(cbuf).trim();
				System.out.println("当前容器使用端口 : "+port);
				fr.close();
				return port;
			}
		}
		return "8080";//default
	}
}


