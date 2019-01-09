package mappers;

import entity.Hobby;

public class HobbyDTO {
    private String name;
    private String description;

    public HobbyDTO(Hobby hobby) {
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }   
    
}
