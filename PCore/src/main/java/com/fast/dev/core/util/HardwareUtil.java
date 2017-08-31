package com.fast.dev.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class HardwareUtil {

	/**
	 * 取出硬件id
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getHardwareId() {
		try {
			List<List<Integer>> list = new ArrayList<List<Integer>>();
			// 依次取出所有的mac地址，并添加进数组
			Enumeration<NetworkInterface> es = NetworkInterface.getNetworkInterfaces();
			while (es.hasMoreElements()) {
				NetworkInterface networkInterface = es.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				if (inetAddresses != null) {
					if (networkInterface.getHardwareAddress() != null) {
						byte[] hardwareAddress = networkInterface.getHardwareAddress();
						if (hardwareAddress != null) {
							List<Integer> mac = new ArrayList<Integer>();
							// 取出mac地址，如果为ipv4则自动添加两位与ipv6相同
							if (hardwareAddress.length == 6) {
								mac.add(0);
								mac.add(0);
							}
							for (byte b : hardwareAddress) {
								mac.add(((int) b) & 0xff);
							}
							list.add(mac);
						}
					}

				}
			}
			// 合并所有mac地址所有数字的合
			int[] result = new int[8];
			for (List<Integer> integers : list) {
				for (int i = 0; i < integers.size(); i++) {
					result[i] += integers.get(i);
				}
			}
			String ips = "";
			for (int i = 0; i < result.length; i++) {
				ips += String.valueOf(result[i]);
			}
			return ips;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getHardwareId());
	}
}
