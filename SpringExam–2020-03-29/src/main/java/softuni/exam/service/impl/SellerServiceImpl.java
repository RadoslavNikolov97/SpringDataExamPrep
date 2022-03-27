package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import softuni.exam.models.Seller;
import softuni.exam.models.dto.xmlDTO.seller.SellerDTO;
import softuni.exam.models.dto.xmlDTO.seller.SellerRootDTO;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.impl.ValidatorUtilImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;

    private final String path = "src/main/resources/files/xml/sellers.xml";

    private ModelMapper modelMapper;
    private Validator validator;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, Validator validator) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(SellerRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        SellerRootDTO sellerRootDTO = (SellerRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return sellerRootDTO.getOffers().stream().map(this::importSeller).collect(Collectors.joining("\n"));
    }

    private String importSeller(SellerDTO sellerDTO) {

     Set<ConstraintViolation<SellerDTO>> errors = validator.validate(sellerDTO);

     if (errors.isEmpty()){
         Seller seller = modelMapper.map(sellerDTO,Seller.class);
         sellerRepository.save(seller);
         return String.format("Successfully import seller %s - %s",seller.getFirstName(),seller.getEmail());
     }
     else {
         return "Invalid Seller";
     }
    }
}
