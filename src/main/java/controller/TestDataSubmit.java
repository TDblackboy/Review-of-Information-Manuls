package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hanLP.HanLPUtil;
import net.sf.json.JSONObject;



public class TestDataSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public TestDataSubmit() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		System.out.println("跳转：TestDataSubmit");
		
		//获取提交数据
		String reci=request.getParameter("reci");
		String text=request.getParameter("text");
		
		//处理-并返回reci，type,keywords(3),mohu;
		HanLPUtil hanlp=new HanLPUtil();
		
		String type="?";
		
		String mohu=hanlp.extractMohuField(text);
		
		String keyword="";
		List<String> keywords=hanlp.extractKeyWordUsing(text, 3);
		for(int i=0;i<keywords.size();i++) {
			if(i!=keywords.size()-1) {
				keyword=keyword+keywords.get(i)+",";
			}else {
				keyword+=keywords.get(i);
			}
		}
		
		//构造json返回
		JSONObject jo=new JSONObject();
		jo.put("reci", reci);
		jo.put("type", type);
		jo.put("keyword", keyword);
		jo.put("mohu", mohu);
		System.out.println(jo);
		response.getWriter().print(jo);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
