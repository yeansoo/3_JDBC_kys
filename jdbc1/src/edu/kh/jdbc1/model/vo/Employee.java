package edu.kh.jdbc1.model.vo;



public class Employee {
	private String empName;
	private String jobName;
	private int salary;
	private int annualIncome; // 연봉(연간 수입
	// 이름, 직급명, 급여, 연봉 출력
	
	
	private String hireDate; // 조회되는 입사일의 데이터 타입이 문자열
	private char gender; // DB에는 문자 하나를 나타내는 자료형이 없으므로
	// 어떻게 처리해야될지 생각이 필요
	
	public String getEmpName() {
		return empName;
	}
	public  Employee() {}
	public Employee(String empName, String jobName, int salary, int annualIncome) {
		super();
		this.empName = empName;
		this.jobName = jobName;
		this.salary = salary;
		this.annualIncome = annualIncome;
	}
	
	public Employee(String empName, String hireDate, char gender) {
		this.empName=empName;
		this.hireDate=hireDate;
		this.gender=gender;
	}
	
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}
	
	@Override
	public String toString() {
		//return empName+" / "+jobName +" / "+salary +" / "+annualIncome;
		return empName+" / "+hireDate +" / "+gender;
	}
	
}
