package cn.thecutestgirl.keeper.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import cn.thecutestgirl.keeper.MainApp;
import cn.thecutestgirl.keeper.model.Account;

public class AccountOverviewController {
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Account, String> siteColumn;
    @FXML
    private TableColumn<Account, String> usernameColumn;

    @FXML
    private Label siteLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label noteLabel;
    

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public AccountOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        siteColumn.setCellValueFactory(cellData -> cellData.getValue().siteProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        
        //Clear text fields
        showAccountDetails(null);
        
        //Listen for selection changes and show the account details when changed
        accountTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAccountDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        accountTable.setItems(mainApp.getAccountData());
    }
    
    
    /**
     * Fill all text fields to show details about the account.
     * If the specified account is null, clear all text fields.
     * @param account the account or null
     */
    public void showAccountDetails(Account account) {
    	if(account!=null) {
    		siteLabel.setText(account.getSite());
    		usernameLabel.setText(account.getUsername());
    		passwordLabel.setText((account.getPassword()));
    		noteLabel.setText(account.getNote());
    	}
    	else {
    		siteLabel.setText("");
    		usernameLabel.setText("");
    		passwordLabel.setText("");
    		noteLabel.setText("");
    	}
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteAccount() {
        int selectedIndex = accountTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex!=-1) {
        	accountTable.getItems().remove(selectedIndex);
        }
        else {
        	// Nothing selected.
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("无效操作");
        	alert.setContentText("请先选择一条记录！");
        	 
        	alert.showAndWait();
        }
    }
    @FXML
    private void handleNewAccount() {
	    Account tempAccount = new Account();
	    boolean okClicked = mainApp.showAccountEditDialog(tempAccount);
	    if (okClicked) {
	        mainApp.getAccountData().add(tempAccount);
	    }
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected Account.
	 */
	@FXML
	private void handleEditAccount() {
	    Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
	    if (selectedAccount != null) {
	        boolean okClicked = mainApp.showAccountEditDialog(selectedAccount);
	        if (okClicked) {
	            showAccountDetails(selectedAccount);
	        }

	    } else {
	        // Nothing selected.
	    	Alert alert=new Alert(AlertType.ERROR,"清选择一条记录！");
	    	alert.setHeaderText(null);
	        alert.showAndWait();
	    }
	}
}
