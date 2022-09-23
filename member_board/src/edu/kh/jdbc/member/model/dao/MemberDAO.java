package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

public class MemberDAO {
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MemberDAO() {
		try{
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("member-query.xml"));
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 회원 목록 조회 DAO
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Member> selectAll(Connection conn) throws Exception{
		// 결과 저장용 변수 선언 
		List<Member> memberList=new ArrayList<>();
		try {
			String sql=prop.getProperty("selectall");
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				String memberName=rs.getString("MEMBER_NM");
				String memberId=rs.getString("MEMBER_ID");
				String memberGender=rs.getString("MEMBER_GENDER");
				
				Member member=new Member();
				
				member.setMemberId(memberId);
				member.setMemberName(memberName);
				member.setMemberGender(memberGender);
				
				memberList.add(member);
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		return memberList;
	}

	/** 회원정보 수정 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Connection conn, Member member) throws Exception{
		
		int result=0;
		
		try {
			String sql=prop.getProperty("updateMember");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getMemberGender());
			pstmt.setInt(3, member.getMemberNo());
			
			result=pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updatePw(Connection conn, String currentPw, String newPw1, int memberNo) throws Exception{
		int result=0;
		
		try {
			String sql=prop.getProperty("updatePw");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(3, currentPw);
			pstmt.setString(1,newPw1);
			pstmt.setInt(2, memberNo);
			
			result=pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	
	
	

	public int secession(Connection conn, String memberPw, int memberNo) throws Exception {
		int result=0;
		
		try {
			String sql=prop.getProperty("secession");
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(2, memberPw);
			pstmt.setInt(1, memberNo);
			
			result=pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	
}
