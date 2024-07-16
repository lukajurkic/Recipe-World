package hr.dice.luka_jurkic.rest.base;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BaseImage {

    private byte[] pictureData;

    @Size(max = 1024)
    private String description;
}
