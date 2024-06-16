package com.cafe.fileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.entities.Category;
import com.cafe.entities.Products;
import com.cafe.entities.UserDAO;

@Service
public class FileService {
	public UserDAO setAndUploadFile(String path, UserDAO dao, MultipartFile file) {
		
		String name = file.getOriginalFilename();
		String newPath = path + File.separator + name;

		// create folder if not existed
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// copy file
		dao.setUser_image("Images/profile/" + name);
		System.out.println("updated");
		try {
			Files.copy(file.getInputStream(), Paths.get(newPath));
			System.out.println("returning");

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dao;
	}
	// path = path in the project
	public Category getAndSetCategoryImage(String path , Category category , MultipartFile file) {
		
		String originalFileName = file.getOriginalFilename();
		String newPath = path + File.separator + originalFileName; // path for image
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		// copy file
		category.setCover("Images/cover/" + originalFileName);
		try {
			Files.copy(file.getInputStream(), Paths.get(newPath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return category;
	}
	// path = path in the project
	public Products getAndSetProductImage(String path , Products products , MultipartFile file) {
		
		String originalFileName = file.getOriginalFilename();
		String newPath = path + File.separator + originalFileName; // path for image
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		// copy file
		products.setProductImage("Images/cover/" + originalFileName);
		try {
			Files.copy(file.getInputStream(), Paths.get(newPath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return products;
	}
}
