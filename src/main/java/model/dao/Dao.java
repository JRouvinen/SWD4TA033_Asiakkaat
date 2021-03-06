package model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Asiakas;

public class Dao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="/Asiakkaat/Myynti.sqlite"; //Database
	
	
	//Create connection
	private Connection connect() throws SQLException{
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); 
    	String url = "jdbc:sqlite:"+path+db;    //Tietokannan ajuri tulee olla webapp-WEB-INF-lib-kansiossa	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("DAO - DB Connection opened.");
	     }catch (Exception e){
	    	 connForceClose();
	    	 System.out.println("DAO - DB Connection open failed.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<Asiakas> listAll() throws SQLException{
		
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		sql = "SELECT * FROM asiakkaat";       
		
			con=connect();
			if(con!=null){ //if connection is established
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //if query is succesfull
					System.out.println("DAO - listAll");
					while(rs.next()){
						Asiakas asiakas = new Asiakas();
						asiakas.setAsiakasnro(rs.getString(1));
						asiakas.setEtunimi(rs.getString(2));
						asiakas.setSukunimi(rs.getString(3));
						asiakas.setPuhelin(rs.getString(4));
						asiakas.setSposti(rs.getString(5));
						asiakkaat.add(asiakas);
					}					
				}				
			}	
			con.close();
	        System.out.println("DAO - DB Connection closed.");

				
		return asiakkaat;
	}
	
	public ArrayList<Asiakas> listAll(String search) throws SQLException{
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		sql = "SELECT * FROM asiakkaat WHERE asiakas_id LIKE ? or etunimi LIKE ? or sukunimi LIKE ? "
				+ "or puhelin LIKE ? or sposti LIKE ?";       
		
			con=connect();
			if(con!=null){ //if connection is established
				System.out.println("DAO - listSearch");
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + search + "%");
				stmtPrep.setString(2, "%" + search + "%");   
				stmtPrep.setString(3, "%" + search + "%");
				stmtPrep.setString(4, "%" + search + "%");
				stmtPrep.setString(5, "%" + search + "%");
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //if query is succesfull
					//con.close();					
					while(rs.next()){
						Asiakas asiakas = new Asiakas();
						asiakas.setAsiakasnro(rs.getString(1));
						asiakas.setEtunimi(rs.getString(2));
						asiakas.setSukunimi(rs.getString(3));
						asiakas.setPuhelin(rs.getString(4));
						asiakas.setSposti(rs.getString(5));
						asiakkaat.add(asiakas);
					}					
				}				
			}	
			con.close();
	        System.out.println("DAO - DB Connection closed.");

				
		return asiakkaat;
	}
	
	public boolean lisaaAsiakas(Asiakas asiakas){
		boolean returnVal=true;
		sql="INSERT INTO asiakkaat VALUES(?,?,?,?,?)";						  
		try {
			con = connect();
			stmtPrep=con.prepareStatement(sql);
			stmtPrep.setString(1, asiakas.getAsiakasnro());
			stmtPrep.setString(2, asiakas.getEtunimi());
			stmtPrep.setString(3, asiakas.getSukunimi());
			stmtPrep.setString(4, asiakas.getPuhelin());
			stmtPrep.setString(5, asiakas.getSposti());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			returnVal=false;
		}				
		return returnVal;
	}
	
	public boolean poistaAsiakas(String asiakasnro){ 
		boolean returnVal=true;
		sql="DELETE FROM asiakkaat WHERE asiakas_id=?";						  
		try {
			con = connect();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, asiakasnro);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			returnVal=false;
		}				
		return returnVal;
	}	
	
	
	public Asiakas etsiAsiakas(String asiakasnro) {
		Asiakas asiakas = new Asiakas();
		System.out.println(asiakasnro);
		sql = "SELECT * FROM asiakkaat WHERE asiakas_id=?";
		try {
			con=connect();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, asiakasnro);
        		rs = stmtPrep.executeQuery(); 
        		if(rs.isBeforeFirst()){ 
        			rs.next();
        			asiakas.setAsiakasnro(rs.getString(1));
        			asiakas.setEtunimi(rs.getString(2));
        			asiakas.setSukunimi(rs.getString(3));
        			asiakas.setPuhelin(rs.getString(4));
        			asiakas.setSposti(rs.getString(5));
        			
					     			      			
				}        		
			}	
			con.close();  
		} catch (Exception e) {
			e.printStackTrace();
			asiakas.setAsiakasnro(null);
			asiakas.setEtunimi(null);
			asiakas.setSukunimi(null);
			asiakas.setPuhelin(null);
			asiakas.setSposti(null);
			return asiakas;
			
		}		
		return asiakas;		
	}
	
	public boolean muutaAsiakas(Asiakas asiakas, String asiakasid){
		boolean returnVal=true;
		sql="UPDATE asiakkaat SET asiakas_id=?, etunimi=?, sukunimi=?, puhelin=?, sposti=? WHERE asiakas_id=?";						  
		try {
			con = connect();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, asiakas.getAsiakasnro());
			stmtPrep.setString(2, asiakas.getEtunimi());
			stmtPrep.setString(3, asiakas.getSukunimi());
			stmtPrep.setString(4, asiakas.getPuhelin());
			stmtPrep.setString(5, asiakas.getSposti());
			stmtPrep.setString(6, asiakasid);
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			returnVal=false;
		}				
		return returnVal;
	}
	
	private void connForceClose() throws SQLException {
		con.close();
        System.out.println("DAO - DB Connection closed - connForceClose.");

	}
	
	
}
