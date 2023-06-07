package com.example.uploadingfiles.storage;

public class StorageException extends RuntimeException {

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
	//Throwable是Error,Exception父類可被拋出
	// 這裡使用自定義Exception並使用兩個建構式可以更靈活使用1只要給訊息2給訊息還有出錯原因
}
