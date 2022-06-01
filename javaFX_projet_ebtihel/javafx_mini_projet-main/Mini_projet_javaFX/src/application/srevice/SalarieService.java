package application.srevice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.connexion.connexion;
import application.enteties.employe;
import application.enteties.salarie;
import application.enteties.vendeur;

public class SalarieService {

	

	
	
	
	public List<salarie> anciennete() {
		List<salarie> Ls = new ArrayList<salarie>();
		try {
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM salarie ORDER BY  anneRecruit ASC");
			while (res.next()) {
				if (res.getString("categorie").equals("employe")) {
					EmployeService es = new EmployeService();
					employe emp = es.findEmployeById(res.getInt("matricule"));
					Ls.add(new salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
							res.getString("categorie"), res.getDouble("anneRecruit"),
							res.getDouble("salaire") , 1));
				}else
					{
						VendeurService vs = new VendeurService();
						vendeur vend = vs.findVendeurById(res.getInt("matricule"));
						Ls.add(new salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
								res.getString("categorie"), res.getDouble("anneRecruit"),
								res.getDouble("salaire"), 1));
					}
			}
			return Ls;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public double minSalaire() {
		try {
			EmployeService es = new EmployeService();
			VendeurService vs = new VendeurService();
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM salarie where salaire=(select MIN(salaire) as ms from salarie) ");
			res.next();
			return res.getDouble("salaire");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public List<salarie> salFaible() {
		double minSal=minSalaire();
		List<salarie> Ls = new ArrayList<salarie>();

		try {
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM salarie ");
			while (res.next()) {
				if (res.getString("categorie").equals("employe")) {
					EmployeService es = new EmployeService();
					employe emp = es.findEmployeById(res.getInt("matricule"));
					double sal=res.getDouble("salaire");
					if(sal==minSal) {
					Ls.add(new salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
							res.getString("categorie"), res.getDouble("anneRecruit"),
							res.getDouble("salaire") + emp.getHsupp() * emp.getPHsupp(),1));
					}
				}else {
						VendeurService vs = new VendeurService();
						vendeur vend = vs.findVendeurById(res.getInt("matricule"));
						double sal=res.getDouble("salaire");
						if(sal==minSal) {
						Ls.add(new salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
								res.getString("categorie"), res.getDouble("anneRecruit"),
								res.getDouble("salaire") + vend.getVente() * vend.getPoucentage(),1));
						}
					}
			}
			return Ls;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
