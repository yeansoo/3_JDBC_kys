package edu.kh.jdbc.model.dao;


import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.model.vo.TestVO;


// DAO(Data Access Object) : 데이터가 저장된 DB에 접근하는 객체 
//							-> SQL 수행, 결과 반환 받는 기능을 수행 
public class TestDAO {
	
	// JDBC 객체를 참조하기 위한 참조변수 선언

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// XML로 SQL을 다룰 예정 -> Properties 객체 사용 
	private Properties prop;
	
	// 기본 생성자 
	public TestDAO() {
		try {
			// TestDAO 객체 생성시
			// test-query.xml 파일의 내용을 읽어와
			// Properties 객체에 저장 
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("test-query.xml"));
			
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 1행 삽입 DAO
	 * @param conn
	 * @param vo1
	 * @return result
	 */
	public int insert(Connection conn, TestVO vo1) throws SQLException {
		// -> 호출한 곳으로발생한 SQLException 을던짐
		// -> 예외 처리를 모아서 진행하기 위해 
		
		// 1. 결과 저장용 변수 선언 
		int result=0;
		
		try {
			// 2. SQL 작성(test-query.xml 에 작성된 SQL 얻어오기)
			//				-> prop 이 저장하고 있음
			
			String sql=prop.getProperty("insert");
			
			
			
			// 3. ProparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			// -> throws 예외 처리 사용 
			
			// 4. ?(위치홀더) 에 알맞은 값 세팅 
			pstmt.setInt(1, vo1.getTestNo());
			pstmt.setString(2, vo1.getTestTitle());
			pstmt.setString(3, vo1.getTestContent());
			
			// 5. SQL(insert) 수행 후 결과 반환 받기 
			
			// pstmt.executeQuery(); -> SELECT 수행, ResultSe 반환 
			result=pstmt.executeUpdate();// -> DML 수행, 반영된 행의 개수(int) 반환
			
			
		}finally {
			// 6. 사용한 JDBC 객체 자원 반환(close()) 
			close(pstmt);
			
		}
		
		
		return result;
	}

	public int selup(Connection conn, TestVO vo1) throws SQLException {
		int result=0;
		
		try {
			// 2. SQL 작성(test-query.xml 에 작성된 SQL 얻어오기)
			//				-> prop 이 저장하고 있음
			
			String sql=prop.getProperty("selup");
			
			
			
			// 3. ProparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			// -> throws 예외 처리 사용 
			
			// 4. ?(위치홀더) 에 알맞은 값 세팅 
			pstmt.setInt(3, vo1.getTestNo());
			pstmt.setString(1, vo1.getTestTitle());
			pstmt.setString(2, vo1.getTestContent());
			
			
			
			// 5. SQL(insert) 수행 후 결과 반환 받기 
			
			// pstmt.executeQuery(); -> SELECT 수행, ResultSe 반환 
			result=pstmt.executeUpdate();// -> DML 수행, 반영된 행의 개수(int) 반환
			
			
		}finally {
			// 6. 사용한 JDBC 객체 자원 반환(close()) 
			close(pstmt);
			
		}
		return result;
	}

}
