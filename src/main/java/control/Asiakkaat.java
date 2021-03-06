package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import model.Asiakas;
import model.dao.Dao;

/**
 * Servlet implementation class asiakkaat
 */
@WebServlet("/asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Asiakkaat() {
        super();
        System.out.println("Asiakkaat.Asiakkaat()");
        
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		String pathInfo = request.getPathInfo();	//get search path info			
		System.out.println("search: "+pathInfo);	
		Dao dao = new Dao();
		ArrayList<Asiakas> dao_asiakkaat;
		try {
			String strJSON = "";
			if(pathInfo==null) { //Haetaan kaikki autot 
				dao_asiakkaat = dao.listAll();
				strJSON = new JSONObject().put("asiakkaat", dao_asiakkaat).toString();	
			} else if (pathInfo.indexOf("getone")!=-1){
				String searchword = pathInfo.replace("/", "");
				String customerid = pathInfo.replace("/getone/", ""); //poistetaan polusta "/haeyksi/", j�ljelle j�� rekno		
				Asiakas asiakas = dao.etsiAsiakas(customerid);
				JSONObject JSON = new JSONObject();
				JSON.put("asiakasid", asiakas.getAsiakasnro());
				JSON.put("etun", asiakas.getEtunimi());
				JSON.put("sukun", asiakas.getSukunimi());
				JSON.put("puhelin", asiakas.getPuhelin());
				JSON.put("sposti", asiakas.getSposti());	
				strJSON = JSON.toString();
			} else {
				String srcword = pathInfo.replace("/", "");
				dao_asiakkaat = dao.listAll(srcword);
				strJSON = new JSONObject().put("asiakkaat", dao_asiakkaat).toString();
			}
			
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			System.out.println(strJSON);
			out.println(strJSON);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		} 
			

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); //Muutetaan kutsun mukana tuleva json-string json-objektiksi			
		System.out.println(jsonObj);
		Asiakas asiakas = new Asiakas();
		String as_id = jsonObj.getString("asiakasid");
		if (as_id != "") {
			asiakas.setAsiakasnro(jsonObj.getString("asiakasid"));
			asiakas.setEtunimi(jsonObj.getString("etun"));
			asiakas.setSukunimi(jsonObj.getString("sukun"));
			asiakas.setPuhelin(jsonObj.getString("puh"));
			asiakas.setSposti(jsonObj.getString("sposti"));
		} else {
			asiakas.setEtunimi(jsonObj.getString("etun"));
			asiakas.setSukunimi(jsonObj.getString("sukun"));
			asiakas.setPuhelin(jsonObj.getString("puh"));
			asiakas.setSposti(jsonObj.getString("sposti"));
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.lisaaAsiakas(asiakas)){ //metodi palauttaa true/false
			out.println("{\"response\":1}");  //add success {"response":1}
		}else{
			out.println("{\"response\":0}");  //add fail {"response":0}
		}	
		
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); //Muutetaan kutsun mukana tuleva json-string json-objektiksi			
		System.out.println(jsonObj);
		String vanhaasiakasid = jsonObj.getString("vanhaasiakasid");
		Asiakas asiakas = new Asiakas();
		asiakas.setAsiakasnro(jsonObj.getString("asiakasid"));
		asiakas.setEtunimi(jsonObj.getString("etun"));
		asiakas.setSukunimi(jsonObj.getString("sukun"));
		asiakas.setPuhelin(jsonObj.getString("puh"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		System.out.println(asiakas);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.muutaAsiakas(asiakas, vanhaasiakasid)){ //metodi palauttaa true/false
			out.println("{\"response\":1}");  //Auton muuttaminen onnistui {"response":1}
		}else{
			out.println("{\"response\":0}");  //Auton muuttaminen ep�onnistui {"response":0}
		}	
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo();	//get search path info			
		System.out.println("search: "+pathInfo);
		String del_customer_id = pathInfo.replace("/", "");
		Dao dao = new Dao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();			
		if(dao.poistaAsiakas(del_customer_id)){ //metodi palauttaa true/false
			out.println("{\"response\":1}");  //delete success {"response":1}
		}else{
			out.println("{\"response\":0}");  //delete fail {"response":0}
		}
	}

}
