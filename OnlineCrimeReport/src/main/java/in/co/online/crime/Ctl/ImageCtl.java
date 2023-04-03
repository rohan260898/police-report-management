package in.co.online.crime.Ctl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.online.crime.Utility.JDBCDataSource;

/**
 * Servlet implementation class ImageCtl
 */
@WebServlet(name = "ImageCtl",urlPatterns = "/image")
public class ImageCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageCtl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  response.setContentType("image/jpeg");
		  int id = Integer.parseInt(request.getParameter("id"));
		  System.out.println("id:"+id);
		  Connection conn;
		try {
			conn = JDBCDataSource.getConnection();
			 String sql = "SELECT * FROM crimnalinfo WHERE ID ='"+id+"'";
			  PreparedStatement ps;
			  ps = conn.prepareStatement(sql);
			   ResultSet rs = ps.executeQuery();
			   if(rs.next()){
			    byte [] imageData = rs.getBytes("image"); // extract byte data from the resultset..
			    OutputStream os = response.getOutputStream(); // output with the help of outputStream 
			             os.write(imageData);
			             os.flush();
			             os.close();
			             
			             
			   }
			   
			   }catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected String getView() {
		return null;
	}

}
