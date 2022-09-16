package edu.kh.jdbc.run;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run2 {
	public static void main(String[] args) {
		
		// TB_TEST테이블에 한 번에 3행 삽입 
		
		TestService service=new TestService();
		
		TestVO vo1=new  TestVO(10,"제목10","내용10입니다.");
		TestVO vo2=new  TestVO(20,"제목20","내용20입니다.");
		TestVO vo3=new  TestVO(30,"제목30","내용30입니다.");
		
		
		try {
			int result=service.insert(vo1,vo2,vo3);
			
			if(result>0)System.out.println("삽입 성공");
			else System.out.println("삽입 실패");
			
		}catch(Exception e) {
			System.out.println("SQL수행 중 오류 발생");
			e.printStackTrace();
		}
	}
}
