package com.bajie.project.server.config;

import javax.servlet.annotation.WebFilter;

import org.springframework.web.filter.CharacterEncodingFilter;

@WebFilter(urlPatterns = { "/*" }, asyncSupported = true)
public class EncodingFilter extends CharacterEncodingFilter {

	public EncodingFilter() {
		setForceEncoding(true);
		setEncoding("UTF-8");
	}

}
