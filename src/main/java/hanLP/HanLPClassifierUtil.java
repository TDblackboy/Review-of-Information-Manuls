package hanLP;

import static com.hankcs.hanlp.utility.Predefine.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.hankcs.hanlp.classification.classifiers.IClassifier;

public class HanLPClassifierUtil {

	/**
	 * 模型训练加载的语料库路径
	 */
	public static final String CORPUS_FOLDER = "data/搜狗文本分类语料库";

	/**
	 * 模型保存路径
	 */
	public static final String MODEL_PATH = "data/svm-classification-model.ser";

	/*
	 * 外部调用接口 - 自定义的并且封装了HanLP的分类器的接口
	 */
	public static String myClassifier(String text) throws IOException{
		IClassifier classifier = new LinearSVMClassifier(trainOrLoadModel());
		String type=classifier.classify(text);
		return type;
	}


	private static LinearSVMModel trainOrLoadModel() throws IOException {
		LinearSVMModel model = (LinearSVMModel) readObjectFrom(MODEL_PATH);
		if (model != null)
			return model;

		File corpusFolder = new File(CORPUS_FOLDER);
		if (!corpusFolder.exists() || !corpusFolder.isDirectory()) {
			System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载："
					+ "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
			System.exit(1);
		}

		IClassifier classifier = new LinearSVMClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
		classifier.train(CORPUS_FOLDER); // 训练后的模型支持持久化，下次就不必训练了
		model = (LinearSVMModel) classifier.getModel();
		saveObjectTo(model, MODEL_PATH);
		return model;
	}

	/**
	 * 序列化对象
	 *
	 * @param o
	 * @param path
	 * @return
	 */
	public static boolean saveObjectTo(Object o, String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			logger.warning("在保存对象" + o + "到" + path + "时发生异常" + e);
			return false;
		}

		return true;
	}

	/**
	 * 反序列化对象
	 *
	 * @param path
	 * @return
	 */
	public static Object readObjectFrom(String path) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			Object o = ois.readObject();
			ois.close();
			return o;
		} catch (Exception e) {
			logger.warning("在从" + path + "读取对象时发生异常" + e);
		}

		return null;
	}
}
