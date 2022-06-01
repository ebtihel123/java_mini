package application.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import application.enteties.employe;
import application.enteties.vendeur;
import application.srevice.EmployeService;
import application.srevice.VendeurService;


public class connexion {
	static String URL="jdbc:mysql://localhost:3306/javafx";
	static String db_name="root";
	static String db_pwd="";
	public static Connection con  = null;   
    static Statement  stmt  = null;
    
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 con = DriverManager.getConnection(URL, db_name, db_pwd);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getCon() {
		return con;
	}
public static void main(String args[]) {

}
}