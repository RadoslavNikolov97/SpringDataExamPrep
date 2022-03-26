package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.dto.PictureDto;
import softuni.exam.domain.entities.dto.PictureRootDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository pictureRepository;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private final String path = "src/main/resources/files/xml/pictures.xml";
    private Validator validate;


    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, FileUtil fileUtil,Validator validate)  {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validate = validate;


    }

    @Override
    public String importPictures() throws JAXBException, FileNotFoundException {

        JAXBContext context = JAXBContext.newInstance(PictureRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PictureRootDto pictureRootDto = (PictureRootDto) unmarshaller.unmarshal(
                new FileReader(path));

        return  pictureRootDto.getPictures().stream().map(this::importPicture).collect(Collectors.joining("\n"));
    }

    private String importPicture(PictureDto pictureDto) {
        Set<ConstraintViolation<PictureDto>> errors =this.validate.validate(pictureDto);
        if (!errors.isEmpty()){
            return "Invalid Picture";
        }
        Picture picture =  this.modelMapper.map(pictureDto,Picture.class);

        this.pictureRepository.save(picture);

        return String.format("Successfully imported picture - %s" ,pictureDto.getUrl());
    }

    @Override
    public boolean areImported() {

        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException  {

        return fileUtil.readFile(path);
    }



}
