package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Offer;
import softuni.exam.models.Seller;
import softuni.exam.models.dto.xmlDTO.offer.OfferDTO;
import softuni.exam.models.dto.xmlDTO.offer.OfferRootDTO;
import softuni.exam.models.dto.xmlDTO.seller.SellerDTO;
import softuni.exam.models.dto.xmlDTO.seller.SellerRootDTO;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class OfferServiceImpl implements OfferService {

    private SellerRepository sellerRepository;
    private OfferRepository offerRepository;
    private CarRepository carRepository;


    private final String path = "src/main/resources/files/xml/offers.xml";

    private ModelMapper modelMapper;
    private Validator validator;

    public OfferServiceImpl(SellerRepository sellerRepository, OfferRepository offerRepository, CarRepository carRepository, ModelMapper modelMapper, Validator validator) {
        this.sellerRepository = sellerRepository;
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(OfferRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        OfferRootDTO sellerRootDTO = (OfferRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return sellerRootDTO.getOffers().stream().map(this::importOffer).collect(Collectors.joining("\n"));

    }

    private String importOffer(OfferDTO offerDTO) {

        Set<ConstraintViolation<OfferDTO>> errors = validator.validate(offerDTO);

        if (errors.isEmpty()){
            Optional<Seller> seller = sellerRepository.findById(offerDTO.getSellerId().getId());
            Optional<Car> car = carRepository.findById(offerDTO.getCarId().getId());
            if (seller.isPresent() && car.isPresent()){
                Offer offer = new Offer();
                String description = offerDTO.getDescription();
                double price = offerDTO.getPrice();
                LocalDateTime addedOn = modelMapper.map(offerDTO.getAddedOn(),LocalDateTime.class);
                boolean hasGoldStandard = offerDTO.isHasGoldStatus();

                offer.setDescription(description);
                offer.setPrice(price);
                offer.setAddedOn(addedOn);
                offer.setHasGoldStatus(hasGoldStandard);
                offer.setCar(car.get());
                offer.setSeller(seller.get());

                offerRepository.save(offer);

                return String.format("Successfully import offer %s - %b",offer.getAddedOn(),offer.isHasGoldStatus());
            }
            else {
                return "Invalid Offer";
            }
        }
        else {
            return "Invalid Offer";
        }

    }
}
