package application.dao;

import java.util.List;

import application.enteties.employe;

public interface IDaoEmploye {
boolean createEmploye(employe e);
boolean deleteEmploye(employe e );
boolean updateEmploye(employe e);
employe findEmployeById(int matricule);
List<employe > findAll();
}
