package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.Advisor;
import wealthmanagement.wealth_management_app.repository.AdvisorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/advisors")
public class AdvisorController {

    private final AdvisorRepository advisorRepository;

    public AdvisorController(AdvisorRepository advisorRepository) {
        this.advisorRepository = advisorRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("advisors", advisorRepository.findAll());
        return "advisors/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("advisor", new Advisor());
        return "advisors/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        Advisor advisor = advisorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Advisor not found: " + id));
        model.addAttribute("advisor", advisor);
        return "advisors/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Advisor advisor) {
        if (advisor.getAdvisorId() == null) {
            advisorRepository.insert(advisor);
        } else {
            advisorRepository.update(advisor);
        }
        return "redirect:/advisors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        advisorRepository.delete(id);
        return "redirect:/advisors";
    }
}
