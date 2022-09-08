package edu.kh.emp.model.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.kh.emp.model.vo.Employee;

// DAO(Data Access Object, 데이터 접근 객체)
// -> 데이터베이스에 접근(연결)하는 객체
// --> JDBC 코드 작성

/**
 * @author raura
 *
 */
public class EmployeeDAO {
	
	// JDBC 객체 참조 변수 필드 선언
	
	private Connection conn; // 필드(Heap, 변수가 비어있을 수 없음)
	private Statement stmt;		// -> JVM이 지정한 기본값으로 초기화 
	private ResultSet rs;		// -> 참조형의 초기값은 null
								// 별도 초기화 안해도 된다. 
	
	
	private PreparedStatement pstmt;
	//Statement의 자식으로 향상된 기능을 제공
	// -> ?기호(placeholder/ 위치 홀더)
	// SQL에 작성되어지는 리터럴을 동적으로 제어함
	
	// SQL ? 기호에 추가되는 값은
	// 숫자인 경우 '' 없이 대입
	// 문자열인경우 ''가 자동으로 추가되어 대입
	
	
	
	private String driver="oracle.jdbc.driver.OracleDriver";
	private String url="jdbc:oracle:thin:@localhost:1521:XE";
	private String user="kh_kys";
	private String pw="kh1234";
	
	
	/** 전체 사원 정보 조회 DAO
	 * @return
	 */
	public List<Employee> selectAll() {
		// 1. 결과 저장용 변수 선언 
		List<Employee> empList=new ArrayList<>();
		
		try {
			// 2. JDBC 참조변수에 객체 대입
			// -> conn, stmt, rs에 객체 대입
			Class.forName(driver); // 오라클 jdbc 드라이버 객체 메모리 로드
			
			conn=DriverManager.getConnection(url, user, pw);
			// 오라클 jdbc 드라이버 객체를 이용하여 DB 접속 방법 생성
			
			String sql="SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,"
					+ " NVL(DEPT_TITLE,'부서없음') DEPT_TITLE, JOB_NAME, SALARY"
					+ "  FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON(DEPT_ID=DEPT_CODE)"
					+ " JOIN JOB USING(JOB_CODE)"
					+ " ORDER BY 1";
			
			// Statement 객체 생성
			stmt=conn.createStatement();
			
			// SQL을 수행 후 결과(ResultSet) 반환 받음
			rs=stmt.executeQuery(sql);
			
			// 3. 조회결과를 얻어와 한 행씩 접근하여
			// Employee 객체 추가 생성 후 컬럼 값 옮겨담기
			// -> List 추가
			while(rs.next()) {
				int empId=rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만
				// 저장된 값들이 모드 숫자형태
				// -> DB에서 자동으로 형변환 진행해서 얻어온다.
				
				String empName=rs.getString("EMP_NAME");
				String empNo=rs.getString("EMP_NO");
				String phone=rs.getString("PHONE");
				String email=rs.getString("EMAIL");
				String deptTitle=rs.getString("DEPT_TITLE");
				String jobName=rs.getString("JOB_NAME");
				int salary=rs.getInt("SALARY");
				
				Employee emp=new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
				
				empList.add(emp);
			}
			

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				// 4. JDBC 객체 자원 반환
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				
			}finally {
				
			}
		}
		
