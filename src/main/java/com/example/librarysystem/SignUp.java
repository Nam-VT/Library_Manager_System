package com.example.librarysystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.sql.*;

public class SignUp {
    @FXML
    private TextField user_name;

    @FXML
    private PasswordField user_password;

    @FXML
    private TextField user_email;

    @FXML
    private TextField user_id;

    @FXML
    private Button signUp;

    @FXML
    private Button Back;

    private Connection  connect = Database.getConnection();
    public void insertSingupDetails() {
        String name = user_name.getText();
        String pwd = user_password.getText();
        String email = user_email.getText();
        String id = user_id.getText();

        connect = Database.getConnection();
        try {
            String sql = "insert into users(user_name,user_password,user_email,user_id,role) values(?,?,?,?,'user')";
            PreparedStatement pst = connect.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, pwd);
            pst.setString(3, email);
            pst.setString(4, id);

            int updatedRowCount = pst.executeUpdate();

            Alert alert;
            if (updatedRowCount > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Record Insert Successfully");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMenu.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Login Menu");
                stage.show();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Record Insert Failure");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateSignup(){
        String name = user_name.getText();
        String pwd = user_password.getText();
        String email = user_email.getText();
        String id = user_id.getText();

        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin");
            alert.setHeaderText(null);
            alert.setContentText("Please enter User Name");

            return false;
        }

        if (pwd.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin");
            alert.setHeaderText(null);
            alert.setContentText("Please enter User PassWord");

            return false;
        }

        if (email.isEmpty() || !email.matches("^.+@.+\\..+$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin");
            alert.setHeaderText(null);
            alert.setContentText("Please enter User Email");

            return false;
        }

        if (id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin");
            alert.setHeaderText(null);
            alert.setContentText("Please enter User ID");

            return false;
        }

        return true;
    }

    public boolean checkDuplicateUser(){
        String name = user_name.getText();
        boolean isExist = false;

        connect = Database.getConnection();
        try {
            String sql = "select * from users where user_name = ?";
            PreparedStatement pst = connect.prepareStatement(sql);

            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                isExist = true;
            }else{
                isExist = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    @FXML
    public  void buttonsignUpActionPerformed(){
        if (validateSignup()) {
            if (!checkDuplicateUser()) {
                insertSingupDetails();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Sign Up Successful!");
                alert.showAndWait();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMenu.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("Login Menu");
                    stage.show();

                    Stage currentStage = (Stage) signUp.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void buttonBackActionPerformed() {
        try {
            Stage currentStage = (Stage) Back.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMenu.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSignUpScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpMenu.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}