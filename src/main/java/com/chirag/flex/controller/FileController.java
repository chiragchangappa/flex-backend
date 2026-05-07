package com.chirag.flex.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

	@PostMapping("/upload")
	public String upload(@RequestParam MultipartFile file) throws Exception {

	    String dir = System.getProperty("user.dir") + "/uploads/";

	    File folder = new File(dir);
	    if (!folder.exists()) {
	        folder.mkdirs(); // 🔥 create folder properly
	    }

	    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

	    String path = dir + fileName;

	    file.transferTo(new File(path));

	    return "uploads/" + fileName;
	}
}
