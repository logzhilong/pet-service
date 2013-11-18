package com.momoplan.pet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.domain.PetFile;
import com.momoplan.pet.domain.PetVersion;
import com.momoplan.pet.service.UploadService;

@Controller
@RequestMapping("static")
public class StaticController {
	
	@Autowired
	UploadService uploadService;

	@RequestMapping("/{id}")
	public void download(@PathVariable("id")String id,HttpServletResponse response){
		int idx = id.indexOf("_");
		if(idx!=-1){
			id=id.substring(0, idx);
		}
		PetFile petFile = PetFile.findPetFile(Long.parseLong(id));
		  response.setCharacterEncoding("utf-8");  
	        response.setContentType("multipart/form-data");  
	  
	        response.setHeader("Content-Disposition", "attachment;fileName="+petFile.getName());  
	        try {  
	            InputStream inputStream=new FileInputStream(uploadService.getUploadUrl()+File.separator+petFile.getId());  
	            OutputStream os=response.getOutputStream();  
	            byte[] b=new byte[1024];  
	            int length;  
	            while((length=inputStream.read(b))>0){  
	                os.write(b,0,length);  
	            }  
	            inputStream.close();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}
	
	@RequestMapping("/petVersion/{version}")
	public void downloadVersion(@PathVariable("version")String version,HttpServletResponse response){
		PetVersion petVersion = PetVersion.findPetVersionsByPetVersionEquals(version).getSingleResult();
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + petVersion.getPetVersion());
		try {
			InputStream inputStream = new FileInputStream("E:\\" + File.separator + petVersion.getId()+".petV");
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
