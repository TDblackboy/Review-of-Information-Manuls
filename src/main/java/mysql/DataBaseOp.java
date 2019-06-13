package mysql;

import java.util.List;

import modal.Check;
import modal.HotWord;
import modal.TypeSet;

public interface DataBaseOp {
	public void addData(String word,String explain);
	public List<HotWord> exportAllData();
	public List<TypeSet> exportTypeSet();
	public String loadByName(String name);
	public List<Check> exportChecks();
	public void addCheck(Check cc);
	public String getNetText(String word);
	public void addTypeSet(String word,String keyword,String mohu,String type);
}
