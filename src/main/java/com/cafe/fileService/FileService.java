package com.cafe.fileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.entities.Category;
import com.cafe.entities.Products;
import com.cafe.entities.UserDAO;

import jakarta.validation.Valid;

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
	public Category getAndSetCategoryImage(String path, Category category, MultipartFile file) throws IOException {

		String originalFileName = file.getOriginalFilename();
		String newPath = path + File.separator + originalFileName; // path for image

		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// copy file
		category.setCover("Images/cover/" + originalFileName);

		Files.copy(file.getInputStream(), Paths.get(newPath));

		return category;
	}

	// path = path in the project
	public Products getAndSetProductImage(String path, Products products, MultipartFile file) {

		String originalFileName = file.getOriginalFilename();
		String newPath = path + File.separator + originalFileName; // path for image

		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// copy file
		products.setProductImage("Images/products/" + originalFileName);
		try {
			Files.copy(file.getInputStream(), Paths.get(newPath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return products;
	}

	public Category removeFile(@Valid Category category, String path) {
		Path path2 = Paths.get(path + File.separator + category.getCover());

		try {
			Files.delete(path2);
			category.setCover("");
		} catch (Exception e) {
			System.out.println("path :::: " + path2 + " | " + category.getCover());
		}

		return category;
	}

	public Products deleteProductImage(Products prod, String deleteProductPath) {
		
		Path path = Paths.get(deleteProductPath + File.separator + prod.getProductImage());
		try {
			Files.delete(path);
			prod.setProductImage("");
		}catch (Exception e) {
			System.out.println("Exception while removing product image "+e);
		}
		return prod;
	}
	
	public UserDAO removeUserImage(UserDAO  user, String removePath) {
		
		System.out.println("REMOVING IMAGE");
		
		Path path = Paths.get(removePath + File.separator + user.getUser_image());
		try {
			Files.delete(path);
			user.setUser_image("");
		}catch (Exception e) {
			System.out.println("Exception while removing product image "+e);
		}
		return user;
	}
}
