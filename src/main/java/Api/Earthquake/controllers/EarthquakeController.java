package Api.Earthquake.controllers;

import Api.Earthquake.dtos.requests.EarthquakeRequest;
import Api.Earthquake.dtos.responses.EarthquakeResponse;
import Api.Earthquake.service.EarthquakeService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class EarthquakeController {
    private final EarthquakeService earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @PostMapping("/getAll")
    public EarthquakeResponse getAll(@RequestBody EarthquakeRequest earthquakeRequest) throws IOException {
        return earthquakeService.getAll(earthquakeRequest);
    }
}
