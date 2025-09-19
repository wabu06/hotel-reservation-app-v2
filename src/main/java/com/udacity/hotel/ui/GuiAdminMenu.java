package com.udacity.hotel.ui;


//import java.util.*;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collection;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.Scene;
//import javafx.scene.Node;

import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;

//import javafx.scene.control.Hyperlink;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.*;

import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.event.ActionEvent;

//import static javafx.application.Platform.exit;


public class GuiAdminMenu
{
	AdminResource AR;
	
	Stage ms; // menu stage
	Scene ams; // admin menu scene
	
	Map<String, Hyperlink> itemsMap;
	
	final private static GuiAdminMenu gam = new GuiAdminMenu();

	public static GuiAdminMenu getInstance() {
		return gam;
	}
	
	private GuiAdminMenu()
	{
		AR = AdminResource.getInstance();
		
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Admin Menu");
		title.setUnderline(true);
		title.setFont(new Font(20));
		pane.setTop(title);
		
		VBox vbox = new VBox();
		//vbox.setSpacing(5.0d);
		pane.setLeft(vbox);
		
		String[] items_txt = new String[]{
			"See All Customers",
			"See All Rooms",
			"See All Reservations",
			"Change Price For Single Rooms",
			"Change Price For Double Rooms",
			"See Current Room Rates",
			"See All Reservations For A Room",
			"Show Rooms Vacant For A Particular Check-in & Check-out Date",
			"Change The Room Of A Reservation",
			"Show All Reservations For A Customer",
			"Back To Main Menu"
		};

		itemsMap = new LinkedHashMap<>();
		
		for(String txt: items_txt)
			itemsMap.put(txt, new Hyperlink(txt));
		
		itemsMap.get("See All Customers").setOnAction(this::displayAllCustomers);
		itemsMap.get("See All Rooms").setOnAction(this::displayAllRooms);
		itemsMap.get("See All Reservations").setOnAction(this::displayAllReservations);
		
		Hyperlink[] items = new Hyperlink[items_txt.length];
		
		items = itemsMap.values().toArray(items);
		
		vbox.getChildren().addAll(items);
		
		ams = new Scene(pane);
	}
	
	private void displayAllCustomers(ActionEvent e)
	{
		Collection<Customer> customers = AR.getAllCustomers();
		
		if (customers.size() == 0)
		{
			var alert = new Alert(Alert.AlertType.ERROR, "There Are No Customers To Display");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
		}
		else
		{
			VBox cbox = new VBox();
			
			for(Customer C: customers)
				cbox.getChildren().addAll( new Label( C.toString() ), new Label() );
				
			ScrollPane sp = new ScrollPane();
			
			sp.setVmax(440);
      sp.setPrefSize(400, 300);
      sp.setContent(cbox);
      
      Dialog<ButtonType> dialog = new Dialog<>();
      dialog.setTitle("Customers");
      
      DialogPane dp = dialog.getDialogPane();
      dp.getButtonTypes().add(ButtonType.OK);
      dp.setContent(sp);
      
      dialog.showAndWait();
		}
	}
	
	private void displayAllRooms(ActionEvent e)
	{
		Collection<Room> rooms = AR.getAllRooms();
		
		VBox rbox = new VBox();
		
		for(Room R: rooms)
			rbox.getChildren().addAll( new Label( R.toString() ), new Label() );
		
		ScrollPane sp = new ScrollPane();
			
		sp.setVmax(440);
    sp.setPrefSize(400, 300);
    sp.setContent(rbox);
      
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Rooms");
      
    DialogPane dp = dialog.getDialogPane();
    dp.getButtonTypes().add(ButtonType.OK);
    dp.setContent(sp);
      
    dialog.showAndWait();
	}
	
	void displayAllReservations(ActionEvent e)
	{
		Collection<Reservation> reserves = AR.getAllReservations();
		
		if(reserves.size()  == 0)
		{
			var alert = new Alert(Alert.AlertType.ERROR, "There Are No Reservations To Display");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
		}
		else
		{
			VBox rbox = new VBox();
			
			for(Reservation R: reserves)
				rbox.getChildren().addAll( new Label( R.toString() ), new Label() );
			
			ScrollPane sp = new ScrollPane();
			
			sp.setVmax(440);
    	sp.setPrefSize(400, 300);
    	sp.setContent(rbox);
      
    	Dialog<ButtonType> dialog = new Dialog<>();
    	dialog.setTitle("Reservations");
      
    	DialogPane dp = dialog.getDialogPane();
    	dp.getButtonTypes().add(ButtonType.OK);
    	dp.setContent(sp);
      
    	dialog.showAndWait();
		}
	}
	
	public void setStage(Stage menuStage) {
		ms = menuStage;
	}
	
	public Scene getMenu() {
		return ams;
	}
	
	public void setMainMenuActionHandler(Scene mms) {
		itemsMap.get("Back To Main Menu").setOnAction( e -> ms.setScene(mms) );
	}
}
