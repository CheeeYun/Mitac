package com.example.uploadingfiles;

import com.example.uploadingfiles.storage.StorageProperties;
import com.example.uploadingfiles.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class) //寫在執行程式上搭配@ConfigurationProperties使用
public class UploadingFilesApplication { 		 	//用來配置物件屬性

	public static void main(String[] args) {
		SpringApplication.run(UploadingFilesApplication.class, args);
	}

	@Bean 		//註冊一個Bean(有返回值)
	CommandLineRunner init(StorageService storageService){ //spring boot在執行時會掃描到含有bean的
		return(args)->{ 								//CommandLineRunner自動執行run方法
			storageService.deleteAll();
			storageService.init();
		};
	}
}
