package com.example.adocaoanimais.adocaoanimais.controller;

import com.example.adocaoanimais.adocaoanimais.model.Animal;
import com.example.adocaoanimais.adocaoanimais.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @PostMapping("/animais")
    public String saveAnimal(@ModelAttribute Animal animal, Model model) {
        try {
            animalRepository.save(animal);
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar o animal: " + e.getMessage());
            return "adicionar-animal"; // Corrigido o nome do template
        }
        return "redirect:/animais"; // Redireciona para a lista de animais
    }

    @GetMapping("/animais")
    public String listAnimais(Model model) {
        List<Animal> animais = animalRepository.findAll();
        model.addAttribute("animais", animais);
        return "animais-disponiveis";
    }

    @GetMapping("/animais/adotados")
    public String listAnimaisAdotados(Model model) {
        List<Animal> animaisAdotados = animalRepository.findByAdotado(true);
        model.addAttribute("animaisAdotados", animaisAdotados);
        return "listar-animais"; // O nome do template HTML para listar animais adotados
    }

    @GetMapping("/animais/cadastrar")
    public String showCadastroForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "adicionar-animal"; // O nome do template HTML deve corresponder ao arquivo "adicionar-animal.html"
    }

    @GetMapping("/animais/adotar/{id}")
    public String adotarAnimal(@PathVariable Long id, Model model) {
        try {
            // Procura o animal pelo ID
            Animal animal = animalRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado com o ID: " + id));

            // Define o animal como adotado
            animal.setAdotado(true);
            animalRepository.save(animal); // Salva as alterações

            // Adiciona uma mensagem de sucesso na model
            model.addAttribute("message", "Animal adotado com sucesso!");
        } catch (Exception e) {
            // Em caso de erro, adiciona uma mensagem de erro e retorna para a lista de animais
            model.addAttribute("error", "Erro ao adotar o animal: " + e.getMessage());
            return "animais-disponiveis"; // Volta para a página de listagem se der erro
        }
        return "redirect:/animais"; // Redireciona para a lista de animais após adoção bem-sucedida
    }

    // Método para mostrar o formulário de edição
    @GetMapping("/animais/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado com o ID: " + id));
        model.addAttribute("animal", animal);
        return "editar-animal"; // O nome do template HTML para editar animal
    }

    // Método para atualizar o animal
    @PostMapping("/animais/atualizar/{id}")
    public String updateAnimal(@PathVariable Long id, @ModelAttribute Animal animal, Model model) {
        try {
            animal.setId(id); // Certifique-se de que o ID é mantido
            animalRepository.save(animal);
            model.addAttribute("message", "Animal atualizado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar o animal: " + e.getMessage());
            return "editar-animal"; // Retorna ao formulário de edição em caso de erro
        }
        return "redirect:/animais"; // Redireciona para a lista de animais após a atualização
    }

    // Método para excluir o animal
    @GetMapping("/animais/excluir/{id}")
    public String deleteAnimal(@PathVariable Long id, Model model) {
        try {
            Animal animal = animalRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado com o ID: " + id));
            animalRepository.delete(animal);
            model.addAttribute("message", "Animal excluído com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao excluir o animal: " + e.getMessage());
        }
        return "redirect:/animais"; // Redireciona para a lista de animais após a exclusão
    }

    // Método para mostrar os detalhes do animal
    @GetMapping("/animais/detalhes/{id}")
    public String viewAnimalDetails(@PathVariable Long id, Model model) {
        try {
            Animal animal = animalRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado com o ID: " + id));
            model.addAttribute("animal", animal);
            return "detalhes-animal"; // O nome do template HTML para detalhes do animal
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao buscar detalhes do animal: " + e.getMessage());
            return "animais-disponiveis"; // Redireciona para a lista de animais em caso de erro
        }
    }

    // Método para buscar um animal pelo ID
    @GetMapping("/animais/buscar")
    public String searchAnimalById(@RequestParam Long id, Model model) {
        try {
            Animal animal = animalRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado com o ID: " + id));
            model.addAttribute("animal", animal);
            return "detalhes-animal"; // Exibe os detalhes do animal encontrado
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao buscar o animal: " + e.getMessage());
            return "animais-disponiveis"; // Redireciona para a lista de animais em caso de erro
        }
    }
}
