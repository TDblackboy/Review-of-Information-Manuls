package hanLP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

/*
 * 使用HanLP第三方工具包-封装了HanLP的方法
 */
public class HanLPUtil {
	
	/**
	 * 分词
	 * 输入：text
	 * 输出：list<Term>
	 */
	public List<Term> segmentUsing(String text){
		List<Term> words=HanLP.segment(text);
		return words;
	}
	
	/**
	 * 提取关键字
	 * 输入：text，key_word_number
	 * 输出：list<String>
	 */
	public List<String> extractKeyWordUsing(String text,int key_word_number){
		List<String> keyWords=HanLP.extractKeyword(text, key_word_number);
		return keyWords;
	}
	
	/**
	 * 提取模糊字段
	 * 输入：text，
	 * 输出：String
	 */
	public String extractMohuField(String text){
		List<String> keyWords=HanLP.extractKeyword(text, 1);
		String mohu=keyWords.get(0);
		return mohu;
	}
	
	/**
	 * 相似性查重
	 * 输入：text1 text2
	 * 输出：相似度double
	 */
	public double similarityCheck(String text1,String text2) {
		double degree=0;
		List<String> sent1Words = getSplitWords(text1);
        List<String> sent2Words = getSplitWords(text2);
        List<String> allWords = mergeList(sent1Words, sent2Words);

        int[] statistic1 = statistic(allWords, sent1Words);
        int[] statistic2 = statistic(allWords, sent2Words);

        double dividend = 0;
        double divisor1 = 0;
        double divisor2 = 0;
        for (int i = 0; i < statistic1.length; i++) {
            dividend += statistic1[i] * statistic2[i];
            divisor1 += Math.pow(statistic1[i], 2);
            divisor2 += Math.pow(statistic2[i], 2);
        }

        degree = dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
		return degree;
	}
	private static int[] statistic(List<String> allWords, List<String> sentWords) {
        int[] result = new int[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            result[i] = Collections.frequency(sentWords, allWords.get(i));
        }
        return result;
    }

    private static List<String> mergeList(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        return result.stream().distinct().collect(Collectors.toList());
    }

    private static List<String> getSplitWords(String sentence) {
        // 标点符号会被单独分为一个Term，去除之
        return HanLP.segment(sentence).stream().map(a -> a.word).filter(s -> !"`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？".contains(s)).collect(Collectors.toList());
    }
    
    
}
