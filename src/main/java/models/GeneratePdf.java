package models;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import models.ReservationMusee;
import services.ServiceReservation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratePdf {

    public static void generatePDF(String nomMusee, File file) {
        // Récupérer le musée sélectionné à partir du ChoiceBox
        String museeSelectionne = nomMusee;

        // Obtenez la date courante
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);

        // Définir les marges et les espacements
        float margin = 50;
        float startX = 50;
        float startY = 750;
        float endY = 50;
        float lineSpacing = 20;

        // Créer un nouveau document PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Créer un flux de contenu pour écrire dans le document PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Ajouter le titre avec la date actuelle
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText("Liste des utilisateurs ayant réservé au musée " + museeSelectionne + " - " + formattedDate);
                contentStream.endText();

                // Déplacer vers une nouvelle ligne
                startY -= lineSpacing * 2;

                // Récupérer les utilisateurs qui ont réservé dans le musée sélectionné
                ServiceReservation serviceReservation = new ServiceReservation();
                ObservableList<ReservationMusee> reservations = serviceReservation.getReservationsByMuseum(museeSelectionne);

                // Afficher les informations des utilisateurs dans le document PDF
                for (ReservationMusee reservation : reservations) {
                    startY -= lineSpacing;
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.showText("Nom: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(reservation.getUser().getUser_name() + "    ");
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12); // Set font to Helvetica Bold
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.showText("Nombre de tickets réservés: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(String.valueOf(reservation.getNbr_tickets_reserves()));
                    contentStream.newLine();

                    contentStream.endText(); // <- Ajout de cette ligne pour terminer le texte
                }
            }

            // Sauvegarder le document PDF
            document.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
