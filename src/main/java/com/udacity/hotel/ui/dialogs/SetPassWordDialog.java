package com.udacity.hotel.ui.dialogs;



import javafx.scene.control.*;
//import javafx.scene.layout.HBox;'
import javafx.scene.layout.VBox;


public class SetPassWordDialog extends Dialog<String>
{
	PasswordField pwField;
	
	public SetPassWordDialog()
	{
		Label pwLabel = new Label("Please Select & Enter An Admin PasssWord!");
		pwField = new PasswordField();
		
		VBox pwBox = new VBox(pwLabel, pwField);
		pwBox.setSpacing(5.0d);
		
		DialogPane dp = getDialogPane();
		
		dp.getButtonTypes().addAll(ButtonType.OK);
		dp.setContent(pwBox);
		
		setTitle("Set Admin Password");

    setResultConverter( bt -> { return pwField.getText(); } );
	}
}
