package com.udacity.hotel.ui;



//import java.util.*;

import java.util.Optional;
import java.util.Map;
import java.util.LinkedHashMap;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;

import com.udacity.hotel.ui.dialogs.*;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.Scene;
//import javafx.scene.Node;

import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.*;
//import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
//import javafx.scene.text.*;

import javafx.event.ActionEvent;

import static javafx.application.Platform.exit;

import javafx.scene.paint.Color;



public class GuiMainMenu
{
	AdminResource AR;
	HotelResource HR;
	
	Stage ms; // menu stage
	Scene mms; // main menu scene
	
	Map<String, Hyperlink> itemsMap;
	
	final private static GuiMainMenu gmm = new GuiMainMenu();

	public static GuiMainMenu getInstance() {
		return gmm;
	}
	
	private GuiMainMenu()
	{
		AR = AdminResource.getInstance();
		HR = HotelResource.getInstance();
		
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Main Menu");
		title.setUnderline(true);
		title.setFont(new Font(20));
		title.setFill(Color.FIREBRICK);
		pane.setTop(title);
		
		VBox vbox = new VBox();
		vbox.setSpacing(15.0d);
		pane.setLeft(vbox);
		
		String[] items_txt = new String[]{
			"Reserve A Room",
			"Change A Reservation",
			"Cancel A Reservation",
			"See My Reservations",
			"Create An Account",
			"Goto Admin Menu",
			"Exit APP"
		};

		itemsMap = new LinkedHashMap<>();

		for(String txt: items_txt)
			itemsMap.put(txt, new Hyperlink(txt));
		
		itemsMap.get("Create An Account").setOnAction(this::creatAccount);
		
		Hyperlink[] items = new Hyperlink[items_txt.length];
		
		items = itemsMap.values().toArray(items);
		
		vbox.getChildren().addAll(items);
			
		mms = new Scene(pane);
	}
	
	public void setStage(Stage menuStage) {
		ms = menuStage;
	}
	
	public Scene getMenu() {
		return mms;
	}
	
	private void showAccountDialog(ActionEvent e) {
		new AccountPsuedoDialog(ms).show();
	}
	
	private void creatAccount(ActionEvent e)
	{
		AccountPsuedoDialog accountDialog = new AccountPsuedoDialog(ms);
		
		accountDialog.getStage().showAndWait();
		
		Customer C;
		
		if(accountDialog.isOkButtonClicked())
		{
			C = HR.createACustomer(	accountDialog.getPhone(),
															accountDialog.getEmail(),
															accountDialog.getFirstName(),
															accountDialog.getLastName(),
															accountDialog.getPin().hashCode()
														);
														
			var acctAlert = new Alert(Alert.AlertType.INFORMATION, C.toString());
			acctAlert.initStyle(StageStyle.UNDECORATED);
			acctAlert.showAndWait();
		}
		
	}
	
	public void setAdminAndExitActionHandlers(Scene ams)
	{
		itemsMap.get("Goto Admin Menu").setOnAction( e -> {
																										int pwHash = AR.getPasswordHash();
																										
																										if(pwHash == 0)
																										{
																											var spwDialog = new SetPassWordDialog();
																											spwDialog.initStyle(StageStyle.UNDECORATED);
																											Optional<String> passwd = spwDialog.showAndWait();
																											
																											if(passwd.get().isEmpty())
																											{
																												var spwAlert = new Alert(Alert.AlertType.ERROR, "You Did Not Enter An Admin Password!!");
																												spwAlert.initStyle(StageStyle.UNDECORATED);
																												spwAlert.showAndWait();
																												return;
																											}
																											
																											AR.savePasswordHash(passwd.get());
																											ms.setScene(ams);
																											
																											return;
																										}
																										
																										PassWordDialog pwDialog = new PassWordDialog();
																										pwDialog.initStyle(StageStyle.UNDECORATED);
																										Optional<String> passwd = pwDialog.showAndWait();
																								
																										String pw;
																								
																										if(passwd.isPresent())
																											pw = passwd.get();
																										else
																											return;
																										
																										if(pw.isEmpty())
																										{
																											var pwError = new Alert(Alert.AlertType.ERROR, "You Did Not Enter An Admin Password!!");
																											pwError.initStyle(StageStyle.UNDECORATED);
																											pwError.showAndWait();
																											return;
																										}
																								
																										if(pwHash == pw.hashCode())
																											ms.setScene(ams);
																										else
																										{
																											var pwError = new Alert(Alert.AlertType.ERROR, "Incorrect Password!!");
																											pwError.initStyle(StageStyle.UNDECORATED);
																											pwError.showAndWait();
																										}
																									});
																							
		//items[items_txt.length - 1].setOnAction( e -> exit() );
		itemsMap.get("Exit APP").setOnAction( e -> exit() );
		
	}
}
