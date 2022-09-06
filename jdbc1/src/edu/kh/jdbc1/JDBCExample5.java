package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample5 {
	public static void main(String[] args) {
		// 입사일을 입력받아("2022-09-06")
		// 입력받은 값 보다 먼저 입사한 사람의
		// 이름, 입사일(1990년 01월 01일) , 성별(M,F) 조회
		
		Scanner sc=new Scanner(System.in);
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String user="kh_kys";
			String pw="kh1234";
			
			System.out.print("입사일 (YYYY-MM-DD): ");
			String hireInput=sc.next();
			
			conn=DriverManager.getConnection(url,user,pw);
			
			
					
			stmt=conn.createStatement();
			String sql="SELECT EMP_NAME 이름, \r\n"
					+ "TO_CHAR(HIRE_DATE,'YYYY\"년\" MM\"월\" DD\"일\"') 입사일 , \r\n"
					+ "DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F') 성별 \r\n"
					+ "FROM EMPLOYEE \r\n"
					+ "WHERE HIRE_DATE < TO_DATE('"+hireInput+"')";
			
			//문자열 내부에 쌍따옴표 작성시 \"로 작성해야 한다!(Escape 문자)
			
			rs=stmt.executeQuery(sql);
			List<Employee> empList=new ArrayList<>();
			while(rs.next()) {
//				String empName=rs.getString("이름");
//				String hireDate=rs.getString("입사일");
//				String temp=rs.getString("성별");
//				char gender=temp.charAt(0);
				
				Employee emp=new Employee();// 기본 생성자로 Employee 객체 생성
				// 필드 초기화 X
				// setter를 초기화해서 하나씩 세팅
				
				emp.setEmpName(rs.getString("이름"));// 조회시 컬럼명이 "이름"
				
				
				emp.setHireDate(rs.getString("입사일"));
				// 현재행의 "입사일"컬럼에서 문자열의 값을 얻어와
				// emp가 참조하는 객체의 hireDate 필드에 세팅
				
				emp.setGender(rs.getString("성별").charAt(0));
				// -> char 자료형 매개변수가 필요
				// * 중요 *
				// Java의 char : 문자 1개 의미
				// DB의 CHAR : 고정길이의 문자열(==String)
				// DB 컬럼 값을 char 자료형에 저장하고 싶으면
				// String.charAt(index) 이용!
				
				empList.add(emp);
				
				//empList.add(new Employee(empName, hireDate, gender));
				
				//System.out.println(empName+" / "+hireDate+" / "+gender);
			}
			
			
			if(empList.isEmpty()) {
				System.out.println("조회결과 없음");
			}else {
//				for(Employee e:empList) {
//					System.out.println(e);
//				}
				
				for(int i=0;i<empList.size();i++)
					System.out.printf("%02d) %s %s %c\n",
						 i+1, empList.get(i).getEmpName(),
						 empList.get(i).getHireDate(),
						 empList.get(i).getGender());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				
			}
		}
	}
}
