package kr.co.dhflour.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.dhflour.mysite.vo.UserVo;

public class UserDao {
	public boolean update( UserVo vo ) {
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			if( vo.getPassword() == null || 
				"".equals(vo.getPassword()) ) {
				
				String sql = 
						" update users" + 
						"   set name=?, gender=?" + 
						" where no=?";
				pstmt = conn.prepareStatement(sql);				
				pstmt.setString( 1, vo.getName() );
				pstmt.setString( 2, vo.getGender() );
				pstmt.setLong( 3, vo.getNo() );
			} else {
				String sql = 
						" update users" + 
						"   set name=?, gender=?, password=?" + 
						" where no=?";
				pstmt = conn.prepareStatement(sql);				
				pstmt.setString( 1, vo.getName() );
				pstmt.setString( 2, vo.getGender() );
				pstmt.setString( 3, vo.getPassword() );
				pstmt.setLong( 4, vo.getNo() );			
				
			}
		
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
	
	public UserVo fetch(long no) {
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = 
				"select no, name, email, gender" + 
				"  from users" + 
				" where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong( 1, no );
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				result = new UserVo();
				result.setNo( rs.getLong(1) );
				result.setName( rs.getString(2) );
				result.setEmail( rs.getString(3) );
				result.setGender( rs.getString(4) );
			}
			
		} catch( SQLException e ) {
			System.out.println( "에러:" + e);
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
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
	
	public UserVo fetch(UserVo vo) {
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = 
				"select no, name" + 
				"  from users" + 
				" where email=?" + 
				"   and password=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, vo.getEmail() );
			pstmt.setString( 2, vo.getPassword() );
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}
			
		} catch( SQLException e ) {
			System.out.println( "에러:" + e);
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
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
	
	public boolean insert(UserVo vo) {
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = 
				" insert" + 
				"   into users" + 
				" values (seq_users.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, vo.getName() );
			pstmt.setString( 2, vo.getEmail() );
			pstmt.setString( 3, vo.getPassword() );
			pstmt.setString( 4, vo.getGender() );
			
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
}
