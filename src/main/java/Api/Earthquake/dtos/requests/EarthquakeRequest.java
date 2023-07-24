package Api.Earthquake.dtos.requests;

import lombok.Data;

@Data
public class EarthquakeRequest {
    private int countOfDays;
    private String country;
}
