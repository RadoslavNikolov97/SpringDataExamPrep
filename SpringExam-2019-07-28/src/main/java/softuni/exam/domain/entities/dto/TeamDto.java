package softuni.exam.domain.entities.dto;

import softuni.exam.domain.entities.Picture;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TeamDto {

    @XmlElement
    @NotNull
    @Size(min = 3, max = 20)
    private String name;
    @XmlElement(name = "picture")
    @NotNull
    private UrlDto pictureDto;

    public TeamDto() {}

    public String getName() {
        return name;
    }

    public UrlDto getPicture() {
        return pictureDto;
    }

}
