package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.input.InputMethodEvent;

import services.ServiceMusee;

public class WeatherFXML {
    @FXML
    private Label nameCity;
    @FXML
    private TextField enterCity;
    @FXML
    private Text temperatureFX;
    @FXML
    private Button exitBtn;


    @FXML
    private Button buttonSearch;
   private String city;
    /*
        @FXML
        private RadioButton metric;
        @FXML
        private RadioButton imperial;
    */
    @FXML
    private Label dateTime;
    @FXML
    private ImageView temp_C_F;


    @FXML
    void ternOnEnter(InputMethodEvent event) {

    }
    @FXML
    void exitClicked(MouseEvent event) {
        Stage stage = (Stage) nameCity.getScene().getWindow();
        stage.close();
    }
    @FXML
    void setData1(String city) {
        this.city = city;
        System.out.println(city);
        System.out.println("aa1");

        // Call the method to fetch weather data here
        fetchWeatherData(city);
    }
    private void fetchWeatherData(String city) {
        if (city != null && !city.isEmpty()) {
            String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=c7d90c67450ed7d8990c1553a9cb5d57&units=" + temperatureSwitch);
            if (!output.isEmpty()) {
                JSONObject obj = new JSONObject(output);
                temperatureFX.setText(obj.getJSONObject("main").getInt("temp") + iconT);
                nameCity.setText(obj.getString("name"));

                // Check for weather conditions
                JSONArray weatherArray = obj.getJSONArray("weather");
                for (int i = 0; i < weatherArray.length(); i++) {
                    JSONObject weatherObj = weatherArray.getJSONObject(i);
                    String mainWeather = weatherObj.getString("main");
                    // Update ImageView based on weather condition
                    if (mainWeather.equals("Rain")) {
                        // Load and display rain image
                        temp_C_F.setImage(new Image("/assets/rain.png"));
                    } else if (mainWeather.equals("Clear")) {
                        // Load and display clear image
                        temp_C_F.setImage(new Image("/assets/clear.png"));
                    } else if (mainWeather.equals("Clouds")) {
                        // Load and display cloudy image
                        temp_C_F.setImage(new Image("/assets/cloudy.png"));
                    }
                }

                // Resize the image
                temp_C_F.setFitWidth(100); // Set the width to 100 pixels
                temp_C_F.setFitHeight(100); // Set the height to 100 pixels
            }
        }
    }


    //private String temperature;
    private String iconT = "°C";;
    private String temperatureSwitch = "metric";


    @FXML
    void initialize() {
        // Remove window decorations


// SWITCH (°C/°F)
        temp_C_F.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            boolean check;
            @Override
            public void handle(MouseEvent event) {
                if (check) {
                    check = false;
                    temperatureSwitch = "metric";
                    iconT = "°C";
                } else {
                    check = true;
                    temperatureSwitch = "imperial";
                    iconT = "°F";
                }
            }
        });


// DATE
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM, E");
        String formattedDate = myDateObj.format(myFormatObj);

        dateTime.setText(String.valueOf(formattedDate));


        temperatureFX.setText(iconT);


    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();

        } catch (Exception e) {
            System.out.println("This city not fained!");
        }
        return content.toString();
    }
}

