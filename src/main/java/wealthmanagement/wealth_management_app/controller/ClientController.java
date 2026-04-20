package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.Client;
import wealthmanagement.wealth_management_app.repository.ClientPageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/clients/new")
    public String showNewClientPage(Model model) {
        model.addAttribute("newClient", new Client());
        return "new-client";
    }

    @PostMapping("/clients/add")
    public String addClient(@ModelAttribute("newClient") Client newClient) {
        clientPageRepository.addClient(newClient);
        return "redirect:/clients";
    }

    @GetMapping("/clients/edit/{id}")
    public String showEditClientPage(@PathVariable("id") int clientId, Model model) {
        model.addAttribute("client", clientPageRepository.findClientById(clientId));
        return "edit-client";
    }

    @PostMapping("/clients/update")
    public String updateClient(@ModelAttribute("client") Client client) {
        clientPageRepository.updateClient(client);
        return "redirect:/clients";
    }

    @PostMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable("id") int clientId) {
        clientPageRepository.deleteClient(clientId);
        return "redirect:/clients";
    }
}