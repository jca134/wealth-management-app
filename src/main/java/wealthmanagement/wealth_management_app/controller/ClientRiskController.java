package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.ClientRisk;
import wealthmanagement.wealth_management_app.repository.ClientPageRepository;
import wealthmanagement.wealth_management_app.repository.ClientRiskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client-risk")
public class ClientRiskController {

    private final ClientRiskRepository clientRiskRepository;
    private final ClientPageRepository clientPageRepository;

    public ClientRiskController(ClientRiskRepository clientRiskRepository,
                                ClientPageRepository clientPageRepository) {
        this.clientRiskRepository = clientRiskRepository;
        this.clientPageRepository = clientPageRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("risks", clientRiskRepository.findAll());
        return "client-risk/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("risk", new ClientRisk());
        model.addAttribute("clients", clientPageRepository.findAllClientOptions());
        model.addAttribute("profiles", clientRiskRepository.findAllProfileNames());
        return "client-risk/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        ClientRisk risk = clientRiskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Assessment not found: " + id));
        model.addAttribute("risk", risk);
        model.addAttribute("clients", clientPageRepository.findAllClientOptions());
        model.addAttribute("profiles", clientRiskRepository.findAllProfileNames());
        return "client-risk/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ClientRisk risk) {
        if (risk.getAssessmentId() == null) {
            clientRiskRepository.insert(risk);
        } else {
            clientRiskRepository.update(risk);
        }
        return "redirect:/client-risk";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        clientRiskRepository.delete(id);
        return "redirect:/client-risk";
    }
}
