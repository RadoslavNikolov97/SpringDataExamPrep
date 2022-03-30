package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    @Query("SELECT p FROM Player AS p WHERE p.firstName = :firstName AND p.lastName = :lastName ")
    Optional<Player> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT p FROM Player AS p WHERE (p.birth > :after AND p.birth < :before) ORDER BY p.stats.shooting DESC ,p.stats.passing DESC , p.stats.endurance DESC , p.lastName asc ")
    List<Player> findAllByBirthAfterAndBirthBefore(LocalDate after, LocalDate before);
}
