package com.fast.dev.component.data.sign.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

import com.fast.dev.component.data.sign.constant.StringConstant;
import com.fast.dev.core.util.bytes.BytesUtil;

public class CacheRequestWapper extends HttpServletRequestWrapper {

	private final byte[] body;

	private Map<String, String[]> cacheMap = null;

	private final static byte[] splitBin = "&".getBytes();

	public CacheRequestWapper(HttpServletRequest request) throws IOException {
		super(request);
		String query = request.getQueryString() == null ? "" : request.getQueryString();
		byte[] postBin = StreamUtils.copyToByteArray(request.getInputStream());
		byte[] queryBin = query.getBytes();
		body = BytesUtil.merge(postBin, splitBin, queryBin);
		initCacheMap();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	/**
	 * 初始化缓存map
	 */
	private void initCacheMap() {
		try {
			// 数据
			String content = new String(body, StringConstant.DefaultCharset.name());
			// 数据转换为字典
			cacheMap = ParameterMapHelper.toParameterMap(content, new ParameterMapDecode() {
				@Override
				public String value(String value) {
					try {
						return URLDecoder.decode(value, StringConstant.DefaultCharset.name());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				public String name(String name) {
					try {
						return URLDecoder.decode(name, StringConstant.DefaultCharset.name());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);

		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// TODO Auto-generated method stub

			}
		};
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return this.cacheMap;
	}

	@Override
	public String getParameter(String name) {
		String[] values = this.cacheMap.get(name);
		if (values != null && values.length > 0) {
			return values[0];
		}
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		return this.cacheMap.get(name);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(this.cacheMap.keySet());
	}

	/**
	 * 获取原始数据
	 * 
	 * @return
	 */
	public byte[] getContentAsByteArray() {
		return body;
	}

}