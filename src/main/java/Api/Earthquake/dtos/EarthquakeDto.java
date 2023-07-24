package Api.Earthquake.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class EarthquakeDto {
    private String country;
    private Float magnitude;
    private Date date;
}
