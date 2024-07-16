package hr.dice.luka_jurkic.service.exceptions;

import hr.dice.luka_jurkic.service.exceptions.base.ServiceException;
import hr.dice.luka_jurkic.service.exceptions.errors.ImageErrorKey;
import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import org.springframework.http.HttpStatus;

public class ImageServiceException extends ServiceException {

    protected ImageServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        super(errorKey, httpStatus);
    }

    protected ImageServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(errorKey, httpStatus, message);
    }
    
    public static ImageServiceException notFound() {
        throw new ImageServiceException(ImageErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static ImageServiceException notFound(String message) {
        throw new ImageServiceException(ImageErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }

    public static ImageServiceException sizeTooLarge() {
        throw new ImageServiceException(ImageErrorKey.SIZE_TOO_LARGE, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    public static ImageServiceException sizeTooLarge(String message) {
        throw new ImageServiceException(ImageErrorKey.SIZE_TOO_LARGE, HttpStatus.PAYLOAD_TOO_LARGE, message);
    }

    public static ImageServiceException notSupportedFormat() {
        throw new ImageServiceException(ImageErrorKey.NOT_SUPPORTED_FORMAT, HttpStatus.BAD_REQUEST);
    }

    public static ImageServiceException notSupportedFormat(String message) {
        throw new ImageServiceException(ImageErrorKey.NOT_SUPPORTED_FORMAT, HttpStatus.BAD_REQUEST, message);
    }

    public static ImageServiceException wrongContentType() {
        throw new ImageServiceException(ImageErrorKey.WRONG_CONTENT_TYPE, HttpStatus.BAD_REQUEST);
    }

    public static ImageServiceException wrongContentType(String message) {
        throw new ImageServiceException(ImageErrorKey.WRONG_CONTENT_TYPE, HttpStatus.BAD_REQUEST, message);
    }
}
