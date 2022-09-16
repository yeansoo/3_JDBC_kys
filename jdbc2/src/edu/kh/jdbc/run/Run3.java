package edu.kh.jdbc.run;

import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run3 {

	public static void main(String[] args) {
		// 번호, 제목, 내용을 입력받아
	      // 번호가 일치하는 행의 제목, 내용 수정
		
		Scanner sc=new Scanner(System.in);
		
		System.out.print("번호 입력 : ");
		int input1=sc.nextInt();
		
		System.out.print("제목 입력 : ");
		String input2=sc.next();
		
		System.out.print("내용 입력 : ");
		String input3=sc.next();
		
		TestService service=new TestService();
		TestVO vo1=new TestVO(input1,input2,input3);
		try {
			
			
			int result=service.insert(vo1);
			
			if(result>0)System.out.println("수정되었습니다.");
			else System.out.println("일치하는 번호가 없습니다. ");
			
			
		}catch(Exception e) {
			System.out.println("수정 중 예외가 발생했습니다.");
			e.printStackTrace();
			
		}
	      
	      // 수정 성공 시 -> 수정되었습니다.
	      // 수정 실패 시 -> 일치하는 번호가 없습니다.
	      // 예외 발생 시 -> 수정 중 예외가 발생했습니다.
	}

}
