package Api.Earthquake.dtos.responses;

import Api.Earthquake.dtos.EarthquakeDto;
import lombok.Data;

import java.util.List;

@Data
public class EarthquakeResponse {
    private String message;
    private List<EarthquakeDto> earthquakeDtos;
}
