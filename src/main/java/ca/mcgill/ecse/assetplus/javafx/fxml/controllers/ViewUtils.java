package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOUser;
import ca.mcgill.ecse.assetplus.controller.TOUserController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.model.AssetType;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewUtils {

    /**
     * Calls the controller and shows an error, if applicable.
     */
    public static boolean callController(String result) {
        if (result.isEmpty()) {
            AssetPlusFxmlView.getInstance().refresh();
            return true;
        }
        showError(result);
        return false;
    }

    /**
     * Calls the controller and returns true on success. This method is included for readability.
     */
    public static boolean successful(String controllerResult) {
        return callController(controllerResult);
    }

    /**
     * Creates a popup window.
     *
     * @param title:   title of the popup window
     * @param message: message to display
     */
    public static void makePopupWindow(String title, String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogPane = new VBox();

        // create UI elements
        Text text = new Text(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(a -> dialog.close());

        // display the popup window
        int innerPadding = 10; // inner padding/spacing
        int outerPadding = 100; // outer padding
        dialogPane.setSpacing(innerPadding);
        dialogPane.setAlignment(Pos.CENTER);
        dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
        dialogPane.getChildren().addAll(text, okButton);
        Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
        dialog.setScene(dialogScene);
        dialog.setTitle(title);
        dialog.show();
    }

    public static void makePopupWindow(String title, String message, String secondary_button) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogPane = new VBox();

        // create UI elements
        Text text = new Text(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(a -> dialog.close());

        // display the popup window
        int innerPadding = 10; // inner padding/spacing
        int outerPadding = 100; // outer padding
        dialogPane.setSpacing(innerPadding);
        dialogPane.setAlignment(Pos.CENTER);
        dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
        dialogPane.getChildren().addAll(text, okButton);
        Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
        dialog.setScene(dialogScene);
        dialog.setTitle(title);
        dialog.show();
    }

    public static void showError(String message) {
        makePopupWindow("Error", message);
    }

    public static ObservableList<TOMaintenanceTicket> getTickets() {
        List<TOMaintenanceTicket> tickets = AssetPlusFeatureSet6Controller.getTickets();
        // as javafx works with observable list, we need to convert the java.util.List to
        // javafx.collections.observableList
        return FXCollections.observableList(tickets);
    }

    public static ObservableList<String> getUserEmails() {
        List<TOUser> users = TOUserController.getUsers();
        List<String> userEmails = new ArrayList<String>();
        for (TOUser t : users) {
            userEmails.add(t.getEmail());
        }
        return FXCollections.observableList(userEmails);
    }


    public static ObservableList<AssetType> getAssetTypes() {
        //List<AssetType> assetTypes = AssetPlusFeatureSet2Controller.getAssetTypes();
        return FXCollections.observableList(new ArrayList<AssetType>());
    }

    public static void openAddTicketPage() {
    }

    public static void loadTicketsIntoTableView(
            TableView<TOMaintenanceTicket> ticketsTableView,
            TableColumn<TOMaintenanceTicket, Integer> numberColumn,
            TableColumn<TOMaintenanceTicket, String> issuerColumn,
            TableColumn<TOMaintenanceTicket, String> statusColumn,
            TableColumn<TOMaintenanceTicket, String> dateRaisedColumn,
            TableColumn<TOMaintenanceTicket, String> fixerColumn,
            TableColumn<TOMaintenanceTicket, String> timeToResolveColumn,
            TableColumn<TOMaintenanceTicket, String> priorityColumn,
            TableColumn<TOMaintenanceTicket, Boolean> approvalRequiredColumn
    ) {
        loadTicketsIntoTableView(ticketsTableView, numberColumn, issuerColumn, statusColumn, dateRaisedColumn, fixerColumn, timeToResolveColumn, priorityColumn, approvalRequiredColumn, ViewUtils.getTickets());
    }

    public static void loadTicketsIntoTableView(
            TableView<TOMaintenanceTicket> ticketsTableView,
            TableColumn<TOMaintenanceTicket, Integer> numberColumn,
            TableColumn<TOMaintenanceTicket, String> issuerColumn,
            TableColumn<TOMaintenanceTicket, String> statusColumn,
            TableColumn<TOMaintenanceTicket, String> dateRaisedColumn,
            TableColumn<TOMaintenanceTicket, String> fixerColumn,
            TableColumn<TOMaintenanceTicket, String> timeToResolveColumn,
            TableColumn<TOMaintenanceTicket, String> priorityColumn,
            TableColumn<TOMaintenanceTicket, Boolean> approvalRequiredColumn,
            ObservableList<TOMaintenanceTicket> tickets
    ) {
        numberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        issuerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedByEmail()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        dateRaisedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedOnDate().toString()));
        fixerColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFixedByEmail() == null) {
                return new SimpleStringProperty("N/A");
            }
            return new SimpleStringProperty(cellData.getValue().getFixedByEmail());
        });
        timeToResolveColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTimeToResolve() == null) {
                return new SimpleStringProperty("N/A");
            }
            return new SimpleStringProperty(cellData.getValue().getTimeToResolve());
        });
        priorityColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPriority() == null) {
                return new SimpleStringProperty("N/A");
            }
            return new SimpleStringProperty(cellData.getValue().getPriority());
        });
        approvalRequiredColumn.setCellValueFactory(cellData -> {
            return new SimpleBooleanProperty(cellData.getValue().getApprovalRequired());
        });

        System.out.println("update count");
        ticketsTableView.getItems().clear();
        // load tickets into TableView.
        for (TOMaintenanceTicket ticket : tickets) {
            ticketsTableView.getItems().add(ticket);
        }
        ticketsTableView.setRowFactory(tv -> new TableRow<TOMaintenanceTicket>() {
            protected void updateItem(TOMaintenanceTicket ticket, boolean empty) {
                super.updateItem(ticket, empty);

                if (ticket == null || empty) {
                    setStyle(""); // Default style for empty cells
                } else {
                    if (ticket.getStatus().equals("Open")) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else if (ticket.getStatus().equals("Inactive")) {
                        setStyle("-fx-background-color: lightcoral;");
                    } else {
                        setStyle(""); // Default style if the status doesn't match
                    }
                }
            }
        });
    }

    public static void returnToTicketStatusPage(Class currentClass, Stage currentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(currentClass.getResource("../MainPage.fxml"));
            Parent newRoot = fxmlLoader.load();

            //ViewStatusPageController viewStatusPageController = fxmlLoader.getController();
            //viewStatusPageController.initialize();

            MainPageController mainPageController = fxmlLoader.getController();
            mainPageController.setSelectedTabIndex(0);
            mainPageController.initialize();

            // Access the current stage
            //Stage currentStage = (Stage) doneEditButton.getScene().getWindow();
            // Replace the content in the current scene with content loaded from the new FXML
            currentStage.getScene().setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}