package Api.Earthquake.dataAccess;

import Api.Earthquake.dtos.Earthquake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class EarthquakeRepository {
    private final String URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";

    public List<Earthquake> getAll(int countOfDays, String country) throws IOException {
        List<Earthquake> earthquakes = new ArrayList<>();

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().minusDays(countOfDays);
        URL url = new URL(this.URL + "&starttime=" + startDate + "&endtime=" + endDate + "&orderby=time-asc");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == 200) {
            Scanner scanner = new Scanner(url.openStream());

            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                inputLine = scanner.nextLine();
                stringBuilder.append(inputLine);
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            List<String> list = new ArrayList<String>();
            JSONArray jsonArray = jsonObject.getJSONArray("features");
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(String.valueOf(jsonArray.getJSONObject(i).getJSONObject("properties")));
            }
            for (int i = 0; i < list.size(); i++) {
                Earthquake earthquake = new Earthquake();
                JSONObject object = new JSONObject(list.get(i));
                try {
                    if (object.getString("place").endsWith(country)) {
                        earthquake.setMagnitude(object.getFloat("mag"));
                        earthquake.setTime(object.getLong("time"));
                        earthquake.setCountry(object.getString("place"));
                        earthquakes.add(earthquake);
                    }
                }catch (JSONException e) {

                }
            }
        }else  {
            return null;
        }

        return earthquakes;

    }



}
