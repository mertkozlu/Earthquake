package Api.Earthquake.service;

import Api.Earthquake.dataAccess.EarthquakeRepository;
import Api.Earthquake.dtos.EarthquakeDto;
import Api.Earthquake.dtos.requests.EarthquakeRequest;
import Api.Earthquake.dtos.responses.EarthquakeResponse;
import Api.Earthquake.dtos.Earthquake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EarthquakeService {
    private final EarthquakeRepository earthquakeRepository;

    public EarthquakeService(EarthquakeRepository earthquakeRepository) {
        this.earthquakeRepository = earthquakeRepository;
    }

    public EarthquakeResponse getAll(EarthquakeRequest earthquakeRequest) throws IOException {
        EarthquakeResponse earthquakeResponse = new EarthquakeResponse();

        if (!validateCountry(earthquakeRequest.getCountry()) || !validateCountryOfDays(earthquakeRequest.getCountOfDays())) {
            earthquakeResponse.setMessage("Parameters are invalid");
            return earthquakeResponse;
        }

        List<Earthquake> daoResult = earthquakeRepository.getAll(earthquakeRequest.getCountOfDays(), earthquakeRequest.getCountry());
        if (!checkResponseCodeIsNot200(daoResult)) {
            earthquakeResponse.setMessage("Response code is not 200");
            return earthquakeResponse;
        }

        if (!checkIfListIsEmpty(daoResult)) {
            earthquakeResponse.setMessage("No Earthquakes were recorded past " + earthquakeRequest.getCountOfDays() + "days.");
            return earthquakeResponse;

        }

        List<EarthquakeDto> earthquakeDtos = this.convertEntityToDto(daoResult);
        earthquakeResponse.setEarthquakeDtos(earthquakeDtos);
        earthquakeResponse.setMessage("Success");

        return earthquakeResponse;
    }

    public boolean validateCountry(String country) {
        boolean flag = true;
        if (StringUtils.isBlank(country)) {
            flag = false;
        }
        return flag;
    }

    public boolean validateCountryOfDays(int countOfDays) {
        boolean flag = true;
        if (countOfDays < 0) {
            flag = false;
        }
        return flag;
    }

    public boolean checkResponseCodeIsNot200(List<Earthquake> result) {
        boolean flag = true;
        if (result == null) {
            flag = false;
        }
        return flag;
    }

    public boolean checkIfListIsEmpty(List<Earthquake> result) {
        boolean flag = true;
        if (result.size() <= 0) {
            flag = false;
        }
        return flag;
    }

    public List<EarthquakeDto> convertEntityToDto(List<Earthquake> earthquakes) {
        List<EarthquakeDto> earthquakeDtos = new ArrayList<>();

        for (Earthquake e : earthquakes) {
            EarthquakeDto earthquakeDto = new EarthquakeDto();

            earthquakeDto.setCountry(e.getCountry());
            earthquakeDto.setMagnitude(e.getMagnitude());
            earthquakeDto.setDate(new Date(e.getTime()));

            earthquakeDtos.add(earthquakeDto);

        }
        return earthquakeDtos;
    }
}
