package printPDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import modal.Check;

public class PrintChaChongPDF {

	private static BaseFont baseFont = getBaseFont();
	private static Font chineseFont = new Font(baseFont);
	private static Font chineseFontBold = new Font(baseFont,15,Font.BOLD);
	private static Font firstTitleFont=new Font(baseFont,22,Font.BOLD);
	/**
	 * 打印查重结果 - - - - - - 定 制
	 * 
	 * @param file
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public static boolean exportPdf(File file,List<Check>checks) throws FileNotFoundException, DocumentException {
		boolean pdfOK = false;

		Document document = new Document(PageSize.A4, 150, 150, 100, 100);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		
		document.addTitle("信息技术手册查重结果报告");
		document.addAuthor("库奇");
		document.addSubject("O_O");
		document.addKeywords("O_O");
		
		//标题
		Paragraph title=new Paragraph("查重结果报告",firstTitleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		
		//相关
		Paragraph checkInfo=new Paragraph("检测范围：百度百科",chineseFont);
		//checkInfo.setAlignment(Element.ALIGN_CENTER);
		checkInfo.setIndentationLeft(90);
		checkInfo.setLeading(100);
		document.add(checkInfo);

		Paragraph introduce=new Paragraph("检测员：库奇",chineseFont);
		//introduce.setAlignment(Element.ALIGN_CENTER);
		introduce.setIndentationLeft(90);
		introduce.setLeading(20);
		document.add(introduce);
		
		Paragraph result=new Paragraph("检测结果：如下",chineseFont);
		result.setIndentationLeft(90);
		result.setLeading(20);
		result.setSpacingAfter(60);
		document.add(result);
		
		//设置字体颜色
		Font redFont = new Font(baseFont,15,Font.BOLD);
        redFont.setColor(BaseColor.RED);
		//结果
		for(Check check:checks) {
			
			//标头
			Paragraph word=new Paragraph("单词："+check.getWord(),redFont);
			word.setSpacingBefore(40);//与前一个单词的距离
			document.add(word);
			//相似比
			Paragraph similarity=new Paragraph("相似度："+check.getDegree(),chineseFontBold);
			similarity.setSpacingBefore(10);
			document.add(similarity);
			//原文
			Paragraph dbText=new Paragraph("原文：",chineseFontBold);
			dbText.setSpacingBefore(10);
			document.add(dbText);
			Paragraph dbTextContent=new Paragraph(check.getDbText(),chineseFont);
			dbTextContent.setSpacingBefore(10);
			dbTextContent.setIndentationLeft(40);
			document.add(dbTextContent);
			//网络对比数据
			Paragraph netText=new Paragraph("百度百科文章：",chineseFontBold);
			netText.setSpacingBefore(10);
			document.add(netText);
			Paragraph netTextContent=new Paragraph(check.getNetText(),chineseFont);
			netTextContent.setSpacingBefore(10);
			netTextContent.setIndentationLeft(40);
			document.add(netTextContent);
		}
		
		document.close();
		pdfWriter.close();
		return pdfOK;

	}

	private static BaseFont getBaseFont() {
		try {
			return BaseFont.createFont("simfang.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