		// 5. 결과 반환
		return empList;
	}


	/** 사번이 일치하는 사원 정보 조회
	 * @param empId
	 * @return
	 */
	public Employee selectEmpId(int empId) {
		
		// 결과 저장용 변수를 선언
		Employee emp=null;
		// 만약 조회 결과가 있으면 Employee 객체를 생성해서 emp에 대입(null이 아님)
		// 만약 조회 결과가 없으면 emp에 아무것도 대입하지 않음(null)
		
		try {
			
			Class.forName(driver);// 오라클 JDBC 드라이버 메모리 로드
			
			conn=DriverManager.getConnection(url,user,pw);// 커넥션 생성해서 얻어오기
			
			// SQL 작성
			String sql="SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, \r\n"
					+ "NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, \r\n"
					+ "JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)"
					+ " WHERE EMP_ID="+empId;
			//							View에서 전달받은 사번
			
			// Statement 생성
			stmt=conn.createStatement();
			
			// SQL 수행 후 결과 반환 받기
			rs=stmt.executeQuery(sql);
			
			// ** 조회 결과가 최대 1행인 경우
			// 불필요한 조건 심사를 줄이기 위해 if문 사용 권장
			if(rs.next()) { // 조회 결과가 있을 경우
				
				//int empId=rs.getInt("EMP_ID"); 파라미터와 같은 값으므로 불필요
				
				String empName=rs.getString("EMP_NAME");
				String empNo=rs.getString("EMP_NO");
				String phone=rs.getString("PHONE");
				String email=rs.getString("EMAIL");
				String deptTitle=rs.getString("DEPT_TITLE");
				String jobName=rs.getString("JOB_NAME");
				int salary=rs.getInt("SALARY");
				
				emp=new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
				 
			}
			
		}catch(Exception e) {
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
		
		return emp;
	}


	public Employee selectEmpNo(String empNo) {
		
		// 결과 저장용 변수
		Employee emp=null;
		
		try {
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			String sql="SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,\r\n"
					+ "NVL(DEPT_TITLE,'부서없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "JOIN DEPARTMENT ON(DEPT_ID=DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE EMP_NO=?";
			// placeholder
			
			// Statement 객체 사용시 순서
			// SQL 작성 -> Statement 생성 -> SQL 수행 후 결과 반환
			
			// PreparedStatement 객체 사용시 순서
			// SQL 작성
			// -> PreparedStatement 객체 생성(?가 포함된 SQL을 매개변수로 사용)
			// -> ?에 알맞은 값 대입
			// -> SQL 수행 후 결과 반환
			
			// PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			// ?에 알맞은 값 대입
			pstmt.setString(1, empNo);
			
			// SQL 수행 후 결과 반환
			rs=pstmt.executeQuery();
			//PreparedStatement는
			// 객체 생성시 이미 SQL이 담겨져 있는 상태이므로
			// SQL 수행시 매개변수로 전달할 필요가 없다
			
			// -> 실수로 SQL을 매개변수에 추가하면
			// ?에 작성 추가했던 값이 모두 사라져 수행시 오류 발생
			
			
			if(rs.next()) { // 조회 결과가 있을 경우
				
				int empId=rs.getInt("EMP_ID"); 
				
				String empName=rs.getString("EMP_NAME");
				//String empNo=rs.getString("EMP_NO");//파라미터와 같은 값으므로 불필요
				String phone=rs.getString("PHONE");
				String email=rs.getString("EMAIL");
				String deptTitle=rs.getString("DEPT_TITLE");
				String jobName=rs.getString("JOB_NAME");
				int salary=rs.getInt("SALARY");
				
				emp=new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
				 
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				
			}finally {
				
			}
		}
		
		
		return emp;
	}


	/** 사원 정보 추가 DAO
	 * @param emp
	 * @return result (INSERT 성공한 행의 개수 반환)
	 */
	public int insertEmployee(Employee emp) {
		// 결과 저장용 변수 선언
		
		int result=0;
		
		try {
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			
			// ** DML 수행할 예정 **
			// - 트랜잭션에 DML 구문이 임시 저장
			// -> 정상적인 DML인지 판별해서 개발자가 직접 commit, rollback을 수행
			
			// 하지만... Connection 객체 생성 시 
			// AutoCommit이 활성화되어 있는 상태이기 때문에
			// 이를 해제하는 코드를 추가!!!!!!!!!!!!!!!!!!!
			
			conn.setAutoCommit(false); // AutoCommit 비활성화
			// -> AutoCommit 비활성화
			// conn.close() 구문이 실행되면 자동으로 Commit이 수행됨
			// --> close() 수행 전에 트랜잭션 제어 코드를 작성해야함!
			
			
			String sql="INSERT INTO EMPLOYEE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL, DEFAULT)";
			// 퇴사여부 컬럼의 DEFAULT == 'N'
			
			// PreparedStatement 객체 생성(매개변수에 SQL 추가)
			
			pstmt=conn.prepareStatement(sql);
			
			// ?(placeholder)에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			 
			// SQL 실행 후 결과 반환받기
			result=pstmt.executeUpdate();
			
			// executeUpdate() : DML(INSERT, UPDATE, DELETE) 수행 후 결과 행 개수 반환
			// executeQuery() : SELECT 수행 후 ResultSet 반환
			
			// *** 트랜잭션 제어 처리 ***
			// -> DML 성공 여부에 따라서 commit, rollback 제어
			if(result>0) conn.commit(); // DML 성공시
			else		conn.rollback();// DML 실패시 rollback;
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
			}
			
		}
		
		
		return result;
	}


	/** 사번이 일치하는 사원 정보 수정 DAO
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Employee emp) {
		int result=0;
		
		try {
			
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			conn.setAutoCommit(false);
			
			String sql="UPDATE EMPLOYEE SET EMAIL=?, PHONE=?, SALARY=? WHERE EMP_ID=?";
			
			// PreparedStatement객체 생성
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result=pstmt.executeUpdate();
			
			if(result==0)conn.rollback();
			else conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
			}
		}
		
		
		return result;
	}


	public int deleteEmployee(int empId) {
		int result=0;
		
try {
			
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			conn.setAutoCommit(false);
			
			String sql="DELETE FROM EMPLOYEE WHERE EMP_ID=?";
			
			// PreparedStatement객체 생성
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			
			result=pstmt.executeUpdate();
			
			if(result>0)conn.commit();
			else conn.rollback();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
			}
		}
		
		
		return result;
	}

	/**
	 * 6. 입력 받은 부서와 일치하는 모든 사원 정보 조회
	 */
	public List<Employee> selectDeptEmp(String dept) {
		List<Employee> empList=new ArrayList<>();
		
		try {
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			conn.setAutoCommit(false);
			
			String sql="SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,\r\n"
					+ "NVL(DEPT_TITLE,'부서없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "JOIN DEPARTMENT ON(DEPT_ID=DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE DEPT_TITLE=?";
			
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dept);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				int empId=rs.getInt("EMP_ID");
				String empName=rs.getString("EMP_NAME");
				String empNo=rs.getString("EMP_NO");
				String email=rs.getString("EMAIL");
				String phone=rs.getString("PHONE");
				
				String jobName=rs.getString("JOB_NAME");
				int salary=rs.getInt("SALARY");
				
				empList.add(new Employee(empId, empName, empNo, email, phone, dept, jobName, salary));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return empList;
	}


	/**
	 * 7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회
	 */
	public List<Employee> selectSalaryEmp(int inputSal) {
		List<Employee> empList=new ArrayList<>();
		
		try {
			
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			String sql="SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE,\r\n"
					+ "NVL(DEPT_TITLE,'부서없음') DEPT_TITLE, JOB_NAME, SALARY\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "JOIN DEPARTMENT ON(DEPT_ID=DEPT_CODE)\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE SALARY>=?"
					+ " ORDER BY SALARY";
					
			
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, inputSal);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				int empId=rs.getInt("EMP_ID");
				String empName=rs.getString("EMP_NAME");
				String empNo=rs.getString("EMP_NO");
				String email=rs.getString("EMAIL");
				String phone=rs.getString("PHONE");
				String dept=rs.getString("DEPT_TITLE");
				String jobName=rs.getString("JOB_NAME");
				int salary=rs.getInt("SALARY");
				
				
				empList.add(new Employee(empId, empName, empNo, email, phone, dept, jobName, salary));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return empList;
	}


	/**
	 * 8. 부서별 급여 합 전체 조회
	 */
	public Map<String, Integer> selectDeptTotalSalary() {
		Map<String, Integer> empMap=new HashMap<>();
		
		try {
			
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			String sql="SELECT DEPT_TITLE, SUM(SALARY) TOTAL_SALARY"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON(DEPT_CODE=DEPT_ID)"
					+ " GROUP BY DEPT_TITLE";
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				String deptTitle=rs.getString("DEPT_TITLE");
				//String sumSal=rs.getString("TOTAL_SALARY");
				int totalSalary=rs.getInt("TOTAL_SALARY");
				empMap.put(deptTitle, totalSalary);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	
		return empMap;
	}


	/**
	 * 10. 직급별 급여 평균 조회
	 */
	public Map<String, Double> selectJobAvgSalary() {
		Map<String, Double> empMap=new HashMap<>();
		
		try {
			
			Class.forName(driver);
			
			conn=DriverManager.getConnection(url,user,pw);
			
			String sql="SELECT JOB_NAME, ROUND(AVG(SALARY),1) AVG_SAL"
					+ " FROM EMPLOYEE"
					+ " JOIN JOB ON(EMPLOYEE.JOB_CODE=JOB.JOB_CODE)"
					+ " GROUP BY JOB_NAME";
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String jobName=rs.getString("JOB_NAME");
				double avgSal=rs.getDouble("AVG_SAL");
				
				empMap.put(jobName, avgSal);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return empMap;
	}
	
	// 지역변수(Stack, 변수가 비어있을 수 있음)
}
