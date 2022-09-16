package edu.kh.jdbc.model.vo;

/**
 * @author kimyeonsoo
 *
 */
public class TestVO {
	private int testNo;
	private String testTitle;
	private String testContent;
	
	// 기본생성자
	
	public TestVO() {}
	
	// 매개 변수 생성자
	public TestVO(int testNo, String testTitle, String testContent) {
		this.testContent=testContent;
		this.testNo=testNo;
		this.testTitle=testTitle;
	}
	
	// getter/setter
	public void setTestNo(int testNo) {
		this.testNo=testNo;
	}
	public int getTestNo() {
		return testNo;
	}
	
	public void setTestTitle(String testTitle) {
		this.testTitle=testTitle;
	}
	public String getTestTitle() {
		return testTitle;
	}
	
	public void setTestContent(String testContent) {
		this.testContent=testContent;
	}
	public String getTestContent() {
		return testContent;
	}

	
	
	// toString() 오버라이딩 

	@Override
	public String toString() {
		return "TestVo={"+testNo+", "+testTitle+", "+testContent+"}";
	}
	
	
}
