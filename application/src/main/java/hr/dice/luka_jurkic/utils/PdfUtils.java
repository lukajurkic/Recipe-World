package hr.dice.luka_jurkic.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeIngredientEntity;
import hr.dice.luka_jurkic.persistence.entity.StepEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PdfUtils {

    public void deleteOldPdfFiles() {
        System.out.println("Finding all .pdf files . . .");
        List<Path> result = findAllPdfs();
        System.out.println("Deleting all .pdf files . . .");
        for (Path fileName : result) {
            File file = new File(fileName.toString());
            if (file.delete()) {
                System.out.println("File deleted successfully");
            }
            else {
                System.out.println("Failed to delete the file " + fileName);
            }
        }
    }

    public String getFileName(RecipeEntity recipe) {
        LocalDate date = LocalDate.now();
        return recipe.getName() + "_" + recipe.getUser().getUsername() + "_" + date.getYear() + date.getMonthValue() + date.getDayOfMonth() + ".pdf";
    }


    public void export(RecipeEntity recipe) {
        String documentTitle = getFileName(recipe);
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(documentTitle));
        }
        catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            document.open();
            buildDocument(document, recipe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }
    }

    private void buildDocument(Document document, RecipeEntity recipe) throws DocumentException, IOException {

        Font font;
        Paragraph paragraph;
        BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1250, BaseFont.EMBEDDED);
        BaseFont times = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);

        font = new Font(courier, 24, Font.BOLD, BaseColor.BLACK);
        paragraph = new Paragraph();
        paragraph.add(new Chunk(recipe.getName(), font));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        font = new Font(times, 12, Font.ITALIC, BaseColor.BLACK);
        paragraph = new Paragraph();
        paragraph.add(new Chunk("by: " + recipe.getUser().getUsername(), font));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add("\r\n");
        document.add(paragraph);

        document.add(new Chunk(new LineSeparator()));
        document.add(new Paragraph("\r\n"));

        font = new Font(courier, 21, Font.NORMAL, BaseColor.BLACK);
        paragraph = new Paragraph();
        paragraph.add(new Chunk("Ingredients:", font));
        document.add(new Paragraph("\r\n"));
        paragraph.add("\r\n");
        font = new Font(courier, 16, Font.NORMAL, BaseColor.BLACK);
        for(RecipeIngredientEntity ingredient : recipe.getIngredients()) {
            paragraph.add(new Chunk(ingredient.getIngredient().getName() + " - " +
                    ingredient.getAmount() + " " + ingredient.getUnit(), font));
            paragraph.add("\r\n");
        }
        document.add(paragraph);
        document.add(new Paragraph("\r\n"));

        document.add(new Chunk(new LineSeparator()));
        document.add(new Paragraph("\r\n"));

        font = new Font(courier, 21, Font.NORMAL, BaseColor.BLACK);
        paragraph = new Paragraph();
        paragraph.add(new Chunk("Steps:", font));
        paragraph.add("\r\n");
        for(StepEntity step : recipe.getSteps().stream().sorted(Comparator.comparing(StepEntity::getNumber)).toList()) {
            paragraph.add(new Chunk(step.getNumber() + ". " + step.getDescription()));
            paragraph.add("\r\n");
        }
        document.add(paragraph);

        document.add(new Chunk(new LineSeparator()));

        if(!recipe.getImages().isEmpty()) {
            addImage(document, recipe.getImages().get(0).getPictureData(), courier);
        }
    }

    private void addImage(Document document, byte[] pictureData, BaseFont baseFont) throws DocumentException, IOException {
        Image image = Image.getInstance(pictureData);
        image.scaleToFit(200, 200);
        Font font = new Font(baseFont, 21, Font.NORMAL, BaseColor.BLACK);
        document.add(new Paragraph("Image of recipe", font));
        document.add(image);
    }

    private List<Path> findAllPdfs() {
        List<Path> result = null;
        try(Stream<Path> walk = Files.walk(Path.of(""))) {
            result = walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".pdf"))
                    .toList();
        } catch (IOException ignored){}
        return result;
    }
}
