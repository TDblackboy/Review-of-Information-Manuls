package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modal.TypeSet;
import mysql.DataBaseImp;
import mysql.DataBaseOp;
import net.sf.json.JSONArray;

public class TypeRequest2_1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TypeRequest2_1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		System.out.println("跳转：TypeRequest2_1");
		
		DataBaseOp db=new DataBaseImp();
		List<TypeSet> words=db.exportTypeSet();
		
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
