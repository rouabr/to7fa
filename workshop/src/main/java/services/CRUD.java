package services;

import models.Oeuvre;

import java.sql.SQLException;
import java.util.List;

public interface CRUD <T> {
    void  insertOne(T t) throws SQLException;
   void  updateOne(T t) throws SQLException;
   void  deleteOne(int id) throws SQLException;
    List<T> selectAll() throws SQLException;
    void updateOne1(int id_oeuvre, Oeuvre updatedOeuvre, String imagePath) throws SQLException ;


    }
