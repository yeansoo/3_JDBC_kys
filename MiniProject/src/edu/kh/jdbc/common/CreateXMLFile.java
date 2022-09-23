package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	
    // XML(eXtensible Markup Language) : 단순화된 데이터 기술 형식
    
    // XML에 저장되는 데이터의 형식은 Key : Value 형식(Map)이다.
    // -> Key, Value 모두 String(문자열) 형식
    // -> Map<String, String>
    
    // XML 파일을 읽고,쓰기 위한 IO 관련 클래스 필요
    
    // * Properties 컬렉션 객체 *
    //  - Map의 후손 클래스
    //  - Key, Value 모두 String(문자열) 형식
    //  - XML 파일을 읽고, 쓰는데 특화된 메서드 제공
	public static void main(String[] args) {
		try {
			Scanner sc=new Scanner(System.in);
			 
			
			Properties prop=new Properties();
			
			System.out.print("생성할 파일 이름 : ");
			String filename=sc.nextLine();
			
			FileOutputStream fos=new FileOutputStream(filename+".xml");
			
			prop.storeToXML(fos, filename+".xml"); 
			
			
		}catch(IOException e) {
			
		}

	}
	
}
