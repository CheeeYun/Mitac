package com.example.uploadingfiles.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage") //這個搭配EnableConfigurationProperties使用代表配置類
public class StorageProperties { 	//並把所有配置的物件屬性都寫在這個類統一管理

	/**
	 * Folder location for storing files
	 */
	private String location = "upload-dir";
	//location 初始化

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
