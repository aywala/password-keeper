package cn.thecutestgirl.keeper.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KeyInputDialogController {

	@FXML
	private TextField keyField;
	@FXML
	private Stage dialogStage;
	@FXML
	private String key=null;
	@FXML
	private Label label;
	
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
	
	@FXML
	private void handleOk() {
		if(keyField.getText()!=null&&keyField.getText()!="") {
			this.key=keyField.getText();
			dialogStage.close();
		}
		else this.key=null;
	}
	
	@FXML
	 private void handleCancel() {
			this.key=null;
	        dialogStage.close();
	 }
	
	public void clearKeyField() {
		this.keyField.clear();
	}
	 
	 public String getKey() {
		 return this.key;
	 }
	 
	 
	 public void setLabel(String text) {
		 label.setText(text);
	 }
}
