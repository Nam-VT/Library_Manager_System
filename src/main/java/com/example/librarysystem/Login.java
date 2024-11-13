package com.example.librarysystem;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class Login {
    @FXML
    private TextField user_name;

    @FXML
    private PasswordField user_password;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button X;

    @FXML
    public void buttonXActionPerformed(){
        System.exit(0);
    }

    public PreparedStatement prepare;
    private ResultSet result;
    public void login(){
        String name = user_name.getText();
        String pwd = user_password.getText();
        String sql = "Select * from users where user_name = ? and user_password = ?";

        try{
            Connection connect = Database.getConnection();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, name);
            prepare.setString(2, pwd);
            result = prepare.executeQuery();

            if( name.isEmpty() || pwd.isEmpty() ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            }else {
                if(result.next()){

                        Stage currentStage = (Stage) loginButton.getScene().getWindow();
                        currentStage.close();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Admin");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username or Password");
                    alert.showAndWait();
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonLoginActionPerformed(ActionEvent event) {
            login();
    }

    @FXML
    public  void buttonSignUpActionPerformed() {
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        currentStage.close();

        SignUp signUp = new SignUp();
        signUp.showSignUpScreen();
    }
}





