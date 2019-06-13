package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modal.HotWord;
import mysql.DataBaseImp;
import mysql.DataBaseOp;
import poi.DocumentRead;
import uploader.WebUploadFileUtil;

@MultipartConfig
public class DataImportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataImportServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");

		String filePath="";
		
		//接受上传文件
		WebUploadFileUtil wufu = new WebUploadFileUtil();
		filePath=wufu.uploadTolocal(request);
		System.out.println("上传到本地文件系统");
		
		//使用特定的导入数据库类 - 读取doc文档的类
		DocumentRead dr=new DocumentRead();
		if(dr.readDoc(filePath)) {
			System.out.println("导入MySQL");
		}else {
			System.out.println("没有导入MySQL");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
