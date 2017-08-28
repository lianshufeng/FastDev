package com.bajie.project.server.wrapper;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {

	private Map<String, String[]> parameterMap;

	public RequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public RequestWrapper(HttpServletRequest request, Map<String, String[]> parameterMap) {
		super(request);
		this.parameterMap = parameterMap;
	}

	@Override
	public String getParameter(String name) {
		String[] value = this.parameterMap.get(name);
		return value == null || value.length < 1 ? null : value[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return this.parameterMap;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Set<String> ketSet = this.parameterMap.keySet();
		final Iterator<String> keyI = ketSet.iterator();
		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {
				return keyI.hasNext();
			}

			@Override
			public String nextElement() {
				return keyI.next();
			}
		};
	}

	@Override
	public String[] getParameterValues(String name) {
		return this.parameterMap.get(name);
	}

}
