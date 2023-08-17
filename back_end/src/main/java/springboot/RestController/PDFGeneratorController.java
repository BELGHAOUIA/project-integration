package springboot.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import springboot.RestController.impl.PDFGeneratorImpl;
import springboot.Service.generator.PDFGeneratorService;

@CrossOrigin
@RestController
public class PDFGeneratorController implements PDFGeneratorImpl {

    @Autowired
    PDFGeneratorService pdfGeneratorService;
    @Override
    public ResponseEntity<InputStreamResource> generatePDF(String numeroSalle) {
        return pdfGeneratorService.generatePDF(numeroSalle);
    }
}
