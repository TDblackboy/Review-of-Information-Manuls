package dataTransform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hanLP.HanLPClassifierUtil;
import hanLP.HanLPUtil;
import modal.HotWord;
import mysql.DataBaseImp;
import mysql.DataBaseOp;

public class MakeClassData {

	/**
	 * @throws IOException 
	 * 
	 */
	public static void fillInTypeSetTable() throws IOException {
		
		HanLPUtil hanlp=new HanLPUtil();
		
		//获取original_word
		DataBaseOp db=new DataBaseImp();
		List<HotWord> hws=db.exportAllData();
		
		//调用分类器分类
		for(HotWord hw:hws) {
			
			String dbText=hw.getExplaining();
			
			List<String> keywords=new ArrayList<String>();
			String keyword="";
			String mohu="";
			String word="";
			String type="";
			
			keywords=hanlp.extractKeyWordUsing(dbText, 3);
			keyword=keywords.get(0)+"-"+keywords.get(1)+"-"+keywords.get(2);
			mohu=keywords.get(0);
			word=hw.getWord();
			type=HanLPClassifierUtil.myClassifier(dbText);
			
			db.addTypeSet(word, keyword, mohu, type);
			
		}
		
		System.out.println("分类完成");
	}
}
