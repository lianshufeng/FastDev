package com.fast.dev.component.webservice.impl.client;

import java.io.IOException;
import java.net.URL;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianURLConnectionFactory;
import com.fast.dev.component.webservice.impl.util.HessianSecurityUtil;

/**
 * 客户端的安全类的实现
 * 
 * @author 练书锋
 *
 */
public class HessianURLConnectionFactorySecurity extends HessianURLConnectionFactory {

	private String aToken;
	private String sToken;

	public String getaToken() {
		return aToken;
	}

	public void setaToken(String aToken) {
		this.aToken = aToken;
	}

	public String getsToken() {
		return sToken;
	}

	public void setsToken(String sToken) {
		this.sToken = sToken;
	}

	@Override
	public HessianConnection open(URL url) throws IOException {
		HessianConnection hessianConnection = super.open(url);
		String nowTime = String.valueOf(System.currentTimeMillis());
		if (aToken != null && sToken != null) {
			hessianConnection.addHeader("time", nowTime);
			hessianConnection.addHeader("hash", HessianSecurityUtil.hash(sToken, nowTime));
			hessianConnection.addHeader("aToken", aToken);
		}
		return hessianConnection;
	}

}
