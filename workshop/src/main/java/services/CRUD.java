package services;

import models.Musee;
import models.User;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    //void insertOne(T t) throws SQLException;
    Boolean insertOne(T t) throws SQLException;
    void deleteOne(int t) throws SQLException;
    //void insertOne1(T t) throws SQLException;
    void displayOne() throws SQLException;
    T getMusee(int a) throws SQLException;
    //void updateOne(int a, T t,String c) throws SQLException;
    T getUserByUsername(String s) throws SQLException;

}