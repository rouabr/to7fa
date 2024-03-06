package to7fa.controllers;

import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import to7fa.entities.evenement;


import java.io.File;
import java.io.IOException;

public class PDF {

    public static void generatePDF(ObservableList<evenement> events, File file) {
        // Set margins and spacing
        float margin = 50;
        float startX = 50;
        float startY = 750;
        float endY = 50;
        float lineSpacing = 20;

        // Create a new PDF document
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream to write to the PDF document
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Add the title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText("Liste des évènements");
                contentStream.endText();

                // Move to a new line
                startY -= lineSpacing * 2;

                // Display event information in the PDF document
                for (evenement event : events) {
                    startY -= lineSpacing;
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.showText("Nom de l'évènement: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(event.getNom_event() + "    ");
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.showText("Description: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(event.getDescription_event() + "    ");
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.showText("Prix: ");
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText(String.valueOf(event.getPrix_event()));
                    contentStream.newLine();

                    contentStream.endText();
                }
            }

            // Save the PDF document
            document.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
