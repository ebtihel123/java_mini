package application.salarieController;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.enteties.employe;
import application.enteties.entreprise;
import application.enteties.salarie;
import application.enteties.vendeur;

import application.srevice.EmployeService;
import application.srevice.SalarieService;
import application.srevice.VendeurService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SalarieController implements Initializable {
	@FXML 
	private TextArea tfDetails;
	@FXML
	private ChoiceBox cbEntreprise;
	@FXML
	private RadioButton re;
	@FXML 
	private RadioButton rbLemp;
	@FXML
	private RadioButton rbLvend;
	@FXML
	private RadioButton rv;
	@FXML
	private TextField tfMat;
	@FXML
	private TextField tfNom;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfDateRec;
	
	@FXML
	private TextField tfSalaireMin;
	@FXML
	private TextField tfSalaireMax;
	@FXML
	private TextField tfTHV;
	@FXML
	private TextField tfHVS;
	@FXML
	private Button addBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button exportBtn;
	@FXML
	private Button importBtn;
	@FXML
	private Button removeBtn;
	@FXML
	private Button detailsBtn;
	@FXML
	private Button quitBtn;
	@FXML
	private Button catBtn;
	@FXML
	private Button dateBtn;
	@FXML
	private Button maxTauxBtn;
	@FXML
	private Button minSalaireBtn;
	@FXML
	private Button minMaxBtn;
	@FXML
	private TableView<salarie> table;
	@FXML
	private TableColumn<salarie, Integer> matriculeCol;
	@FXML
	private TableColumn<salarie, String> nomCol;
	@FXML
	private TableColumn<salarie, String> emailCol;
	@FXML
	private TableColumn<salarie, String> catCol;
	@FXML
	private TableColumn<salarie, Double> salaireCol;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		matriculeCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		catCol.setCellValueFactory(new PropertyValueFactory<>("cat"));
		salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaireFix"));
		
		EmployeService es=new EmployeService();
		VendeurService vs=new VendeurService();
		
		
		table.getItems().addAll(es.findAll());
		table.getItems().addAll(vs.findAll());
		
	}
	public void setHSuppEmp() {
		File inputFile = new File("C:\\fichiers\\taux_heure.txt");
		FileReader fr;
		BufferedReader br;
		String PHSupp="";
		try {
			rv.setSelected(false);
			fr = new FileReader(inputFile);
			 br=new BufferedReader(fr);
				String s;
				while((s=br.readLine())!=null) {
					String [] fileData=s.split(" ");
					if(fileData[0].equals("PHSupp")) {
						PHSupp=fileData[1];
					}
				}
				br.close();
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tfTHV.setText(PHSupp);
	}
	
	public void setPourcentageVend() {
		File inputFile = new File("C:\\fichiers\\taux_heure.txt");
		FileReader fr;
		BufferedReader br;
		String pourcentage="";
		try {
			re.setSelected(false);
			fr = new FileReader(inputFile);
			 br=new BufferedReader(fr);
				String s;
				while((s=br.readLine())!=null) {
					String [] fileData=s.split(" ");
					if(fileData[0].equals("pourcentage")) {
						pourcentage=fileData[1];
					}
				}
				br.close();
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tfTHV.setText(pourcentage);
	}
	
	public void addSalarie() {
		if(re.isSelected()) {
			employe  emp = new employe(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"employe",Double.parseDouble(tfDateRec.getText()),1, 0,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			EmployeService es = new EmployeService();
			es.createEmploye(emp);
			table.getItems().add(es.findEmployeById(emp.getMatricule()));
		} else if(rv.isSelected()) {
			vendeur v = new vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"vendeur",Double.parseDouble(tfDateRec.getText()), 1,0,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			VendeurService vs = new VendeurService();
			vs.createVendeur(v);
			table.getItems().add(vs.findVendeurById(v.getMatricule()));
		}	
	}
	
	
	public void supprimer() {
		if(table.getSelectionModel().getSelectedItem().getCat().equals("employe")) {
			EmployeService es=new EmployeService();
			es.deleteEmploye(es.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());
		}else
		if(table.getSelectionModel().getSelectedItem().getCat().equals("vendeur")){
			VendeurService vs=new VendeurService();
			vs.deleteVendeur(vs.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());
		}
		
	}
	
	public void update() {
		if(re.isSelected()) {
			employe  emp = new employe(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"employe",Double.parseDouble(tfDateRec.getText()),1, 0.1,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			EmployeService es = new EmployeService();
			es.updateEmploye(emp);
			table.getItems().clear();
			table.getItems().addAll(es.findAll());
		} else if(rv.isSelected()) {
			vendeur v = new vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"vendeur",Double.parseDouble(tfDateRec.getText()), 1,0.1,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			VendeurService vs = new VendeurService();
			vs.updateVendeur(v);
			table.getItems().clear();
			table.getItems().addAll(vs.findAll());
		}
	
		
	}
	
	public void listParCat() {
		EmployeService  es=new EmployeService();
		VendeurService vs=new VendeurService();
		table.getItems().clear();
		if (rbLemp.isSelected()) {
			table.getItems().addAll(es.findAll());
		}else 
			if(rbLvend.isSelected()) {
			table.getItems().addAll(vs.findAll());
		}
	}
	
	
	public void details() {
		if(table.getSelectionModel().getSelectedItem().getCat().equals("employe")) {
			EmployeService es=new EmployeService();
			employe emp=es.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetails.setText(emp.toString());
		}else
		if(table.getSelectionModel().getSelectedItem().getCat().equals("vendeur")){
			VendeurService vs=new VendeurService();
			vendeur vend=vs.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetails.setText(vend.toString());
		}
	}
	

	
	public void listAnciennete() {
		SalarieService sv=new SalarieService();
		List<salarie> listAnciennete=sv.anciennete();
		table.getItems().clear();
		table.getItems().addAll(listAnciennete);
	}
	
	public void listerMaxTauxVente() {
		VendeurService vs=new VendeurService();
		List<vendeur> Lmtv=vs.maxTauxVente();
		table.getItems().clear();
		table.getItems().addAll(Lmtv);
	}
	
	public void salaireFaible() {
		SalarieService sv=new SalarieService();
		List<salarie> listFaibleSalaire=sv.salFaible();
		table.getItems().clear();
		table.getItems().addAll(listFaibleSalaire);
	}
	
	
	public void exporter() {
			File expFile = new File("C:\\fichiers\\exporter.txt");
			try {
				EmployeService emps=new EmployeService();
				VendeurService vends=new VendeurService();
				FileWriter expFileReader=new FileWriter(expFile);
				expFileReader.write(emps.findAll().toString());
				expFileReader.write(vends.findAll().toString());

				expFileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	public void exit() {
		System.exit(0);
	}


}
