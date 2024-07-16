package hr.dice.luka_jurkic.utils;

import hr.dice.luka_jurkic.service.exceptions.ImageServiceException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    private static final String IMAGE_DATA_TYPE = "image";
    private static final Parser PARSER = new AutoDetectParser();
    private static final ContentHandler HANDLER = new BodyContentHandler();
    private static final Metadata METADATA = new Metadata();
    private static final ParseContext PARSE_CONTEXT = new ParseContext();

    public static void validateFile(byte[] data) {
        InputStream dataInputStream = new ByteArrayInputStream(data);

        try {
            PARSER.parse(dataInputStream, HANDLER, METADATA, PARSE_CONTEXT);
            MediaType mediaType = MediaType.parse(METADATA.get(Metadata.CONTENT_TYPE));
            if (!mediaType.getType().startsWith(ImageUtils.IMAGE_DATA_TYPE)) {
                throw ImageServiceException.wrongContentType();
            }
        }
        catch (IOException | SAXException | TikaException e) {
            throw ImageServiceException.notSupportedFormat();
        }
    }
}
