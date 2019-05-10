package cn.thecutestgirl.keeper.view;


import java.io.File;
import java.util.Optional;

import cn.thecutestgirl.keeper.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty password book.
     */
    @FXML
    private void handleNew() {
        mainApp.getAccountData().clear();
        mainApp.setAccountFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadAccountDataFromFile(file);
        }
    }

    /**
     * Saves the file to the Account file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File AccountFile = mainApp.getAccountFilePath();
        if (AccountFile != null) {
            mainApp.saveAccountDataToFile(AccountFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveAccountDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
       Alert alert=new Alert(AlertType.INFORMATION);
       alert.setHeaderText(null);
       alert.setContentText("作者: Minstrel\n站点 :www.thecutestgirl.cn\nGithub: Minstrel223");
       alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        if(mainApp.getIsChanged()) {
        	Alert alert_1=new Alert(AlertType.CONFIRMATION);
        	alert_1.setTitle("Confirmation Dialog");
        	alert_1.setHeaderText("是否保存？");
        	alert_1.setContentText(null);
        	 
        	Optional<ButtonType> result_1 = alert_1.showAndWait();
        	if (result_1.get() == ButtonType.OK){
        	    // ... user chose OK
        		handleSave();
        		System.exit(0);
        	} else {
        		Alert alert_2=new Alert(AlertType.CONFIRMATION);
            	alert_2.setTitle("Confirmation Dialog");
            	alert_2.setHeaderText("是否退出？");
            	alert_2.setContentText(null);
            	Optional<ButtonType> result_2 = alert_2.showAndWait();
            	if (result_2.get() == ButtonType.OK){
            		System.exit(0);
            	}  
        	}
        }
        else{
        	System.exit(0);
        	}
    }
    
}
