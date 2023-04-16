package in.co.online.crime.Ctl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.online.crime.Bean.BaseBean;
import in.co.online.crime.Bean.CrimeBean;
import in.co.online.crime.Bean.FirBean;
import in.co.online.crime.Exception.ApplicationException;
import in.co.online.crime.Exception.DuplicateRecordException;
import in.co.online.crime.Model.CrimeModel;
import in.co.online.crime.Model.FirModel;
import in.co.online.crime.Utility.DataUtility;
import in.co.online.crime.Utility.DataValidator;
import in.co.online.crime.Utility.PropertyReader;
import in.co.online.crime.Utility.ServletUtility;

/**
 * Servlet implementation class CrimeCtl
 */
@WebServlet(name = "CrimeCtl", urlPatterns = "/crime")
public class CrimeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_SAVE = "Save";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrimeCtl() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("in validation");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("crime_name"))) {
			request.setAttribute("crime_name", PropertyReader.getvalue("error.require", "Crime Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getvalue("error.require", "Description"));
			pass = false;
		}
	return pass;
}
    
    protected BaseBean populateBean(HttpServletRequest request) {

		CrimeBean bean = new CrimeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCrime_name(DataUtility.getString(request.getParameter("crime_name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		populateDTO(bean, request);
		return bean;

	}
	
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in do post");

		CrimeModel model = new CrimeModel();
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		CrimeBean bean = new CrimeBean();
		if (OP_SAVE.equalsIgnoreCase(op)) {
			bean = (CrimeBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setbean(bean, request);
				ServletUtility.setSuccessMessage("Crime Successfully Add", request);
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (Exception e) {

				e.printStackTrace();
			}
			ServletUtility.forward(getView(), request, response);

		}
	}

	@Override
	protected String getView() {
		return OCRView.CRIME_VIEW;
	}

}
