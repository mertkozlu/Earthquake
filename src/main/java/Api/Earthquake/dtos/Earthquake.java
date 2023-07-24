package Api.Earthquake.dtos;

import lombok.Data;

@Data
public class Earthquake {
    private String country;
    private Float magnitude;
    private Long time;
}
