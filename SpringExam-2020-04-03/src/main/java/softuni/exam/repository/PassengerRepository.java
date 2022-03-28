package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Integer> {

    Optional<Passenger> findByEmail(String email);


    @Query("SELECT p FROM Passenger AS p JOIN Ticket As t on t.passenger.id = p.id GROUP BY p.id ORDER BY COUNT(t.id) DESC , p.email DESC")
    List<Passenger> findAllOrderByTicketsCountDescAndByEmailDesc();

    @Query("SELECT count(t.id) FROM Passenger AS p JOIN Ticket AS t ON t.passenger.id = p.id WHERE p.id = :id")
    int countTicketsByPassengerId(int id);
}
