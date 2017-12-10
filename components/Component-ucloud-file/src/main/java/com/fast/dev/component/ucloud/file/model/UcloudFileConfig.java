package com.fast.dev.component.ucloud.file.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 蒋健
 *
 */
@Component
public class UcloudFileConfig implements Serializable{

		private static final long serialVersionUID = 1L;
		
		private String UCloudPublicKey;
		
		private String UCloudPrivateKey;
		
		private String ProxySuffix;
		
		private String DownloadProxySuffix;

		private String bucketName;
		
		public String getBucketName() {
			return bucketName;
		}

		public void setBucketName(String bucketName) {
			this.bucketName = bucketName;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getUCloudPublicKey() {
			return UCloudPublicKey;
		}

		public void setUCloudPublicKey(String uCloudPublicKey) {
			UCloudPublicKey = uCloudPublicKey;
		}

		public String getUCloudPrivateKey() {
			return UCloudPrivateKey;
		}

		public void setUCloudPrivateKey(String uCloudPrivateKey) {
			UCloudPrivateKey = uCloudPrivateKey;
		}

		public String getProxySuffix() {
			return ProxySuffix;
		}

		public void setProxySuffix(String proxySuffix) {
			ProxySuffix = proxySuffix;
		}

		public String getDownloadProxySuffix() {
			return DownloadProxySuffix;
		}

		public void setDownloadProxySuffix(String downloadProxySuffix) {
			DownloadProxySuffix = downloadProxySuffix;
		}
		
		
		
}
