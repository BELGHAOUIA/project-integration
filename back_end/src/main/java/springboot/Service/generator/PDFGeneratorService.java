package springboot.Service.generator;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springboot.Entity.Actif;
import springboot.Entity.Enum.TypeActif;
import springboot.Service.SalleService;

@Service
public class PDFGeneratorService {
    @Autowired
    SalleService salleService;

    public ResponseEntity<InputStreamResource> generatePDF(String numeroSalle) {
        List<Actif> values = salleService.fetchAllActifs(numeroSalle);
        boolean isProjecteur = salleService.getDataShow(numeroSalle);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Add text to top left corner
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 30);
            contentStream.showText("Departement Technologie");
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 50);
            contentStream.showText("de l'informatique");
            contentStream.endText();

            // Add text to top center
            contentStream.beginText();
            contentStream.setFont(PDType1Font.COURIER_OBLIQUE, 12);
            contentStream.newLineAtOffset((page.getMediaBox().getWidth() / 2) - 50, page.getMediaBox().getHeight() - 30);
            contentStream.showText(numeroSalle);
            contentStream.endText();

            // Add text to top right corner
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 150, page.getMediaBox().getHeight() - 30);
            contentStream.showText(LocalDate.now().toString());
            contentStream.endText();

            // Add table
            float tableTopY = 700;
            float tableBottomY = 50;
            float tableWidth = 500;
            float columnWidth = tableWidth / 3f;
            float y = tableTopY;

            // Add header row
            contentStream.drawLine(50, y, 550, y);
            y -= 20;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(55, y - 5);
            contentStream.showText("N° inventaire");
            contentStream.newLineAtOffset(columnWidth, 0);
            contentStream.showText("Article");
            contentStream.newLineAtOffset(columnWidth, 0);
            contentStream.showText("Remarque");
            contentStream.endText();

            // Add data rows
            contentStream.setFont(PDType1Font.HELVETICA, 10);
                y -= 20;
                contentStream.drawLine(50, y, 550, y);
                contentStream.beginText();
                contentStream.newLineAtOffset(55, y - 11);
                contentStream.showText("");
                contentStream.newLineAtOffset(columnWidth, 0);
                contentStream.showText("Table Ordinateur");
                contentStream.newLineAtOffset(columnWidth, 0);
                contentStream.showText(String.valueOf( salleService.fetchNombreTable(numeroSalle)));
                contentStream.endText();

            for (Actif value : values) {
                if(value.getType().equals(TypeActif.Unite) || value.getType().equals(TypeActif.Ecran))
                {
                    y -= 20;
                    contentStream.drawLine(50, y, 550, y);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(55, y - 11);
                    contentStream.showText(value.getReference());
                    contentStream.newLineAtOffset(columnWidth, 0);
                    contentStream.showText(value.getType() + " " + value.getModele());
                    contentStream.newLineAtOffset(columnWidth, 0);
                    contentStream.showText(String.valueOf(1));
                    contentStream.endText();
                }
            }

            y -= 20;
            contentStream.drawLine(50, y, 550, y);
            contentStream.beginText();
            contentStream.newLineAtOffset(55, y - 11);
            contentStream.showText("");
            contentStream.newLineAtOffset(columnWidth, 0);
            contentStream.showText("Projecteur");
            contentStream.newLineAtOffset(columnWidth, 0);
            if(isProjecteur)
                contentStream.showText(String.valueOf(1));
            else
                contentStream.showText(String.valueOf(0));

            contentStream.endText();
            y -= 20;
            contentStream.drawLine(50, y, 550, y);

            contentStream.moveTo(200,tableTopY);
            contentStream.lineTo(200, y);
            contentStream.stroke();

            contentStream.moveTo(370,tableTopY);
            contentStream.lineTo(370, y);
            contentStream.stroke();

            contentStream.moveTo(50,tableTopY);
            contentStream.lineTo(50, y);
            contentStream.stroke();

            contentStream.moveTo(550,tableTopY);
            contentStream.lineTo(550, y);
            contentStream.stroke();

            // Add signature image
            PDImageXObject signature = PDImageXObject.createFromFile("G:\\COURS_ISET\\sem2\\projet-d'integration\\IMG_6814__1_-removebg-preview.png", document);
            contentStream.drawImage(signature, 350, tableBottomY, 220, 70);

            contentStream.close();
            document.save(new File("G:\\COURS_ISET\\sem2\\projet-d'integration\\output.pdf"));
            System.out.println("PDF generated successfully!");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            byteArrayOutputStream.close();

            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(new ByteArrayInputStream(pdfBytes)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
        return null;
    }
}