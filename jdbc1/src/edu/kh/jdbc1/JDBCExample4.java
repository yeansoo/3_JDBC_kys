package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// 직급명, 급여를 입력받아
		// 해당 직급에서 입력 받은 급여보다 많이 받는 사원의
		// 이름, 직급명, 급여, 연봉을 조회하여 출력
		
		// 단, 조회 결과가 없으면 "조회결과 없음" 출력
		// 조회결과가 있으면
		// 선동일 / 대표 / 8000000 / 10000000
		
		Scanner sc=new Scanner(System.in);
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			System.out.print("직급명 입력 : ");
			String jobInput=sc.next();
			
			System.out.print("급여 입력 : ");
			int salInput=sc.nextInt();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String type="jdbc:oracle:thin:@";
			String ip="localhost"; 
			String port=":1521"; 
			String sid=":XE";
			String user="kh_kys";
			String pw="kh1234";
			
			conn=DriverManager.getConnection(type+ip+port+sid,user,pw);
			
			String sql="SELECT EMP_NAME, JOB_NAME , SALARY , SALARY*12 ANNUAL_INCOME\r\n"
					+ " FROM EMPLOYEE \r\n"
					+ " JOIN JOB ON(EMPLOYEE.JOB_CODE=JOB.JOB_CODE)\r\n"
					+ " WHERE JOB_NAME = '"+jobInput+"' \r\n"
					+ " AND SALARY>"+salInput;
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			//List<Employee> empList=new ArrayList<Employee>();
			boolean flag=false;
			
			while(rs.next()) {
				flag=true;
				String empName=rs.getString("EMP_NAME");
				String jobName=rs.getString("JOB_NAME");
				int salary=rs.getInt("SALARY");
				int annualIncome=rs.getInt("ANNUAL_INCOME");
				
				System.out.println(empName+" / "+jobName+" / "+salary+" / "+annualIncome);
				
				//empList.add(new Employee(empName, jobName, salary, salary*12));
			}
			
			if(!flag)
				System.out.println("조회결과 없음");
			
//			if(empList.isEmpty()) {
//				System.out.println("조회결과 없음");
//			}else {
//				for(Employee e:empList) {
//					System.out.println(e);
//				}
//			}
			
			
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
