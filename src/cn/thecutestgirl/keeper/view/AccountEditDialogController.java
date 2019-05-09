package cn.thecutestgirl.keeper.view;

import cn.thecutestgirl.keeper.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * To edit account
 * @author minstrel
 *
 */
public class AccountEditDialogController {

	@FXML
	private TextField siteField;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField noteField;
	
	private Stage dialogStage;
	private Account account;
	private boolean okClicked=false;
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage=dialogStage;
	}
	
	public void setAccount(Account account) {
		this.account=account;
		siteField.setText(account.getSite());
		usernameField.setText(account.getUsername());
		passwordField.setText(account.getPassword());
		noteField.setText(account.getNote());
	}
	
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if(isInputValid()) {
		account.setSite(siteField.getText());
		account.setUsername(usernameField.getText());
		account.setPassword(passwordField.getText());
		account.setNote(noteField.getText());
		
		okClicked=true;
		dialogStage.close();
		}
	}
	
	@FXML
    private void handleCancel() {
        dialogStage.close();
    }
	
	/**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
	 private boolean isInputValid() {
	        String errorMessage = "";

	        if (siteField.getText() == null || siteField.getText().length() == 0) {
	            errorMessage += "无效的站点名!\n"; 
	        }
	        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
	            errorMessage += "无效的用户名!\n"; 
	        }
	        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
	            errorMessage += "无效的密码!\n"; 
	        }

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	           Alert alert=new Alert(AlertType.ERROR,errorMessage);
	           alert.setHeaderText(null);
	           alert.showAndWait();
	           return false;
	        }
	   }
}
