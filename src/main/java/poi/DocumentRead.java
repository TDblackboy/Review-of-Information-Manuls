package poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Range;

import mysql.DataBaseImp;
import mysql.DataBaseOp;

public class DocumentRead {

	public boolean readDoc(String filePath) {
		
		boolean ok=false;
		if (filePath.endsWith(".doc")) {
			// 输入流-基类
			InputStream is = null;
			try {
				is = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("文件打开失败。");
			}
			// 加载doc文档
			try {
				DataBaseOp db = new DataBaseImp();
				HWPFDocument doc = new HWPFDocument(is);
				Range text = doc.getRange();// 整个文档
				Range hotWord = text.getSection(2);// 0-封面，1-目录，2-文本；第3小节
				// 段落处理
				String word = "";
				String explaining = "";
				int wordOK = 0;
				int explainOK = 0;// 判断当前word&explain是否可以填入数据库
				int count = 24;// 读取几条数据到数据库
				int begin = 2;// 段落读取位置

				for (int i = 0; i < count;) {
					Range para = hotWord.getParagraph(begin);
					CharacterRun field = para.getCharacterRun(0);
					int fontSize = field.getFontSize();
					if (fontSize == 26) {
						word = para.text();
						wordOK = 1;
						begin++;
					} else {
						while (fontSize < 26) {
							explaining += para.text();
							begin++;
							para = hotWord.getParagraph(begin);
							field = para.getCharacterRun(0);
							fontSize = field.getFontSize();
						}
						explainOK = 1;
					}
					// 判断word&explain是否可以填入数据库
					if (wordOK == 1 && explainOK == 1) {
						db.addData(word, explaining);
						i++;
						// 填入数据库后，一切归"0"
						wordOK = 0;
						explainOK = 0;
						word = "";
						explaining = "";
					}
				}
				// 输出测试
				// System.out.println("读取：" + "head:");
				ok=true;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IO错误。");
			}
		} else {
			System.out.println("文件格式 error:not .doc");
		}
		return ok;
	}
	
}
