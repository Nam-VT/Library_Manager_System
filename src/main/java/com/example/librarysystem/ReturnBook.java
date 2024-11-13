package com.example.librarysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class ReturnBook implements Initializable {
    @FXML
    private TableView<ReturnBookData> return_tableView;

    @FXML
    private TableColumn<ReturnBookData, String> col_StudentName;

    @FXML
    private TableColumn<ReturnBookData, String> col_BookName;

    @FXML
    private TableColumn<ReturnBookData, String> col_IssueDate;

    @FXML
    private TableColumn<ReturnBookData, String> col_DueDate;

    @FXML
    private TextField txt_bookid;

    @FXML
    private TextField txt_studentid;

    @FXML
    private Button Back;

    @FXML
    private Button Exit;

    @FXML
    private Button Find;

    @FXML
    private Button Return;

    @FXML
    public void buttonExitActionPerformed(){
        System.exit(0);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showBookReturn();
    }

    public ObservableList<ReturnBookData> returnBook() {

        ObservableList<ReturnBookData> bookReturnData = FXCollections.observableArrayList();

        try {
        Connection connect = Database.getConnection();
//        String sql = "SELECT * FROM issue_book_details WHERE status = ? and student_id = ?";
        String sql = "SELECT * FROM issue_book_details WHERE status = ?";
        PreparedStatement prepare = connect.prepareStatement(sql);

        prepare.setString(1,"pending");
//        prepare.setString(2, txt_studentid.getText());

        ReturnBookData rBook;
        ResultSet result = prepare.executeQuery();

        while (result.next()) {
            rBook = new ReturnBookData(result.getString("student_name"),
                    result.getString("book_name"),
                    result.getDate("issue_date"),
                    result.getDate("due_date")
            );
            bookReturnData.add(rBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookReturnData;
    }



/*    public void showBookReturn() {

        ObservableList<ReturnBookData> retBook = returnBook();

        col_StudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        col_BookName.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        col_IssueDate.setCellValueFactory(new PropertyValueFactory<>("issue_date"));
        col_DueDate.setCellValueFactory(new PropertyValueFactory<>("due_date"));

        return_tableView.setItems(retBook);

    }
 */

    public void showBookReturn() {
        ObservableList<ReturnBookData> bookData = returnBook();
        return_tableView.setItems(bookData);
        if (col_StudentName.getCellValueFactory() == null) {
            col_StudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
            col_BookName.setCellValueFactory(new PropertyValueFactory<>("book_name"));
            col_IssueDate.setCellValueFactory(new PropertyValueFactory<>("issue_date"));
            col_DueDate.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        }

    }

    public void updateBookCount() {
        int bookId = Integer.parseInt(txt_bookid.getText());
        try {
            Connection connect = Database.getConnection();
            String sql = "UPDATE book_details SET quantity = quantity + 1 WHERE book_id = ?";
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, bookId);
            prepare.executeUpdate(); // Thực thi câu lệnh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReturnBooks(ReturnBookData row) {

        String sql = "DELETE FROM issue_book_details WHERE student_name = ? AND book_name = ? and status = 'pending'";
        try {
            Connection connect = Database.getConnection();
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setString(1, row.getStudent_name());
            prepare.setString(2, row.getBook_name());

            prepare.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteSelectedRow() {
        ReturnBookData selectedRow = return_tableView.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            return_tableView.getItems().remove(selectedRow);

            ReturnBooks(selectedRow);
//            updateBookCount();
            showBookReturn();
        }
    }

    @FXML
    public void checkFields() {
        String bookId = txt_bookid.getText();
        String studentId = txt_studentid.getText();

        if (!bookId.isEmpty() && !studentId.isEmpty()) {
            showBookReturn();
        }
    }

    public void shownReturnBookScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReturnBookMenu.fxml"));
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
