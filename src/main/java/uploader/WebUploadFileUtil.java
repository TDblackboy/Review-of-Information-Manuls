package uploader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@MultipartConfig
public class WebUploadFileUtil {

	/*
	 * 接口设置思想
	 * 
	 * 解释：前台JS发送请求上传文件，接受request并处理
	 * 
	 * 输入：request
	 * 
	 * 输出：string[文件路径 - 完成文件上传后的]
	 * 
	 * 配合：uploadConfig.properties / XML[最好] 配置文件
	 */
	public String uploadTolocal(HttpServletRequest request) throws ServletException {

		System.out.println("使用：WebUploarFileUtil.class");

		String save = "";

		// 0 配置文件操作properties
		// --------配置文件的路径可以修改成动态的？？----------
		InputStream proIn = getClass().getClassLoader().getResourceAsStream("uploader/uploadConfig.properties");
		Properties properties = new Properties();
		try {
			properties.load(proIn);
		} catch (IOException e) {
			System.out.println("error:properties.load(proIn)?");
			e.printStackTrace();
		}

		// 1文件夹设置
		String systemPath = properties.getProperty("fileSystemPath");// 系统路径

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 根据时间日期设置文件夹-防止重复-日志记录
		String ymd = sdf.format(new Date());
		// System.out.println("yyyyMMdd:"+ymd);

		String savePath = systemPath + "/" + ymd;// 保存的文件夹
		// System.out.println("savePath:"+savePath);

		File local = new File(savePath);// 需要创建文件夹
		if (!local.exists()) {
			local.mkdirs();
			// System.out.println("创建文件夹："+savePath);
		}

		// 2文件名设置
		Collection<Part> parts = null;
		try {
			parts = request.getParts();// <<----------- 处理的内容
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Part part : parts) {
			String contentDisposition = part.getHeader("content-disposition");
			String fileName = getFileName(contentDisposition);
			if (fileName.equals("")) {
				continue;
			} else {
				String pureFileName = fileName.substring(1, fileName.lastIndexOf("."));// 纯文件名称
				// System.out.println("pureFileName:" + pureFileName);

				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.lastIndexOf("\""));// 文件后缀/文件类型
				// System.out.println("suffix:" + suffix);

				String saveFileName = pureFileName + "." + suffix;// 文件组合设置全名
				// System.out.println("saveFileName:" + saveFileName);

				save = savePath + "/" + saveFileName;// 文件总路径 - 重点！！！！！！！！
				System.out.println("save:" + save);

				/*
				 * 3写入 - 主要是这一步
				 */
				try {
					part.write(save);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			proIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return save;
	}

	/*
	 * 从 part.getHeader("content-disposition")中提取file name.
	 */
	private String getFileName(String contentDisposition) {
		String[] strs1 = contentDisposition.split(";");
		// System.out.println("str1:" + strs1);
		if (strs1.length < 3) {
			return "";
		} else {
			String[] strs2 = strs1[2].split("=");
			// System.out.println("strs2:" + strs2);
			String fileName = strs2[1];
			// System.out.println("fileName:" + fileName);
			fileName = fileName.replaceAll("/", "");
			return fileName;
		}
	}
}
