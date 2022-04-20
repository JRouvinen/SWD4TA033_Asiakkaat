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
					//con.close();					
					while(rs.next()){
						Asiakas asiakas = new Asiakas();
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
		sql = "SELECT * FROM asiakkaat WHERE etunimi LIKE ? or sukunimi LIKE ? "
				+ "or puhelin LIKE ? or sposti LIKE ?";       
		
			con=connect();
			if(con!=null){ //if connection is established
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + search + "%");
				stmtPrep.setString(2, "%" + search + "%");   
				stmtPrep.setString(3, "%" + search + "%");
				stmtPrep.setString(4, "%" + search + "%"); 
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //if query is succesfull
					//con.close();					
					while(rs.next()){
						Asiakas asiakas = new Asiakas();
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
	
	private void connForceClose() throws SQLException {
		con.close();
        System.out.println("DAO - DB Connection closed - connForceClose.");

	}
	
	
}
