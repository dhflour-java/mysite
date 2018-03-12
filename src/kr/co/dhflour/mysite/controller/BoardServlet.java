package kr.co.dhflour.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.dhflour.mysite.dao.BoardDao;
import kr.co.dhflour.mysite.vo.BoardVo;
import kr.co.dhflour.mysite.vo.UserVo;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		
		String actionName = request.getParameter( "a" );
		if( "writeform".equals( actionName ) ) {
			// is auth?
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			
			request.
			getRequestDispatcher( "/WEB-INF/views/board/write.jsp" ).
			forward( request, response );
		
		} else if( "write".equals( actionName ) ) {
			// is auth?
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			
			String title = request.getParameter( "title" );
			String contents = request.getParameter( "content" );
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo( authUser.getNo() );	

			new BoardDao().insert(vo);

			response.sendRedirect( "/mysite/board" );
			
		} else if( "view".equals( actionName ) ) {
			long no = Long.parseLong( request.getParameter( "no" ) );
			
			BoardDao dao = new BoardDao();
			BoardVo boardVo = dao.get( no );

			if( boardVo == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			
			// view count 증가
			dao.updateHit( no );
			
			request.setAttribute( "boardVo", boardVo );
	
			request.
			getRequestDispatcher( "/WEB-INF/views/board/view.jsp" ).
			forward( request, response );
			
		} else if( "replyform".equals( actionName ) ) {
			// is auth?
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/board"  );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/board"  );
				return;
			}
			
			long no = Long.parseLong( request.getParameter( "no" ) );
			BoardDao dao = new BoardDao();
			BoardVo boardVo = dao.get( no );
			
			request.setAttribute( "boardVo", boardVo );
			
			request.
			getRequestDispatcher( "/WEB-INF/views/board/reply.jsp" ).
			forward( request, response );
			
		} else if( "reply".equals( actionName ) ) {
			// is auth?
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/board"  );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/board"  );
				return;
			}
			
			String title = request.getParameter( "title" );
			String contents = request.getParameter( "content" );
			int groupNo = Integer.parseInt( request.getParameter( "gno" ) );
			int orderNo = Integer.parseInt( request.getParameter( "ono" ) );
			int depth = Integer.parseInt( request.getParameter( "d" ) );

			BoardDao dao = new BoardDao();
			BoardVo vo = new BoardVo();
		
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo( authUser.getNo() );
			
			// 같은 그룹의 orderNo 보다 큰 글 들의 order_no 1씩 증가
			orderNo = orderNo + 1;
			depth = depth + 1;
			
			dao.increaseGroupOrder( groupNo, orderNo );
			
			vo.setGroupNo( groupNo );
			vo.setOrderNo( orderNo );
			vo.setDepth( depth );
			
			dao.insert2(vo);
			
			response.sendRedirect( "/mysite/board" );
			
		} else if( "delete".equals( actionName ) ) {
			// is auth?
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}	
			
			long no = Long.parseLong( request.getParameter( "no" ) );	
			
			new BoardDao().delete( no, authUser.getNo() );
			response.sendRedirect( "/mysite/board" );	
			
		} else if( "modifyform".equals( actionName ) ) {
			// is auth?
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			
			long no = Long.parseLong( request.getParameter("no") );
			
			BoardDao dao = new BoardDao();
			BoardVo boardVo = dao.get(no);

			request.setAttribute( "boardVo", boardVo );	
			request.
			getRequestDispatcher( "/WEB-INF/views/board/modify.jsp" ).
			forward( request, response );
			
		} else if( "modify".equals( actionName ) ) {
			// is auth?
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/board" );
				return;
			}
		
			long no = Long.parseLong(request.getParameter("no") );
			String title = request.getParameter("title");
			String contents = request.getParameter("content");
		
			BoardVo vo = new BoardVo();
			vo.setNo(no);
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo( authUser.getNo() );

			new BoardDao().update(vo);
			response.sendRedirect( "/mysite/board?a=view&no=" + no );
			
		} else {
			BoardDao dao = new BoardDao();
			List<BoardVo> list = dao.getList();
			
			request.setAttribute( "list", list );
			
			request.
			getRequestDispatcher( "/WEB-INF/views/board/list.jsp" ).
			forward( request, response );
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
