package com.example.football.repository;


import com.example.football.models.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<Stat,Integer> {

    @Query ("SELECT s FROM Stat AS s where s.shooting = :shooting AND s.passing = :passing AND s.endurance = :endurance")
    Optional<Stat> findBYShootingPassingEndurance(double shooting, double passing, double endurance);
}
