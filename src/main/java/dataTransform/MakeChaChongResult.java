package dataTransform;

import java.util.List;

import hanLP.HanLPUtil;
import modal.Check;
import modal.HotWord;
import mysql.DataBaseImp;
import mysql.DataBaseOp;
import pachong.PaChongBean;

public class MakeChaChongResult {
	
	/**
	 * 第一版
	 */
	public void generateChaChongResult() {
		//提取热词  <- original_word
		DataBaseOp db=new DataBaseImp();
		List<HotWord> words=db.exportAllData();
		
		HanLPUtil hanlp=new HanLPUtil();
		//爬去百科数据
		for(HotWord hw:words) {
			Check icheck=new Check();
			icheck.setWord(hw.getWord());
			icheck.setDbText(hw.getExplaining());
			String netText=PaChongBean.getNetExplicate(hw.getWord());//??????????????
			icheck.setNetText(netText);
		//对比相似度
			String similarity=String.valueOf(hanlp.similarityCheck(netText, hw.getExplaining()));
			icheck.setDegree(similarity);
			
		//保存数据->checking
			db.addCheck(icheck);
		}
		
	}
	
	/**
	 * 现用版 - 填check数据
	 */
	public static void makeCheckTable() {
		DataBaseOp db=new DataBaseImp();
		List<HotWord> words=db.exportAllData();
		
		HanLPUtil hanlp=new HanLPUtil();
		for(HotWord hw:words) {
			Check icheck=new Check();
			
			icheck.setWord(hw.getWord());
			icheck.setDbText(hw.getExplaining());
			
			String netText=db.getNetText(hw.getWord());
			icheck.setNetText(netText);

			String similarity=String.valueOf(hanlp.similarityCheck(netText, hw.getExplaining()));
			icheck.setDegree(similarity);
			
			db.addCheck(icheck);
		}
	}
}
