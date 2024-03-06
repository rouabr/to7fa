package controllers;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import java.lang.System;

public class MapFXML {

    @FXML
    private Label locationLabel;

    @FXML
    private WebView webView;

    @FXML
    void initialize() {
        assert locationLabel != null : "fx:id=\"locationLabel\" was not injected: check your FXML file 'MapFXML.fxml'.";
        assert webView != null : "fx:id=\"webView\" was not injected: check your FXML file 'MapFXML.fxml'.";

        // Enable JavaScript communication
        webView.getEngine().setJavaScriptEnabled(true);

        // Inject Java object to JavaScript
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webView.getEngine().executeScript("window");
                window.setMember("javaController", this); // "javaController" can be any name you choose
            }
        });

        // Load the HTML file
        webView.getEngine().load(getClass().getResource("/OpenStreetMap.html").toExternalForm());
    }

    // Method called from JavaScript to update the location label

    public void invokeJavaFunction(double lat, double lng) {
        // Update the label:
        System.out.println("Received location from JavaScript!");
        locationLabel.setText("Latitude: " + lat + ", Longitude: " + lng);

        // Print to console:
        System.out.println("Pin location: Latitude: " + lat + ", Longitude: " + lng);
    }
}