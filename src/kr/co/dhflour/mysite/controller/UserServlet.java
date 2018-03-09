package kr.co.dhflour.mysite.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.dhflour.mysite.dao.UserDao;
import kr.co.dhflour.mysite.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		
		String actionName = request.getParameter( "a" );
		if("joinform".equals(actionName)) {
			RequestDispatcher rd = 
					request.getRequestDispatcher( "/WEB-INF/views/user/joinform.jsp" );
			rd.forward( request, response );
		} else if("join".equals(actionName)) {
			String name = request.getParameter( "name" );
			String email = request.getParameter( "email" );
			String password = request.getParameter( "password" );
			String gender = request.getParameter( "gender" );
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			
			UserDao dao = new UserDao();
			dao.insert(vo);
			
			response.sendRedirect( "/mysite/user?a=joinsuccess" );
			
		} else if("joinsuccess".equals(actionName)) {
			RequestDispatcher rd = 
					request.getRequestDispatcher( "/WEB-INF/views/user/joinsuccess.jsp" );
			rd.forward( request, response );
		} else if("loginform".equals(actionName)) {
			RequestDispatcher rd = 
				request.getRequestDispatcher( "/WEB-INF/views/user/loginform.jsp" );
			rd.forward( request, response );
		} else if("login".equals(actionName)) {
			String email = request.getParameter( "email" );
			String password = request.getParameter( "password" );
			
			UserVo vo = new UserVo();
			vo.setEmail(email);
			vo.setPassword(password);
			
			UserDao dao = new UserDao();
			UserVo authUser = dao.fetch(vo);
			
			if(authUser == null) {
				response.sendRedirect( "/mysite/user?a=loginform&r=fail" );
				return;
			}
			
			// 로그인 성공(인증 처리)
			HttpSession session = request.getSession( true );
			session.setAttribute("authUser", authUser);
			
			response.sendRedirect( "/mysite/main" );
			
		} else if("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			
			if( session != null ) {
				session.removeAttribute( "authUser" );
				session.invalidate();
			}
			
			response.sendRedirect( "/mysite/main" );
		} else if("modifyform".equals(actionName)){
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/main" );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/main" );
				return;				
			}
			
			long no = authUser.getNo();
			UserDao dao = new UserDao();
			UserVo vo = dao.fetch(no);
			
			request.setAttribute( "userVo", vo );
			RequestDispatcher rd = 
					request.getRequestDispatcher( "/WEB-INF/views/user/modifyform.jsp" );
			rd.forward( request, response );			
		} else if( "modify".equals( actionName )){
			HttpSession session = request.getSession();
			if( session == null ) {
				response.sendRedirect( "/mysite/main" );
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute( "authUser" );
			if( authUser == null ) {
				response.sendRedirect( "/mysite/main" );
				return;				
			}
			
			long no = authUser.getNo();
			String name = request.getParameter( "name" );
			String password = request.getParameter( "password" );
			String gender = request.getParameter( "gender" );
		
			UserVo vo = new UserVo();
			vo.setNo(no);
			vo.setName(name);
			vo.setPassword(password);
			vo.setGender(gender);
			
			UserDao dao = new UserDao();
			dao.update( vo );
			
			response.sendRedirect( "/mysite/user?a=modifyform&r=success" );
		} else {
			/* default request */
			response.sendRedirect( "/mysite/main" );
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
