package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Passenger;
import softuni.exam.models.Town;

import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town,Integer> {

    Optional<Town> findByName(String town);
}
