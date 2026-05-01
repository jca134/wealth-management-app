package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.Client;
import wealthmanagement.wealth_management_app.repository.ClientPageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientPageRepository clientPageRepository;

    public ClientController(ClientPageRepository clientPageRepository) {
        this.clientPageRepository = clientPageRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("clients", clientPageRepository.findAllClientsWithAdvisors());
        return "clients";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        Client client = clientPageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
        model.addAttribute("client", client);
        return "client-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Client client) {
        if (client.getClientId() == null) {
            clientPageRepository.insert(client);
        } else {
            clientPageRepository.update(client);
        }
        return "redirect:/clients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        clientPageRepository.delete(id);
        return "redirect:/clients";
    }
}
