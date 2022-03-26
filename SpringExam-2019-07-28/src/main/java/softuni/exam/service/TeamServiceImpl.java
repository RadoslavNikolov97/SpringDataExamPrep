package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.entities.dto.TeamDto;
import softuni.exam.domain.entities.dto.TeamRootDto;
import softuni.exam.domain.entities.dto.UrlDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private PictureRepository pictureRepository;
    private FileUtil fileUtil;
    private ModelMapper modelMapper;
    private final String path = "src/main/resources/files/xml/teams.xml";
    private Validator validate;


    public TeamServiceImpl(TeamRepository teamRepository, FileUtil fileUtil, Validator validate, PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;

        this.fileUtil = fileUtil;
        this.validate = validate;
        this.modelMapper = modelMapper;
    }

    @Override
    public String importTeams() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(TeamRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        TeamRootDto teamRootDto = (TeamRootDto) unmarshaller.unmarshal(
                new FileReader(path));

        return teamRootDto.getTeams().stream().map(this::importTeam).collect(Collectors.joining("\n"));
    }

    private String  importTeam(TeamDto teamDto) {
        Set<ConstraintViolation<TeamDto>> errors =this.validate.validate(teamDto);
        Set<ConstraintViolation<UrlDto>> urlErrors = this.validate.validate(teamDto.getPicture());

        if (!errors.isEmpty()){
            return "Invalid Team";
        }
        if (!urlErrors.isEmpty()) {
            return "Invalid Team";
        }

        String teamName = teamDto.getName();
        Optional<Picture> picture = this.pictureRepository.findByUrl(teamDto.getPicture().getUrl());

        if (picture.isEmpty()){
            return "Invalid Team";
        }

        Team team = new Team();
        team.setName(teamName);
        team.setPicture(picture.get());


        teamRepository.save(team);



        return  String.format("Successfully imported team - %s" ,teamDto.getName());
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {

        return fileUtil.readFile(path);
    }
}
