package mappers;

import entity.CityInfo;

public class CityInfoDTO {
    private String city;
    private String zipCode;

    public CityInfoDTO(CityInfo ci) {
        this.city = ci.getCity();
        this.zipCode = ci.getZip();
    }
    
}
