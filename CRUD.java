package services;

import javafx.collections.ObservableList;
import models.commande;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface CRUD<T,G> {
    void insertOne(T t) throws SQLException;
    void deleteOne(int i) throws SQLException ;
    public void updateOne(T t);
    List<T> selectAll() throws SQLException ;
     ObservableList<commande> selectAllTableview() throws SQLException, ParseException;
    List<G> selectoeuvre() throws SQLException ;
}
