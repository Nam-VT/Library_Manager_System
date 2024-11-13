package com.example.librarysystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashBoard {
    @FXML
    private Button BookOptionButton;

    @FXML
    private Button AvailableBooksButton;

    @FXML
    private Button IssueBooksButton;

    @FXML
    private Button ReturnBooksButton;

    @FXML
    private Button ManageBooksButton;

    @FXML
    private Button X;

    @FXML
    private Button SignOut;

    @FXML
    public void buttonSignOutActionPerformed() {
        try {
            Stage currentStage = (Stage) SignOut.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMenu.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Login Menu");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonXActionPerformed(){
        System.exit(0);
    }

    @FXML
    public void buttonAvailableBooksActionPerformed() {
            Stage dashboardStage = (Stage) AvailableBooksButton.getScene().getWindow();
            dashboardStage.hide();

            AvailableBooks avl_book = new AvailableBooks();
            avl_book.shownAvailableBooksScreen();
    }

    @FXML
    public void buttonIssueBooksActionPerformed() {
        Stage dashboardStage = (Stage) IssueBooksButton.getScene().getWindow();
        dashboardStage.hide();

        IssueBook avl_book = new IssueBook();
        avl_book.shownIssueBookScreen();
    }

    @FXML
    public void buttonReturnBooksActionPerformed() {
        Stage dashboardStage = (Stage) ReturnBooksButton.getScene().getWindow();
        dashboardStage.hide();

        ReturnBook return_book = new ReturnBook();
        return_book.shownReturnBookScreen();
    }
}
