package com.example.adocaoanimais.adocaoanimais.repository;

import com.example.adocaoanimais.adocaoanimais.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByAdotado(boolean adotado);
}
