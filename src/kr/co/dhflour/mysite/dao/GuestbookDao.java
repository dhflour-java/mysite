package kr.co.dhflour.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.dhflour.mysite.vo.GuestbookVo;


public class GuestbookDao {
	
	private Connection getConnection() {
		
		Connection conn = null;
		
		try {
			//1. JDBC 드라이버 로딩
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
		
			//2. Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch( ClassNotFoundException e ) {
			System.out.println( "드라이버 로딩 실패 :" + e );
		} catch( SQLException e ) {
			System.out.println( "에러:" + e);
		}
		
		return conn;
	}
	
	public boolean delete( GuestbookVo vo ) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = 
				"delete from guestbook where no=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong( 1, vo.getNo() );
			pstmt.setString( 2, vo.getPassword() );
			
			int count = pstmt.executeUpdate();
			if( count == 1 ) {
				result = true;
			} else {
				result = false;
			}
			
		} catch( SQLException e ) {
			System.out.println( "에러:" + e);
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
	
	public boolean insert( GuestbookVo vo ) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = 
				" insert" + 
				"   into guestbook" + 
				" values(seq_guestbook.nextval, ?, ?, sysdate, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, vo.getName() );
			pstmt.setString( 2, vo.getPassword() );
			pstmt.setString( 3, vo.getContents() );
			
			int count = pstmt.executeUpdate();
			if( count == 1 ) {
				result = true;
			} else {
				result = false;
			}
			
		} catch( SQLException e ) {
			System.out.println( "에러:" + e);
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<GuestbookVo> fetchList() {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			String sql = 
				"   select no," + 
				"          name," + 
				"          to_char(reg_date, 'yyyy-mm-dd hh:mi:ss')," + 
				"          contents" + 
				"     from guestbook" + 
				" order by reg_date desc";
			
			rs = stmt.executeQuery( sql );
			while( rs.next() ) {
				long no = rs.getLong(1);
				String name = rs.getString( 2 );
				String regDate = rs.getString( 3 );
				String contents = rs.getString( 4 );

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setRegDate(regDate);
				vo.setContents(contents);
				
				list.add( vo );
			}
			
		} catch( SQLException e ) {
			System.out.println( "에러:" + e);
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( stmt != null ) {
					stmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
