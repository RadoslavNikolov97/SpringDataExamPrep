package com.example.football.models.dto.xml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerDTO {
    @XmlElement(name = "first-name")
    @NotNull
    @Size(min = 2)
    private String firstName;
    @XmlElement(name = "last-name")
    @NotNull
    @Size(min = 2)
    private String lastName;
    @XmlElement(name = "email")
    @NotNull
    @Email
    private String email;
    @XmlElement(name = "birth-date")
    @NotNull
    private String birth;
    @XmlElement(name = "position")
    @NotNull
    private String position;
    @XmlElement(name = "town")
    @NotNull
    private TownNameDTO townNameDTO;
    @XmlElement(name = "team")
    @NotNull
    private TeamNameDTO teamNameDTO;
    @XmlElement(name = "stat")
    @NotNull
    private StatIdDTO statIdDTO;

    public PlayerDTO() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TownNameDTO getTownNameDTO() {
        return townNameDTO;
    }

    public void setTownNameDTO(TownNameDTO townNameDTO) {
        this.townNameDTO = townNameDTO;
    }

    public TeamNameDTO getTeamNameDTO() {
        return teamNameDTO;
    }

    public void setTeamNameDTO(TeamNameDTO teamNameDTO) {
        this.teamNameDTO = teamNameDTO;
    }

    public StatIdDTO getStatIdDTO() {
        return statIdDTO;
    }

    public void setStatIdDTO(StatIdDTO statIdDTO) {
        this.statIdDTO = statIdDTO;
    }
}
