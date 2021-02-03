package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Person;


public class ViewController {
		File f = new File("src/main/java/file/file.txt");
		TreeMap<Integer, Person> tm = new TreeMap<Integer, Person>();
		int index = -1;
		
		@FXML
	    private Label messageHomme;

	    @FXML
	    private Label MessageFemme;
	    
	    @FXML
	    private Label messagePrives;

	    @FXML
	    private Label messagePublic;
	    
	    @FXML
	    private Label messagUsers;
	    
	    @FXML
	    private JFXCheckBox check;
		    
		@FXML
	    private JFXTextField tfDate;
   	 	
		@FXML
	    private Label message;

	    @FXML
	    private FontAwesomeIconView icActualiser;

	    @FXML
	    private JFXTextField tfLocalisation;

	    @FXML
	    private JFXTextField tfFormation;

	    @FXML
	    private JFXTextField tfNom;

	    @FXML
	    private JFXTextField tfPrenom;

	    @FXML
	    private JFXComboBox<String> cbSecteur;

	    @FXML
	    private JFXComboBox<String> cbSexe;

	    @FXML
	    private JFXButton btAjouter;

	    @FXML
	    private JFXButton btModifier;

	    @FXML
	    private JFXButton btAnnuler;

	    @FXML
	    private JFXButton btSupprimer;

	    @FXML
	    private TableView<Person> table;
	    

	    @FXML
	    private TableColumn<Person, String> colDate;

	    @FXML
	    private TableColumn<Person, String> colAdresse;

	    @FXML
	    private TableColumn<Person, String> colFormation;

	    @FXML
	    private TableColumn<Person, String> colTypeEtab;
	    ObservableList<String> combx1 = FXCollections.observableArrayList("Publics", "Priv�s");

	    @FXML
	    private TableColumn<Person, String> colNom;

	    @FXML
	    private TableColumn<Person, String> colPrenom;

	    @FXML
	    private TableColumn<Person, String> colGenre;
	    ObservableList<String> combx2 = FXCollections.observableArrayList("Masculin", "Feminin");

	    @FXML
	    private JFXComboBox<String> cbRechercher;

	    @FXML
	    private JFXTextField tfRechercher;

	    @FXML
	    private JFXButton btRechercher;
	    
	    @FXML
	    private PieChart pcSexe;
	    
	    @FXML
	    private PieChart pcSecteur;    
	    
	    @FXML
	    void actualise(MouseEvent event) throws IOException {
	    	initialize();
	    }

	    @FXML
	    void ajouter(ActionEvent event) throws IOException {
	    	tm = ajoutTM(tm);
	    	writeTM(tm, f);
	    	initialize();
	    }
	    
	    TreeMap<Integer, Person> ajoutTM(TreeMap<Integer, Person> tmp) throws IOException{
	    	Person newPerson = new Person(tfDate.getText(),tfLocalisation.getText(),tfFormation.getText(),
	    			cbSecteur.getValue(),cbSexe.getValue(),tfNom.getText(),tfPrenom.getText());
	    	if(tmp.isEmpty())
	    		tmp.put(0, newPerson);
	    	else
	    		tmp.put(tmp.lastKey()+1, newPerson);
			return tmp;
	    }

	    @FXML
	    void annuler(ActionEvent event) throws IOException {
	    	Alert confirmation = new Alert(AlertType.CONFIRMATION);
			
			confirmation.setHeaderText("Demande de confirmation");
			confirmation.setContentText("Voulez-vous vraiment l'annuler?");
			
			Optional<ButtonType> reponse = confirmation.showAndWait();
			
			if(reponse.get().equals(ButtonType.CANCEL))
				return;
	    	
	    	message.setText("Operation annul�e");
			message.setTextFill(Color.TEAL);
	    	initialize();
	    }

	    @FXML
	    void modifier(ActionEvent event) throws Exception {
	    	Person ref = table.getSelectionModel().getSelectedItem();
	    	tm = editLigneTM(tm, ref);
	    	writeTM(tm, f);
	    	initialize();
	    }
	    
	    @FXML
	    void typeRecherche(KeyEvent event) {
	    	tm = readFile(f, tm);
	    	cherche(tm);
	    }

	    @FXML
	    void supprimer(ActionEvent event) throws Exception {
			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			
			confirmation.setHeaderText("Demande de confirmation");
			confirmation.setContentText("Voulez-vous vraiment le supprimer?");
			
			Optional<ButtonType> reponse = confirmation.showAndWait();
			
			if(reponse.get().equals(ButtonType.CANCEL))
				return;
			
	    	Person ref = table.getSelectionModel().getSelectedItem();
	    	tm = removeLigneTM(tm,ref);
	    	writeTM(tm, f);
	    	manisa(f);
	    }
	    
	    @FXML
		public void initialize() throws IOException{
	    	cbSexe.setValue("Masculin");
	    	cbSexe.setItems(combx2);
	    	cbSecteur.setValue("Publics");
	    	cbSecteur.setItems(combx1);
	    	
	    	tfDate.setText("");
	    	tfFormation.setText("");
	    	tfLocalisation.setText("");
	    	tfNom.setText("");
	    	tfPrenom.setText("");
	    	tfRechercher.setText("");
	    	
	    	tm = readFile(f,tm);
	    	buildTab(tm);
			manisa(f);
	    }
	    
