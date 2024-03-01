package services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Categorie;
import models.Oeuvre;
import models.User;
import utils.DBConnection;
import java.util.Calendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class ServiceOeuvre implements CRUD<Oeuvre> {
    private Connection cnx;

    public ServiceOeuvre() {
        cnx = DBConnection.getInstance().getCnx();
    }
        User user = new User(1,"jhonDoe","1234567","john@example.com","password123","1990-01-01","Male","Regular","2024-02-21","2024-02-21","1234");
    @Override
    public void insertOne(Oeuvre oeuvre) throws SQLException {
        String sql = "INSERT INTO oeuvre ( titre, description, prix, date, status, lienImg) VALUES ( ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {


            statement.setString(1, oeuvre.getTitre());
            statement.setString(2, oeuvre.getDescription());
            statement.setFloat(3, oeuvre.getPrix());

            // Convert String date to java.sql.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsedDate = dateFormat.parse(oeuvre.getDate());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            statement.setDate(4, sqlDate); // Set the SQL date

            statement.setString(5, oeuvre.getStatus());
            statement.setString(6, oeuvre.getLienImg());



            statement.executeUpdate();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Oeuvre> selectAll()throws SQLException {
        List<Oeuvre> OeuvreList=new ArrayList<>();
        String req = "SELECT * FROM `oeuvre`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Oeuvre p =new Oeuvre();
            p.setId_oeuvre(rs.getInt(("id_oeuvre")));
            p.setTitre(rs.getString(("titre")));
            p.setDescription(rs.getString(("description")));
            p.setPrix(rs.getFloat("prix"));

            java.sql.Date sqlDate = rs.getDate("date");
            // Convert java.sql.Date to java.util.Date
            java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
            // Format the date as needed, e.g., using SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(utilDate);
            p.setDate(formattedDate);
            System.out.println("55");
            p.setStatus(rs.getString("status"));
            p.setLienImg(rs.getString("lienImg"));
            OeuvreList.add(p);
            System.out.println("aa");
        }
        return OeuvreList;
        //return null;
    }
    /*@Override
    public void updateOne(Oeuvre oeuvre) throws SQLException {
        String sql = "UPDATE oeuvre SET titre = ?, description = ?, prix = ?, date = ?, status = ? WHERE id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, oeuvre.getTitre());
            statement.setString(2, oeuvre.getDescription());
            statement.setFloat(3, oeuvre.getPrix());
            statement.setString(4, oeuvre.getDate());
            statement.setString(5, oeuvre.getStatus());
            statement.setInt(6, oeuvre.getId_oeuvre());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Oeuvre updated successfully.");
            } else {
                System.err.println("No oeuvre found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to update oeuvre in the database.");
        }
    }*/
    @Override
    public void updateOne(Oeuvre oeuvre) throws SQLException {
        String sql = "UPDATE oeuvre SET titre = ?, description = ?, prix = ?, date = ?, status = ?, lienImg = ? WHERE id_oeuvre = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {

            statement.setString(1, oeuvre.getTitre());
            statement.setString(2, oeuvre.getDescription());
            statement.setFloat(3, oeuvre.getPrix());
            statement.setString(4, oeuvre.getDate());
            statement.setString(5, oeuvre.getStatus());
            statement.setString(6, oeuvre.getLienImg());
            statement.setInt(7, oeuvre.getId_oeuvre());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Oeuvre updated successfully.");
            } else {
                System.err.println("No oeuvre found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to update oeuvre in the database.");
        }
    }
@Override
    // Delete operation
    public void deleteOne(int id_ouevre) throws SQLException {
        String sql = "DELETE FROM oeuvre WHERE id_oeuvre = ?";
           System.out.println(id_ouevre);
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, id_ouevre);
          //  System.out.

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Oeuvre deleted successfully.");
            } else {
                System.err.println("No oeuvre found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to delete oeuvre from the database.");
        }
    }
    // Method to retrieve all Oeuvres from the database
    public List<Oeuvre> getAllOeuvres() {
        List<Oeuvre> oeuvres = new ArrayList<>();
        ServiceCategorie sc=new ServiceCategorie();
        // SQL query to select all Oeuvres
        String sql = "SELECT * FROM oeuvre";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and create Oeuvre objects
            while (resultSet.next()) {
                // Retrieve data from the result set
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description");
                float prix = resultSet.getFloat("prix");
                String date = resultSet.getString("date"); // Assuming date is stored as String
                String status = resultSet.getString("status");
                String lienImg = resultSet.getString("lienImg");
                int idcateg = resultSet.getInt("id_cat");
                //Categorie categorie=sc.getCategorieById(idcateg);


                // Create an Oeuvre object and add it to the list
                Oeuvre oeuvre = new Oeuvre(titre, description, prix, date, status, lienImg,idcateg);
                oeuvres.add(oeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve Oeuvres from the database.");
        }

        return oeuvres;
    }
    // Method to apply promotions on Oeuvre prices for a period of 15 days from the date of disposition
   /* public void applyPromotions() {
        try {
            // Get the current date
            Date currentDate = new Date();

            // Calculate the end date of the promotion period (15 days from the current date)
            long promotionPeriodMillis = 15L * 24 * 60 * 60 * 1000; // 15 days in milliseconds
            Date endDate = new Date(currentDate.getTime() + promotionPeriodMillis);

            // SQL query to update the price of Oeuvres within the promotion period
            String sql = "UPDATE oeuvre SET prix = prix * 0.9 WHERE date >= ? AND date <= ?";

            try (PreparedStatement statement = cnx.prepareStatement(sql)) {
                statement.setDate(1, new java.sql.Date(currentDate.getTime()));
                statement.setDate(2, new java.sql.Date(endDate.getTime()));

                // Execute the update query
                int rowsAffected = statement.executeUpdate()    ;
                System.out.println(rowsAffected + " Oeuvres were updated with promotions.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to apply promotions to Oeuvres.");
        }
    }*/
    public void applyPromotions() {
        // Get the current date
        Date currentDate = new Date();

        // Calculate the end date of the promotion period (15 days from the current date)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 15); // Add 15 days
        Date endDate = calendar.getTime();

        // SQL query to update the price of Oeuvres within the promotion period
        String sql = "UPDATE oeuvre SET prix = ? WHERE STR_TO_DATE(date, '%d-%m-%Y') <= ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            // Set the new price (for example, reducing the price by 15%)
            float discountPercentage = 0.15f; // 15% discount

            // Loop through each Oeuvre and check if it falls within the promotion period
            for (Oeuvre oeuvre : getAllOeuvres()) {
                // Parse the string date into a Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date oeuvreDate = dateFormat.parse(oeuvre.getDate());

                // If the date of disposition is within the promotion period
                if (oeuvreDate.compareTo(currentDate) >= 0 && oeuvreDate.compareTo(endDate) <= 0) {
                    // Calculate the new price with the discount
                    float newPrice = oeuvre.getPrix() * (1 - discountPercentage);

                    // Update the price in the database
                    statement.setFloat(1, newPrice);
                    statement.setDate(2, new java.sql.Date(oeuvreDate.getTime())); // Assuming date is stored as java.util.Date
                    statement.executeUpdate();

                    // Send a notification
                    sendNotification("Promotion applied to Oeuvre with ID " + oeuvre.getId_oeuvre() + ": New price is $" + newPrice);
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            System.err.println("Failed to apply promotions to Oeuvres.");
        }
    }

    // Method to send a notification (replace this with appropriate notification mechanism)
    private void sendNotification(String message) {
        JOptionPane.showMessageDialog(null, message, "Promotion Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}
