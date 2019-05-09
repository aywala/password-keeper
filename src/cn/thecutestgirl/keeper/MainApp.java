package cn.thecutestgirl.keeper;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cn.thecutestgirl.keeper.model.Account;
import cn.thecutestgirl.keeper.model.AccountListWrapper;
import cn.thecutestgirl.keeper.view.AccountEditDialogController;
import cn.thecutestgirl.keeper.view.AccountOverviewController;
import cn.thecutestgirl.keeper.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private String key="123";
    
    /**
     * The data as an observable list of Accounts.
     */
    private ObservableList<Account> accountData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        
    }
  
    /**
     * Returns the data as an observable list of Accounts. 
     * @return
     */
    public ObservableList<Account> getAccountData() {
        return accountData;
    }
  

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PasswordKeeper");

        initRootLayout();

        showAccountOverview();
    }
    
    /**
     * Initializes the root layout and tries to load the last opened
     * Account file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened Account file.
        File file = getAccountFilePath();
        if (file != null) {
            loadAccountDataFromFile(file);
        }
    }
    /**
     * Shows the account overview inside the root layout.
     */
    public void showAccountOverview() {
        try {
            // Load account overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AccountOverview.fxml"));
            AnchorPane AccountOverview = (AnchorPane) loader.load();
            
            // Set account overview into the center of root layout.
            rootLayout.setCenter(AccountOverview);
            
            //Give the controller access to the main app.
            AccountOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showAccountEditDialog(Account account) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AccountEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Account");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the Account into the controller.
            AccountEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAccount(account);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Returns the account file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getAccountFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setAccountFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("PasswordKeeper - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("PasswordKeeper");
        }
    }
    
    /**
     * Loads Account data from the specified file. The current Account data will
     * be replaced.
     * 
     * @param file
     */
    public void loadAccountDataFromFile(File file) {
    	
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Key");
    	dialog.setContentText("Please enter your key:");
    	 
    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (!result.isPresent()) System.exit(0);
    	if (result.isPresent()){
    	    this.key=result.get();
    	}
    	 
    	
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AccountListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            AccountListWrapper wrapper = (AccountListWrapper) um.unmarshal(file);
            List<Account> enc_accounts=wrapper.getAccounts();
            List<Account> dec_accounts = new LinkedList<>();
            for(int i=0;i<enc_accounts.size();i++) {
            	Account dec_account=new Account();
                dec_account.setSite(PBE.decryptPBE(enc_accounts.get(i).getSite(),key));
	        	dec_account.setUsername(PBE.decryptPBE(enc_accounts.get(i).getUsername(),key));
	        	dec_account.setPassword(PBE.decryptPBE(enc_accounts.get(i).getPassword(),key));
	        	dec_account.setNote(PBE.decryptPBE(enc_accounts.get(i).getNote(),key));
	        	dec_accounts.add(dec_account);
            }
            accountData.clear();
            accountData.addAll(dec_accounts);

            // Save the file path to the registry.
            setAccountFilePath(file);

        } catch (Exception e) { // catches ANY exception
			Alert alert=new Alert(AlertType.ERROR);
            alert.setContentText("Could not load data from file:\n" + file.getPath()+"\n"+e);
            alert.showAndWait();
            Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
            prefs.remove("filePath");
        }
    }

    /**
     * Saves the current Account data to the specified file.
     * 
     * @param file
     */
    public void saveAccountDataToFile(File file) {
    	
    	TextInputDialog dialog = new TextInputDialog("");
    	dialog.setTitle("Key");
    	dialog.setContentText("Please enter your key:");
    	 
    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (!result.isPresent()) System.exit(0);
    	if (result.isPresent()){
    	    this.key=result.get();
    	}
    	
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AccountListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our Account data.
            AccountListWrapper wrapper = new AccountListWrapper();
            List<Account> dec_accounts=accountData;
            List<Account> enc_accounts=new LinkedList<>();
            for(int i=0;i<dec_accounts.size();i++) {
	            Account enc_account=new Account();
	        	enc_account.setSite(PBE.encryptPBE(dec_accounts.get(i).getSite(),key));
	        	enc_account.setUsername(PBE.encryptPBE(dec_accounts.get(i).getUsername(),key));
	        	enc_account.setPassword(PBE.encryptPBE(dec_accounts.get(i).getPassword(),key));
	        	enc_account.setNote(PBE.encryptPBE(dec_accounts.get(i).getNote(),key));
	        	enc_accounts.add(enc_account);
            }
            wrapper.setAccounts(enc_accounts);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setAccountFilePath(file);
        } catch (Exception e) { // catches ANY exception
        	Alert alert=new Alert(AlertType.ERROR);
        	alert.setHeaderText(null);
            alert.setContentText("Could not save data to file:\n" + file.getPath()+"\n"+e);
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}