	    public void initCol(){
	    	colDate.setCellValueFactory(new PropertyValueFactory<Person, String>("date"));
	    	colAdresse.setCellValueFactory(new PropertyValueFactory<Person, String>("local"));
	    	colFormation.setCellValueFactory(new PropertyValueFactory<Person, String>("formation"));
	    	colTypeEtab.setCellValueFactory(new PropertyValueFactory<Person, String>("secteur"));
	    	colGenre.setCellValueFactory(new PropertyValueFactory<Person, String>("sexe"));
	    	colNom.setCellValueFactory(new PropertyValueFactory<Person, String>("nom"));
	    	colPrenom.setCellValueFactory(new PropertyValueFactory<Person, String>("prenom"));
	    }
	    
	    TreeMap<Integer, Person> readFile(File f,TreeMap<Integer, Person>tmp){
			final String SEPARATOR = "\t";
			try {
				FileReader fr = new FileReader(f);
				BufferedReader b = new BufferedReader(fr);
				String line = "";
				int cle = 0;
				while((line = b.readLine()) != null){
					
					byte[] byteLine = line.getBytes();
					line = new String(byteLine, "ISO-8859-1");
					
					String[] mots = line.split(SEPARATOR);
					Person p = new Person(mots[0], mots[1], mots[2], mots[3], mots[4], mots[5], mots[6]);
					cle +=1;	
					tmp.put(cle, p);
				}
				b.close();
				fr.close();
			} catch (FileNotFoundException e) {
				message.setText("Le fichier n'a pas �t� trouv� ");
				message.setTextFill(Color.RED);
				
				System.err.printf("Le fichier %s n'a pas �t� trouv� ",f.toString());
			} catch (IOException e){
				message.setText("Impossible de lire le contenu dans le fichier");
				message.setTextFill(Color.RED);
				System.err.println("Impossible de lire le contenu dans le fichier");
			}
			return tmp;
		}
	    
	    public void buildTab(TreeMap<Integer, Person> tmp){
	    	try{
	    		ObservableList<Person> data = FXCollections.observableArrayList(tmp.values());
	    		table.getItems().removeAll(table.getItems());
	    		initCol();
	    		table.setItems(data);
	    	}catch(UnsupportedOperationException e){
	    		message.setText("Exception d'op�ration non prise en charge "+e.getMessage());
				message.setTextFill(Color.RED);
	    	}
	    	
	    }
	    
	    public void cherche(TreeMap<Integer, Person> tmp){
	    	ObservableList<Person> data = FXCollections.observableArrayList(tm.values());
	    	FilteredList<Person> datafilter = new FilteredList<Person>(data, b -> true);
	    	
	    	tfRechercher.textProperty().addListener((Observable,oldvalue,newvalue) -> {
	    		datafilter.setPredicate(p -> {
	    			if (newvalue==null || newvalue.isEmpty()) {
						return true;
					}
	    			String lowerCaseFilter = newvalue.toLowerCase();
	    			
	    			if(p.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1 || 
	    					p.getLocal().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
	    					p.getFormation().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
	    					p.getSecteur().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
	    					p.getSexe().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
	    					p.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
	    					p.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1)
						return true;
	    			else
	    				return false;
	    		});
	    	});
	    	SortedList<Person> datasorte = new SortedList<Person>(datafilter);
			datasorte.comparatorProperty().bind(table.comparatorProperty());
			table.setItems(datasorte);
	    }
		
