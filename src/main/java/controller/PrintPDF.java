package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import modal.Check;
import mysql.DataBaseImp;
import mysql.DataBaseOp;
import printPDF.PrintChaChongPDF;

public class PrintPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PrintPDF() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		//应该实现下载？？？？？？？？？？？？？？？
		
		
		// 提取数据库数据
		DataBaseOp op = new DataBaseImp();
		List<Check> checks = op.exportChecks();

		// 打印
		String PDF="";
		String path = "D:\\信息查重结果.pdf";
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			System.out.println(path + "--> 存在已删除");
		}
		if (checks != null) {
			try {
				PrintChaChongPDF.exportPdf(file, checks);
				PDF="已打印";
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			PDF="打印失败";
			System.out.println("数据库没有数据");
		}
		System.out.println("--> 生成PDF" + path);

		response.getWriter().print(PDF);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
