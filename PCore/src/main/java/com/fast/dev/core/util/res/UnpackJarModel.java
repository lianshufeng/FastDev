package com.fast.dev.core.util.res;

/**
 * jar解压类型
 * 
 * @author 练书锋
 *
 */
public class UnpackJarModel {
	//资源类型
	private ResourcesType resourcesType;
	// 源路径
	private String source;
	// 解压目标的路径
	private String target;
	// 相同文件处理方式
	private UnpackType unpackType;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public UnpackType getUnpackType() {
		return unpackType;
	}

	public void setUnpackType(UnpackType unpackType) {
		this.unpackType = unpackType;
	}


	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	

	public ResourcesType getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(ResourcesType resourcesType) {
		this.resourcesType = resourcesType;
	}

	public UnpackJarModel() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param resourcesType
	 * @param source
	 * @param target
	 * @param unpackType
	 */
	public UnpackJarModel(ResourcesType resourcesType, String source, String target, UnpackType unpackType) {
		this.resourcesType = resourcesType;
		this.source = source;
		this.target = target;
		this.unpackType = unpackType;
	}
	
	

}
