package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Pictures;
import softuni.exam.instagraphlite.models.dto.jsonDTO.PictureDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository pictureRepository;

    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;

    private final String path = "src/main/resources/files/pictures.json";

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, Validator validator) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importPictures() throws IOException {
        String json = this.readFromFileContent();
        StringBuilder sb = new StringBuilder();

        PictureDTO[] pictureDTOS = gson.fromJson(json, PictureDTO[].class);

        for (PictureDTO pictureInfo : pictureDTOS) {
            Set<ConstraintViolation<PictureDTO>> errors = validator.validate(pictureInfo);

            if (errors.isEmpty()){

                Pictures picture = modelMapper.map(pictureInfo,Pictures.class);

                sb.append(String.format("Successfully imported Picture, with size %.2f",picture.getSize()));

                this.pictureRepository.save(picture);
            }

            else {
                sb.append("Invalid Picture");
            }

            sb.append(System.lineSeparator());

        }
        return sb.toString();
    }

    @Override
    public String exportPictures() {
        double sizeToGet = 30000;
        StringBuilder sb = new StringBuilder();
        
       List<Pictures> pics =  pictureRepository.findAllBySizeGreaterThan(sizeToGet);

        for (Pictures pic :
                pics) {
            sb.append(String.format("%.2f - %s", pic.getSize(), pic.getPath())).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
