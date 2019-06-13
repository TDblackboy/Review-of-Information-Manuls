package printPDF;

import java.io.File;
import java.util.List;

import modal.Check;
import mysql.DataBaseImp;
import mysql.DataBaseOp;

public class PDFTest {

	public static void main(String[] args) {
		
		String path="D:\\信息查重结果.pdf";
		File file=new File(path);
		
		DataBaseOp op=new DataBaseImp();
		List<Check> checks=op.exportChecks();
		
		if(file.exists()) {
			file.delete();
			System.out.println(path+"--> 存在已删除");
		}
		try {
			//PDFDemo.exportPdf(file);
			if(checks!=null) {
				PrintChaChongPDF.exportPdf(file,checks);
			}else {
				System.out.println("数据库没有数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--> 生成PDF"+path);
		
	}

}
