package models;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javafx.collections.ObservableList;


import java.io.File;
import java.io.IOException;


public class GeneratePdf {

        public static void generatePDF(ObservableList<Livreur> livreurs, File file) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    float startX = 50;
                    float startY = 750;
                    float lineSpacing = 30;
                    float currentY = startY;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, currentY);
                    contentStream.showText("Liste des livreurs");
                    contentStream.newLineAtOffset(0, -lineSpacing * 2);
                    contentStream.endText();

                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    for (Livreur livreur : livreurs) {
                        currentY -= lineSpacing * 2; // Increase line spacing to give more room for each entry
                        contentStream.beginText();
                        contentStream.newLineAtOffset(startX, currentY);
                        contentStream.showText("Nom: " + livreur.getNom_livreur() +"    "+"/Matricule: "+livreur.getMatricule()+"    " +"/Adresse:"+ livreur.getAdresse());
                      /*  contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.showText("Prénom: " + livreur.getPrenom_livreur());
                        contentStream.newLineAtOffset(0, -lineSpacing);

                        contentStream.showText("Adresse: " + livreur.getAdresse());
                        contentStream.newLineAtOffset(0, -lineSpacing);

                        contentStream.showText("Téléphone: " + livreur.getTelephone());
                        contentStream.newLineAtOffset(0, -lineSpacing);

                        contentStream.showText("Matricule: " + livreur.getMatricule());
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.showText("--------------------------------------");
                        contentStream.newLineAtOffset(0, -lineSpacing);
                        contentStream.newLineAtOffset(0, -lineSpacing); // Add extra space between entries*/
                        contentStream.endText();
                    }
                }

                document.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }

