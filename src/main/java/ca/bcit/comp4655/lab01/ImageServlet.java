package ca.bcit.comp4655.lab01;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

@WebServlet("/ImageServlet")
@ServletSecurity(@HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL))
public class ImageServlet extends HttpServlet{

	private static final long serialVersionUID = -4513882485288772128L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String pathToImage = req.getParameter("path");
		if ( null!=pathToImage ) {
			final PushBuilder pushBuilder = ((HttpServletRequest)req).newPushBuilder();
			pushBuilder.path(pathToImage).push();
		}
		
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}
}
