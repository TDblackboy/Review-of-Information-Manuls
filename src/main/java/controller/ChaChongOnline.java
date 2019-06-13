package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hanLP.HanLPUtil;
import mysql.DataBaseImp;
import mysql.DataBaseOp;
import net.sf.json.JSONObject;

public class ChaChongOnline extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChaChongOnline() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		System.out.println("跳转：ChaChongTest");

		// 获取提交数据
		String reci = request.getParameter("reci");
		String testtext = request.getParameter("text");

		// 提取reci相应数据库数据
		DataBaseOp db = new DataBaseImp();
		String dbtext = db.loadByName(reci);
		
		// 相似性查重
		HanLPUtil hanlp = new HanLPUtil();
		double degree = 0;
		if(dbtext!=null&&!dbtext.equals("")) {
			degree = hanlp.similarityCheck(testtext, dbtext);
		}
		String degreeStr=String.valueOf(degree);
		// 构造json返回
		JSONObject jo = new JSONObject();
		jo.put("reci", reci);
		jo.put("degree",degreeStr);
		jo.put("dbtext", dbtext);
		jo.put("testtext", testtext);
		
		response.getWriter().print(jo);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
