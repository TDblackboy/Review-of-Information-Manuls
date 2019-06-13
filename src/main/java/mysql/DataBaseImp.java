package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modal.Check;
import modal.HotWord;
import modal.TypeSet;

public class DataBaseImp implements DataBaseOp {
	
	private static String table="original_words";
	private static String table2="typeset";
	private static String check="checking";
	private static String baike="baike";
	
	public void addData(String word, String explain) {

		String sql="insert into " +table+ "(word,explaining) values(?,?)";
		Connection con=DBUtil.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, word);
			ps.setString(2, explain);
			ps.executeUpdate();
			DBUtil.close(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed:insert");
		}
		DBUtil.close(con);
		//console print 
		System.out.println("END<");
	}

	@Override
	public List<HotWord> exportAllData() {
		String sql="select * from "+table;
		Connection con=DBUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<HotWord> words=new ArrayList<HotWord>();
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				HotWord temp=new HotWord();
				temp.setWord(rs.getString(2));
				temp.setExplaining(rs.getString(3));
				words.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed:load");
		}
		DBUtil.close(rs);
		DBUtil.close(ps);
		DBUtil.close(con);
		//console print 
		System.out.println(">> export ");
		return words;
	}

	@Override
	public List<TypeSet> exportTypeSet() {
		String sql="select * from "+table2;
		Connection con=DBUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TypeSet> words=new ArrayList<TypeSet>();
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				TypeSet temp=new TypeSet();
				temp.setId(rs.getInt(1));
				temp.setReci(rs.getString(2));
				temp.setKeyWord(rs.getString(3));
				temp.setMohu(rs.getString(4));
				temp.setType(rs.getString(5));
				words.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed:load");
		}
		DBUtil.close(rs);
		DBUtil.close(ps);
		DBUtil.close(con);
		//console print 
		System.out.println(">> export typeset. ");
		return words;
	}

	@Override
	public String loadByName(String name) {
		String text="";
		String sql="select explaining from "+table+" where word like '%"+name+"%'";
		Connection con=DBUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				text=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed:load");
		}
		DBUtil.close(rs);
		DBUtil.close(ps);
		DBUtil.close(con);
		//console print 
		System.out.println(">> export explaining. ");
		return text;
	}

	@Override
	public List<Check> exportChecks() {
		String sql="select * from "+check;
		Connection con=DBUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Check> check=new ArrayList<Check>();
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Check temp=new Check();
				temp.setId(rs.getInt(1));
				temp.setWord(rs.getString(2));
				temp.setDegree(rs.getString(3));
				temp.setDbText(rs.getString(4));
				temp.setNetText(rs.getString(5));
				check.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.close(rs);
		DBUtil.close(ps);
		DBUtil.close(con);
		//console print 
		System.out.println(">> export check. ");
		return check;
	}

	@Override
	public void addCheck(Check cc) {
		String sql="insert into " +check+ "(word,degree,dbText,netText) values(?,?,?,?)";
		Connection con=DBUtil.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, cc.getWord());
			ps.setString(2, cc.getDegree());
			ps.setString(3, cc.getDbText());
			ps.setString(4, cc.getNetText());
			ps.executeUpdate();
			DBUtil.close(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed:insert");
		}
		DBUtil.close(con);
		//console print 
		System.out.println("END<");		
	}

	@Override
	public String getNetText(String word) {
		String net_text="";
		String sql="select net_text from "+baike+" where word like '%"+word+"%'";
		Connection con=DBUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				net_text=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed:load");
		}
		DBUtil.close(rs);
		DBUtil.close(ps);
		DBUtil.close(con);
		//console print 
		System.out.println(">> export net_text. ");
		return net_text;
	}

	@Override
	public void addTypeSet(String word, String keyword, String mohu, String type) {
		String sql="insert into " +table2+ "(word,keyword,mohu,type) values(?,?,?,?)";
		Connection con=DBUtil.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, word);
			ps.setString(2, keyword);
			ps.setString(3, mohu);
			ps.setString(4, type);
			ps.executeUpdate();
			DBUtil.close(ps);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed:insert");
		}
		DBUtil.close(con);
		//console print 
		System.out.println("END<");		
	}

}
