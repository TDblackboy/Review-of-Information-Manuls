package hanLP;

import java.io.IOException;

public class HanLPTest {

	public static void main(String[] args) throws IOException {
		//相似性
		/*
		 * HanLPUtil util=new HanLPUtil(); String s1 = "子类可以覆盖父类的成员方法，也可以覆盖父类的成员变量";
		 * String s2 = "子类不可以覆盖父类的成员方法，也不可以覆盖父类的成员变量"; System.out.println("\"" + s1 +
		 * "\"" + "与" + "\"" + s2 + "的相似度是：" + util.similarityCheck(s1, s2));
		 */
		//分类
		String text=
				"数字城市”（英文：digital city）"
				+ "以计算机技术、多媒体技术和大规模存储技术为基础，以宽带网络为纽带，运用遥感、全球定位系统、地理信息系统、遥测、仿真-虚拟等技术，"
				+ "对城市进行多分辨率、多尺度、多时空和多种类的三维描述，"
				+ "即利用信息技术手段把城市的过去、现状和未来的全部内容在网络上进行数字化虚拟实现。";
		String type=HanLPClassifierUtil.myClassifier(text);
		System.out.println("classify:"+type);
	}

}
