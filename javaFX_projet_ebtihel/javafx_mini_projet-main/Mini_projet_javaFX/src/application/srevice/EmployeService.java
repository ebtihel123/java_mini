package application.srevice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.connexion.connexion;
import application.dao.IDaoEmploye;
import application.enteties.employe;
import application.enteties.vendeur;

public class EmployeService implements IDaoEmploye{

	@Override
	public boolean createEmploye(employe e) {
		try {
			if(e.getRecruitDate()<2005)
				e.setSalaireFix(400);
			else
				e.setSalaireFix(280);
			
			Statement st =connexion.getCon().createStatement();
			
			int res1=st.executeUpdate("INSERT INTO salarie VALUES ('"+e.getMatricule()+"','"+e.getNom()+"','"+e.getEmail()+"','"+e.getRecruitDate()+"','"+e.getSalaireFix()+"',"+1+",'"+e.getCat()+"')");
			int res2=st.executeUpdate("INSERT INTO employee VALUES('"+e.getMatricule()+"','"+e.getHsupp()+"','"+e.getPHsupp()+"')");


			if(res1==1 && res2==1) {
				
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}

	
	@Override
	public boolean deleteEmploye(employe e) {
		try {
			Statement st1 =connexion.getCon().createStatement();
			Statement st2 =connexion.getCon().createStatement();

			int res=st1.executeUpdate("DELETE  FROM salarie WHERE matricule=?"+e.getMatricule());
			
			int res1=st2.executeUpdate("DELETE  FROM employee WHERE matricule=?"+ e.getMatricule());
			
		
			
			if(res != 0 && res1 != 0) {
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateEmploye(employe e) {
		try {
			PreparedStatement pst=connexion.getCon().prepareStatement("UPDATE salarie SET nom=?, email=?, categorie=?, anneRecruit=?, salaire=?,idEntreprise=? WHERE matricule=?");
			pst.setString(1,e.getNom());
			pst.setString(2, e.getEmail());
			pst.setString(3,e.getCat());
			pst.setDouble(4, e.getRecruitDate());
			pst.setDouble(5, e.getSalaireFix());
			pst.setInt(6,1);
			pst.setInt(7, e.getMatricule());
			
			PreparedStatement pst1=connexion.getCon().prepareStatement("UPDATE employee SET HSupp=?, PHSupp=? WHERE matricule=?");
			pst1.setDouble(1, e.getHsupp());
			pst1.setDouble(2,e.getPHsupp());
			pst1.setInt(3, e.getMatricule());
			
			int res=pst.executeUpdate();
			int res1=pst1.executeUpdate();
			
			if(res!=0 && res1!=0) {
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}

	@Override
	public employe findEmployeById(int mat) {
		try {
			Statement st =connexion.getCon().createStatement();
			Statement st1 =connexion.getCon().createStatement();

			ResultSet res=st.executeQuery("SELECT * FROM salarie WHERE matricule="+mat);
			ResultSet res1=st1.executeQuery("SELECT * FROM employee WHERE matricule="+mat);

			if(res.next() && res1.next()) {
				return new employe(res.getInt("matricule"),res.getString("nom"),res.getString("email"),res.getString("categorie"),res.getDouble("anneRecruit"),1,res.getDouble("salaire")+res1.getDouble("HSupp")*res1.getDouble("PHSupp"),res1.getDouble("HSupp"),res1.getDouble("PHSupp"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<employe> findAll() {
		try {
			List<employe> listE=new ArrayList<employe>();
			Statement st =connexion.getCon().createStatement();
			ResultSet res=st.executeQuery("SELECT * FROM employee ");

			while(res.next()) {
				listE.add(findEmployeById(res.getInt("matricule")));
			}
			return listE;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
	}

		
		
}


	}