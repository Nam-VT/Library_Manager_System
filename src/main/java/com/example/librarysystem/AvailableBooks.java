package com.example.librarysystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class AvailableBooks implements Initializable {

    @FXML
    private Button X;

    @FXML
    private Button Back;
    @FXML
    private Button SaveBooks;

    @FXML
    private Button UpdateBooks;

    @FXML
    private Button DeleteBooks;

    @FXML
    private TextField txt_title;

    @FXML
    private TextField txt_type;

    @FXML
    private TextField txt_author;

    @FXML
    private TextField txt_quantity;

    @FXML
    private TextField txt_date;

    @FXML
    private TextField txt_ID;

    @FXML
    private TableView<AvailableBookData> availableBooks_tableView;

    @FXML
    private TableColumn<AvailableBookData,String> col_title;

    @FXML
    private TableColumn<ReturnBookData,String> col_type;

    @FXML
    private TableColumn<ReturnBookData,String> col_author;

    @FXML
    private TableColumn<AvailableBookData,Integer> col_quantity;

    @FXML
    private TableColumn<AvailableBookData,String> col_date;

    @FXML
    private TableColumn<AvailableBookData,Integer> col_id;



    private Connection connect = Database.getConnection();
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    public ObservableList<AvailableBookData> datalist() {
        ObservableList<AvailableBookData> listAvailableBooks = FXCollections.observableArrayList();
        String sql = "select * from book_details";
        connect = Database.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                AvailableBookData aBooks = new AvailableBookData(
                        result.getString("book_title"),
                        result.getString("book_type"),
                        result.getString("author"),
                        result.getInt("quantity"),
                        result.getDate("published_date"),
                        result.getInt("book_id")
                );
                listAvailableBooks.add(aBooks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAvailableBooks;
    }



    public void showAvailableBooks(){
        ObservableList<AvailableBookData> listAvailableBooks = datalist();

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));

        availableBooks_tableView.setItems(listAvailableBooks);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAvailableBooks();
    }

    @FXML
    public void buttonAddActionPerformed(){
        try{
            String sql = "insert into book_details(book_title,book_type,author,quantity,published_date,book_id) values(?,?,?,?,?,?)";
            prepare = connect.prepareStatement(sql);

            prepare.setString(1,txt_title.getText());
            prepare.setString(2,txt_type.getText());
            prepare.setString(3,txt_author.getText());
            prepare.setInt(4,Integer.parseInt(txt_quantity.getText()));
            prepare.setDate(5,java.sql.Date.valueOf(txt_date.getText()));
            prepare.setInt(6,Integer.parseInt(txt_ID.getText()));

            int result = prepare.executeUpdate();

            if (result > 0) {
                showAvailableBooks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonUpdateActionPerformed(){
        try {
            String sql = "update book_details set book_title = ?,book_type=?,author = ?,quantity = ?,published_date= ? where book_id = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,txt_title.getText());
            prepare.setString(2,txt_type.getText());
            prepare.setString(3,txt_author.getText());
            prepare.setInt(4,Integer.parseInt(txt_quantity.getText()));
            prepare.setDate(5,java.sql.Date.valueOf(txt_date.getText()));
            prepare.setInt(6,Integer.parseInt(txt_ID.getText()));
            showAvailableBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonDeleteActionPerformed() {
        try {
            String sql = "delete from book_details where book_id = ? ";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, Integer.parseInt(txt_ID.getText()));
            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                showAvailableBooks();
            } else {
                System.out.println("No book found with the provided ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void shownAvailableBooksScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AvailableBookMenu.fxml"));
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
