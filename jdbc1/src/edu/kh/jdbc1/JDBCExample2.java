package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		 // 1단계 : JDBC 객체 참조 변수 생성(java.sql)
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			// 2단계 : 참조변수에 알맞은 객체 대입
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type="jdbc:oracle:thin:@";
			String ip="localhost"; 
			
			String port=":1521"; 
			
			
			String sid=":XE";
			
			String user="kh_kys";
			
			String pw="kh1234";
			
			conn = DriverManager.getConnection(type+ip+port+sid,user,pw);
			
			System.out.print("입력 받은 급여보다 많이 받는(초과) 직원만 조회 > ");
			int input=sc.nextInt();
			
			String sql ="SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE WHERE SALARY>"+input;
			
			stmt=conn.createStatement();
			
			rs  = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				String empId=rs.getString("EMP_ID");
				
				String empName=rs.getString("EMP_NAME");
				
				int salary=rs.getInt("SALARY");
				
				Date hireDate=rs.getDate("HIRE_DATE");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n",empId, empName,salary,hireDate.toString());
				
			}
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
			
			}finally {
				
			}
			
		}
	}
}
