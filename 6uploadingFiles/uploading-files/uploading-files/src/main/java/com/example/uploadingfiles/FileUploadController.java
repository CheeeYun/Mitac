package com.example.uploadingfiles;

import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller //標註為Controller類別 處理HTTP請求
public class FileUploadController {
    private final StorageService storageService; //存儲服務的介面或類別


    @Autowired                          //將storageService物件注入這個Controller
    public FileUploadController(StorageService storageService){
        this.storageService= storageService;
    }

    @GetMapping("/")//用MvcUriComponentsBuilder利用方法名稱創建URL並轉成字串
    public String listUploadedFiles(Model model) throws IOException { //將model(url list)傳入並返回一個字串

        model.addAttribute("files", storageService.loadAll().map( //建立一個叫files的model(就是url列表)
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        return "uploadForm";
    }
    @GetMapping("/files/{filename:.+}")//路徑變數.+代表任意擴展的檔案名稱
    @ResponseBody //指定該方法為http響應的主體內容
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){//@PathVariable路徑參數設定
        System.out.println(filename);
        Resource file= storageService.loadAsResource(filename); //丟入filename拿到Resource
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, //ResponseEntity.ok()200狀態
                "attachment; filename=\""+file.getFilename()+"\"").body(file);
        //將Resource作為Http響應主體返回並設置標頭資訊(用來指定下載名稱)
    }

    @PostMapping("/")   //傳入兩個參數file和redirectAttributes
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes){
            storageService.store(file); //將file送進去判斷並儲存
             redirectAttributes.addFlashAttribute("message", //redirectAttribute的key=message,value=下面字串
                    "導向成功Message You successfully uploaded"+file.getOriginalFilename()+"!"
            );
            return "redirect:/"; //在@Controller裡"redirect:"會自動導向頁面
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc){
        return ResponseEntity.notFound().build();
    }
}
//在@Controller中使用@ExceptionHandler專門用於處理特定類別的Exception
//回應一個ResponseEntity 404