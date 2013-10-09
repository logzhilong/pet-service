package com.momoplan.pet.commons.spring;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.zookeeper.KeeperException;
import org.springframework.context.i18n.LocaleContext;

import com.momoplan.pet.commons.zoo.config.Config;
import com.momoplan.pet.commons.zoo.config.ConfigWatcher;

public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected LocaleContext buildLocaleContext(HttpServletRequest request) {
		/**
		 * 初始 zoo 配置
		 */
		if(Bootstrap.http_port<=0){
			String zooServer = (String) Bootstrap.getBean("zooServer");
			Bootstrap.http_port = request.getLocalPort();
			try {
				System.out.println("初始化 ctx="+request.getContextPath()+" port="+Bootstrap.http_port+" zooServer="+zooServer);
				Bootstrap.configWatcher = new ConfigWatcher(zooServer);
				System.err.println("public : "+Config.publicConfig);
				System.err.println("private : "+Config.privateConfig);
				System.err.println("privateReg : "+Config.privateConfigReg);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (KeeperException e) {
				e.printStackTrace();
			}
		}
		return super.buildLocaleContext(request);
	}
	
}
