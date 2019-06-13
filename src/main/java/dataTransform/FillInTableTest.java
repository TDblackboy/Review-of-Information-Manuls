package dataTransform;

import java.io.IOException;

public class FillInTableTest {

	public static void main(String[] args) {
		//MakeChaChongResult.makeCheckTable();
		try {
			MakeClassData.fillInTypeSetTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
