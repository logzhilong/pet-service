package com.momoplan.pet.commons.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.i18n.LocaleContext;

/**
 * 扩展一下 spring 的 DispatcherServlet
 * @author liangc
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected LocaleContext buildLocaleContext(HttpServletRequest request) {
		return super.buildLocaleContext(request);
	}
}
