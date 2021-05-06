package step3_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.cj.jdbc.Driver;

public class Dao_test_01 {
	
	//싱글톤 패턴
	/*
	 * dao 생성
	 * static dao 인스턴스 생성자
	 * static dao 겟인스턴스
	 */
	
	private Dao_test_01() { }
	private static Dao_test_01 instance = new Dao_test_01();
	public static Dao_test_01 getInstance() {
		return instance;
	}
	
	//객체 생성
	/*
	 * conn
	 * pstmt
	 * rs
	 * sdf
	 */
	
	private Connection 			conn 	= null;
	private PreparedStatement 	pstmt 	= null;
	private ResultSet 			rs 		= null;
	SimpleDateFormat 			sdf 	= new SimpleDateFormat("yyyy-MM-dd");
	
	// DB 연결
	/*
	 * conn 겟커넥션 
	 * 
	 * try {
	 * 
	 * forname com.mysql.jdbc.Driver
	 * dburl jdbc:mysql://localhost:3306/STEP3_BOARD_EX?serverTimezone=UTC
	 * dbid
	 * dbpw
	 * conn = drivermanager.get (주소, 아이디, 비번 )
	 * }
	 * 
	 * catch { }
	 * 
	 * return
	 */
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dbUrl = "jdbc:mysql://localhost:3306/STEP3_BOARD_EX?serverTimezone=UTC";
			String dbId = "root";
			String dbPw = "1234";
			conn = DriverManager.getConnection(dbUrl, dbId, dbPw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// 글쓰기
	/*
	 * void insertBoard(Dto dto)
	 * try {
	 * 	conn에 연결정보가져오기
	 * 
	 * 변수에 db구문 작성 
	 * INSERT INTO BOARD(WRITE, EMAIL, SUBJECT, PASSWORD, REG_DATE, READ_COUNT, CONTENT
	 * 변수 += 밸류값
	 * pstmt에 conn.pre(변수)
	 * 
	 * pstmt에 세팅 ( 밸류값 ? 만 : string만 )
	 * 
	 * }
	 * 
	 * catch { }
	 * finally { pstmt / conn }
	 * 
	 */
	
	public void insertBoard(Dto_test_1 dto_test_1) {
		
		try {
			
			conn = getConnection();
			
			String sql = "INSERT INTO BOARD(WRITE, EMAIL, SUBJECT, PASSWORD, REG_DATE, READ_COUNT, CONTENT";
			sql += "VALUES(?,?,?,?,NOW(),0,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, g);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
	/*
	 * 전체 조회하기
	 * 어래이리스트<dto> getAllBoard()
	 * 
	 *  arraylist 변수 생성
	 *  dto bdto 변수 생성 = 초기화
	 *  conn에 연결정보 가져오기
	 *  
	 * try{
	 * 		pstmt conn.pre(SELECT * FROM BOARD)
	 *  	rs pstmt.exe
	 *  
	 * 		while(데이터한줄씩){
	 *  		bdto변수를 새로 dto클래스 생성
	 * 			dto변수로 데이터값 집어넣기 rs로
	 *  		리스트에 추가
	 *  	}
	 *  
	 *  }
	 *  catch { }
	 *  finally { close }
	 *  
	 */
	public ArrayList<Dto_test_1> getAllBoard() {
		
		ArrayList<Dto_test_1> boardList = new ArrayList<Dto_test_1>(); // ArrayList생성
		Dto_test_1 bdto = null;	// DTO 변수생성해서 초기화
		conn = getConnection(); // conn 에 연결정보 가져오기
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM BOARD");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				bdto = new Dto_test_1();
				bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString(3));
				bdto.setSubject(rs.getString(4));
				bdto.setPassword(rs.getString(5));
				bdto.setRegDate(sdf.format(rs.getString(6)));
				bdto.setReadCount(rs.getInt(7));
				bdto.setContent(rs.getString(8));
				
				boardList.add(bdto);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if (pstmt!=null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (conn!=null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
		}
		
		
		return boardList;
		
	}
	
	// 게시글 한 개 조회하기
	/*public Dto_test_1 getOneBoard(int num)
	 * dto 변수생성
	 * 
	 * try {
	 * 		
	 * conn에 연결정보
	 * pstmt에 conn. UPDATE BOARD SET READ_COUNT = READ_COUNT + 1 WHERE NUM=?
	 * pstmt에 번호 세팅
	 * pstmt.exeup
	 * 
	 * pstmt conn. SELECT * FROM BOARD WHERE NUM=?
	 * pstmt 번호 세팅
	 * rs = pstmt.exe
	 * 
	 * if(데이터한줄씩) {
	 * 
	 * bdto rs로
	 * 
	 * }
	 * 
	 * }catch { }
	 * finally { }
	 * 
	 * return bdto
	 */
	
	
	public Dto_test_1 getOneBoard(int num) {
		
		Dto_test_1 bdto = new Dto_test_1();
		
		try {
			conn = getConnection();
			
			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_COUNT = READ_COUNT + 1 WHERE NUM=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bdto.setNum(rs.getInt("num"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setEmail(rs.getString("email"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setPassword(rs.getString("password"));
				bdto.setRegDate(sdf.format(rs.getDate("reg_date")));
				bdto.setReadCount(rs.getInt("read_count"));
				bdto.setContent(rs.getString("content"));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
		return bdto;
		
		
	}
	
	
	
	
	
	
	// 수정할 게시글 한 개 조회
	/*public Dto_test_1 getOneBoard(int num)
	 * dto 변수생성
	 * 
	 * try {
	 * 		conn 연결정보가져오기
	 * pstmt conn. SELECT * FROM BOARD WHERE NUM=?
	 * pstmt 번호 세팅
	 * rs = pstmt.exe
	 * 
	 * if(데이터한줄씩) {
	 * 
	 * bdto rs로
	 * 
	 * }
	 * 
	 * }catch { }
	 * finally { }
	 * 
	 * return bdto
	 */
	
	
	public Dto_test_1 getOneupdateBoard(int num) {
		
		Dto_test_1 bdto = new Dto_test_1();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bdto.setNum(rs.getInt("num"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setEmail(rs.getString("email"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setPassword(rs.getString("password"));
				bdto.setRegDate(sdf.format(rs.getDate("reg_date")));
				bdto.setReadCount(rs.getInt("read_count"));
				bdto.setContent(rs.getString("content"));
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		return bdto;
	}
	
	
	// 게시글 수정
	/*
	 * public boolean updateBoard(Dto_test_1 dto_test_1)
	 * 불린형 변수 생성
	 * try{
	 * conn 연결정보
	 * 
	 * pstmt conn SELECT * FROM BOARD WHERE NUM=? AND PASSWORD=?
	 * set 번호, 비밀번호
	 * rs = exe
	 * 
	 * if( 데이터 ) {
	 * 
	 * pstmt conn UPDATE BOARD SET SUBJECT=? , CONTENT=? WHERE NUM=?
	 * pstmt.set 제목, 내용, 번호
	 * pstmt.exeup
	 * 불린변수 = true
	 * }
	 * 
	 * 불린변수 반환
	 * 
	 */
	public boolean updateBoard(Dto_test_1 dto_test_1) {
		
		boolean isUpdate = false;
		
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=? AND PASSWORD=?");
			pstmt.setInt(1, dto_test_1.getNum());
			pstmt.setString(2, dto_test_1.getPassword());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pstmt = conn.prepareStatement("UPDATE BOARD SET SUBJECT=?, CONTENT=? WHERE NUM=?");
				pstmt.setString(1, dto_test_1.getSubject());
				pstmt.setString(2, dto_test_1.getContent());
				pstmt.setInt(3, dto_test_1.getNum());
				pstmt.executeUpdate();
				isUpdate = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return isUpdate;
		
	}
	
	// 게시글 삭제
	/*
	 * 불린변수
	 * 
	 * try{
	 * 
	 * conn 연결정보
	 * pstmt conn SELECT * FROM BOARD WHERE NUM=? AND PASSWORD=?
	 * set 번호 비밀번호
	 * rs pstmt.exe
	 * 
	 * if(데이터 {
	 * pstmt conn DELETE FROM BOARD WHERE NUM=?
	 * set 번호
	 * pstmt.exeup
	 * 불린변수 = true
	 * 
	 * 
	 * 
	 * 불린변수 반환
	 * 
	 */
	
	
	public boolean deleteBoard(Dto_test_1 dto_test_1) {
		
		boolean isDelete = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * BOARD WHERE NUM=? AND PASSWORD");
			pstmt.setInt(1, dto_test_1.getNum());
			pstmt.setString(2, dto_test_1.getPassword());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				
				pstmt = conn.prepareStatement("DELETE FROM BOARD WHERE NUM=?");
				pstmt.setInt(1, dto_test_1.getNum());
				pstmt.executeUpdate();
				isDelete = true;
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		return isDelete;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