		public void manisa(File f) throws IOException{
			int Pr = 0;
			int Pb = 0;
			int H = 0;
			int F = 0;
			int[] res = {0,0,0,0};
			final String SEPARATOR = "\t";
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f),Charset.forName("ISO-8859-1")));
			String line = "";
			while((line = b.readLine()) != null){
				String[] mots = line.split(SEPARATOR);
				Person pr = new Person(mots[0], mots[1], mots[2], mots[3], mots[4], mots[5], mots[6]);
				
				if(pr.getSecteur().equals("Priv�s") && pr.getSexe().equals("Masculin")) {
					Pr += 1;
					res[0] = Pr;
					H += 1;
					res[2] = H;
					} else if(pr.getSecteur().equals("Priv�s") && pr.getSexe().equals("Feminin")) {
						Pr += 1;
						res[0] = Pr;
						F += 1;
						res[3] = F;
					} else if(pr.getSecteur().equals("Publics") && pr.getSexe().equals("Masculin")) {
						Pb += 1;
						res[1] = Pb;
						H += 1;
						res[2] = H;
					}
					else if(pr.getSecteur().equals("Publics") && pr.getSexe().equals("Feminin")) {
						Pb += 1;
						res[1] = Pb;
						F += 1;
						res[3] = F;
						} 
					else {
						System.out.println("Error");
					}
				}
			messageHomme.setText(String.valueOf(H)+" Hommes");
			MessageFemme.setText(String.valueOf(F)+" Femmes");
			messagePrives.setText(String.valueOf(Pr)+" Priv�s");
			messagePublic.setText(String.valueOf(Pb)+" Publics");
			messagUsers.setText(String.valueOf(H+F)+" Personnes");
			
		
			pcSexe.getData().add(new PieChart.Data("HOMME: ",res[2]));
			pcSexe.getData().add(new PieChart.Data("FEMME: ",res[3]));
			pcSexe.setLegendVisible(false);
		
	        pcSecteur.getData().add(new PieChart.Data("PUBLICS: ",res[0]));
	        pcSecteur.getData().add(new PieChart.Data("PRIVES: ",res[1]));
	        pcSecteur.setLegendVisible(false);
	        
	        b.close();
		}
		
		@FXML
	    void edit(MouseEvent event) {
	    	try{
	    		Person p = table.getSelectionModel().getSelectedItem();
				tfFormation.setText(p.getFormation());
		    	tfLocalisation.setText(p.getLocal());
		    	tfNom.setText(p.getNom());
		    	tfPrenom.setText(p.getPrenom());
		    	cbSecteur.setValue(p.getSecteur());
		    	cbSexe.setValue(p.getSexe());
		    	tfDate.setText(p.getDate());
	    	}catch (NullPointerException e) {
				message.setText("Aucune s�lection");
				message.setTextFill(Color.RED);
			}
	    }
		
		TreeMap<Integer, Person> removeLigneTM(TreeMap<Integer, Person> tmp, Person ref) throws IOException{
			
			Set<Integer> keys = tmp.keySet();
			for(Integer key : keys){
				Person p = tmp.get(key);
				if(p.equals(ref)){
					tmp.remove(key);
					buildTab(tmp);
					break;
				}
			}
			return tmp;
		}
		
		TreeMap<Integer, Person> editLigneTM(TreeMap<Integer, Person>tmp, Person ref){
			Set<Integer> keys = tmp.keySet();
			for(Integer key : keys){
				Person p = tmp.get(key);
				if(p.equals(ref)){
					tmp.remove(key);
					p.setDate(tfDate.getText());
					p.setFormation(tfFormation.getText());
					p.setLocal(tfLocalisation.getText());
					p.setNom(tfNom.getText());
					p.setPrenom(tfPrenom.getText());
					p.setSecteur(cbSecteur.getValue());
					p.setSexe(cbSexe.getValue());
					tmp.put(key, p);
					buildTab(tmp);
					break;
				}
			}
			return tmp;
		}
		
		File writeTM(TreeMap<Integer, Person> tmp, File f){
			try {
				Person p = new Person();
				PrintWriter pw = new PrintWriter(f,"ISO-8859-1");
				Set<Integer> keys = tmp.keySet();
				for(Integer key : keys){
					p = tmp.get(key);
					if(key==tmp.lastKey()){
						pw.print(p.getDate()+"\t"+p.getLocal()+"\t"+p.getFormation()+"\t"
								+p.getSecteur()+"\t"+p.getSexe()+"\t"+p.getNom()+"\t"+p.getPrenom());
					}
					else
						pw.println(p.getDate()+"\t"+p.getLocal()+"\t"+p.getFormation()+"\t"
								+p.getSecteur()+"\t"+p.getSexe()+"\t"+p.getNom()+"\t"+p.getPrenom());
				}
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return f;
			
		}
		
		@FXML
	    void rechercheMulti(MouseEvent event) {
			
	    }
		
		@FXML
	    void searchAll(KeyEvent event) {
			if(check.isSelected()){
				try{
					Person etudiant = new Person(tfDate.getText(), tfLocalisation.getText(), tfFormation.getText(), cbSecteur.getValue(),
    				cbSexe.getValue(), tfNom.getText(), tfPrenom.getText());
					tm = readFile(f, tm);
					TreeMap<Integer, Person> treem = search(etudiant,tm);
        		
					ObservableList<Person> data = FXCollections.observableArrayList(treem.values());
					table.getItems().removeAll(table.getItems());
           	 		table.setItems(data);
					} catch(Exception e){
						e.printStackTrace();
					} 
				}
			else{
				message.setText("Bug � resoudre");
				message.setTextFill(Color.RED);
			}
	    }
		
		TreeMap<Integer, Person> search(Person etudiant, TreeMap<Integer, Person> tmp){
    		TreeMap<Integer, Person> result = new TreeMap<>();
    		Set<Integer> cles = tmp.keySet();
    		int k = 0;
    		for(int cle : cles){
    			Person p = tmp.get(cle);
    			k += 1;      			
    			if(p.getNom().contains(etudiant.getNom().toUpperCase()) && p.getPrenom().contains(etudiant.getPrenom()) && p.getLocal().contains(etudiant.getLocal()) 
    					&& p.getFormation().contains(etudiant.getFormation()) && p.getDate().contains(etudiant.getDate())
    					&& p.getSecteur().contains(etudiant.getSecteur()) && p.getSexe().contains(etudiant.getSexe())){	
    			result.put(k, p);
    			}
    		}
    		
    		return result;
    	}
		public void messageTemporaire(String mes){
			try {
				//message.setText(mes);
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}