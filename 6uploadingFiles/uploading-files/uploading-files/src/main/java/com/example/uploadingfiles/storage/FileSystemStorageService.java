package com.example.uploadingfiles.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;


	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}//建構FSS需要丟一個StorageProperties進去會回傳一個String當作Path並存起來

	@Override
	public void store(MultipartFile file) { //可以上傳任意檔案
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = this.rootLocation.resolve(  //resolve可以將絕對路徑加上檔案名稱
					Paths.get(file.getOriginalFilename())) 	 	//normalize將. ..非必要路徑元素去除
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check 		//判斷父目錄是否與絕對目錄相同(預期是相同)
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
//			if(Files.exists(destinationFile)){ 	 	//判斷檔案是否重複上傳
//				throw new StorageException("The file has been upload.");
//			}
			try (InputStream inputStream = file.getInputStream()) { //InputStream會把檔案用2進位的方式保存起來
				Files.copy(inputStream, destinationFile, 	//拷貝這個路徑和檔案如果已經存再就覆蓋(若重複上傳)
					StandardCopyOption.REPLACE_EXISTING); 	//拷貝的東西去哪了?
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try { 				//walk遍歷檔案 路徑為rootLocation深度為1 代表只找檔案
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation)) //並把前面的路徑過濾掉
				.map(this.rootLocation::relativize); //映射出檔案的相對路徑存在Stream中(參照relativize)
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename); //一個可以找出路徑+檔名的方法
	}

	@Override 						//方法是傳入檔案名稱拿到資源
	public Resource loadAsResource(String filename) { //Resource是spring的介面代表可被載入的資源
		try {
			Path file = load(filename); //將檔名轉成path物件
			Resource resource = new UrlResource(file.toUri());//將path物件轉成uri存在resource並用url的形式訪問資源
			if (resource.exists() || resource.isReadable()) {//是否存在? 可讀取?
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() { //FileSystemUtils.deleteRecursively是spring本身的工具
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	} 	//遍歷刪除指定目錄下的檔案(包刮目錄) 因為接受參數為File所以要把路徑轉成File

	@Override
	public void init() { //初始化儲存空間的路徑
		try {
			Files.createDirectories(rootLocation); //利用rootLocation指定目錄
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
