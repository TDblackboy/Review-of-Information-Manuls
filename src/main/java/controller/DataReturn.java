package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modal.HotWord;
import mysql.DataBaseImp;
import mysql.DataBaseOp;
import net.sf.json.JSONArray;

public class DataReturn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataReturn() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		System.out.println("跳转：DataReturn");
		
		DataBaseOp db=new DataBaseImp();
		List<HotWord> words=db.exportAllData();
		
		//转json
		JSONArray ja=JSONArray.fromObject(words);
		System.out.print("json_array:"+ja);
		
		//返回ajax
		response.getWriter().print(ja);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
