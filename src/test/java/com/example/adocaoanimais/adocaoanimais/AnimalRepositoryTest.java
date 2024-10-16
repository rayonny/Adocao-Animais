package com.example.adocaoanimais.adocaoanimais;

import com.example.adocaoanimais.adocaoanimais.model.Animal;
import com.example.adocaoanimais.adocaoanimais.repository.AnimalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void testSaveAnimal() {
        // Criando um novo objeto Animal
        Animal animal = new Animal();
        animal.setNome("Rex");
        animal.setEspecie("Cachorro");
        animal.setRaca("Labrador");
        animal.setIdade(3);
        animal.setDescricao("Amigável e brincalhão.");

        // Salvando o animal no repositório
        animalRepository.save(animal);

        // Verificando se o animal foi salvo
        assertThat(animalRepository.findById(animal.getId())).isPresent();
    }
}
