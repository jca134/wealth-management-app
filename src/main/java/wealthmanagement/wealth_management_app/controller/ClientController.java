package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.repository.ClientPageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    private final ClientPageRepository clientPageRepository;

    public ClientController(ClientPageRepository clientPageRepository) {
        this.clientPageRepository = clientPageRepository;
    }

    @GetMapping("/clients")
    public String showClientsPage(Model model) {
        model.addAttribute("clients", clientPageRepository.findAllClientsWithAdvisors());
        return "clients";
    }
}