package com.example.librarysystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IssueBook {
    @FXML
    private TextField ib_bookid;

    @FXML
    private TextField ib_studentid;

    @FXML
    private TextField ib_issuedate;

    @FXML
    private TextField ib_duedate;

    @FXML
    private TextField bd_bookid;

    @FXML
    private TextField bd_booktitle;

    @FXML
    private TextField bd_bookauthor;

    @FXML
    private TextField sd_studentid;

    @FXML
    private TextField sd_studentname;

    @FXML
    private TextField sd_studentmajor;

    @FXML
    private Button button;

    @FXML
    private Button Back;

    @FXML
    private Button X;

    @FXML
    public void initialize() {
        ib_bookid.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !ib_bookid.getText().isEmpty()) {
                getBookDetails();
            }
        });
        ib_studentid.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !ib_studentid.getText().isEmpty()) {
                getStudentDetails();
            }
        });
    }

    public void getBookDetails() {
        int book_id = Integer.parseInt(ib_bookid.getText());

        try {
            Connection connect = Database.getConnection();
            PreparedStatement prepare = connect.prepareStatement("select * from book_details where book_id = ?");
            prepare.setInt(1, book_id);
            ResultSet rs = prepare.executeQuery();

            if (rs.next()) {
                bd_bookid.setText(rs.getString("book_id"));
                bd_booktitle.setText(rs.getString("book_title"));
                bd_bookauthor.setText(rs.getString("author"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Book ID");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStudentDetails() {
        int student_id = Integer.parseInt(ib_studentid.getText());

        try {
            Connection connect = Database.getConnection();
            PreparedStatement prepare = connect.prepareStatement("select * from student_details where student_id = ?");
            prepare.setInt(1, student_id);
            ResultSet rs = prepare.executeQuery();

            if (rs.next()) {
                sd_studentid.setText(rs.getString("student_id"));
                sd_studentname.setText(rs.getString("student_name"));
                sd_studentmajor.setText(rs.getString("student_major"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Student ID");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void issueBook() {
        try {
            Connection connect = Database.getConnection();
            String sql = "insert into issue_book_details(book_id,book_name,student_id,student_name,issue_date,due_date,status) values(?,?,?,?,?,?,?)";
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, Integer.parseInt(ib_bookid.getText()));
            prepare.setString(2, bd_booktitle.getText());
            prepare.setInt(3, Integer.parseInt(ib_studentid.getText()));
            prepare.setString(4, sd_studentname.getText());
            prepare.setDate(5, java.sql.Date.valueOf(ib_issuedate.getText()));
            prepare.setDate(6, java.sql.Date.valueOf(ib_duedate.getText()));
            prepare.setString(7, "pending");

            prepare.executeUpdate();
/*
            if (rowCount > 0) {
                isIssued = true;
            } else {
                isIssued = false;
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return isIssued;
    }

    public void updateBookCount() {
        try {
            Connection connect = Database.getConnection();
            String sql = "update book_details set quantity = quantity - 1 where book_id = ?";
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, Integer.parseInt(ib_bookid.getText()));

            prepare.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAlreadyIssued() {

        boolean isAlreadyIssued = false;

        try {
            Connection connect = Database.getConnection();
            String sql = "select * from issue_book_details where book_id = ? and student_id = ? and status = ?";
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, Integer.parseInt(ib_bookid.getText()));
            prepare.setInt(2, Integer.parseInt(ib_studentid.getText()));
            prepare.setString(3, "pending");

            ResultSet rs = prepare.executeQuery();

            if (rs.next()) {
                isAlreadyIssued = true;
            } else {
                isAlreadyIssued = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAlreadyIssued;

    }

    @FXML
    public void buttonbuttonActionPerformed(){
        if(!isAlreadyIssued()){
            issueBook();
            updateBookCount();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Admin");
            alert.setHeaderText(null);
            alert.setContentText("book issued successfully");
            alert.showAndWait();

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Admin");
            alert.setHeaderText(null);
            alert.setContentText("this student already has this book");
        }
    }

    @FXML
    public void buttonBackActionPerformed() {
        try {
            Stage currentStage = (Stage) Back.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
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

    @FXML
    public void buttonXActionPerformed(){
        System.exit(0);
    }

    public void shownIssueBookScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IssueBookMenu.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
