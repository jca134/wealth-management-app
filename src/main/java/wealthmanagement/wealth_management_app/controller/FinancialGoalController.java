package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.FinancialGoal;
import wealthmanagement.wealth_management_app.repository.ClientPageRepository;
import wealthmanagement.wealth_management_app.repository.FinancialGoalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/financial-goals")
public class FinancialGoalController {

    private final FinancialGoalRepository financialGoalRepository;
    private final ClientPageRepository clientPageRepository;

    public FinancialGoalController(FinancialGoalRepository financialGoalRepository,
                                   ClientPageRepository clientPageRepository) {
        this.financialGoalRepository = financialGoalRepository;
        this.clientPageRepository = clientPageRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("goals", financialGoalRepository.findAll());
        return "financial-goals/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("goal", new FinancialGoal());
        model.addAttribute("clients", clientPageRepository.findAllClientOptions());
        return "financial-goals/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        FinancialGoal goal = financialGoalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found: " + id));
        model.addAttribute("goal", goal);
        model.addAttribute("clients", clientPageRepository.findAllClientOptions());
        return "financial-goals/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute FinancialGoal goal) {
        if (goal.getGoalId() == null) {
            financialGoalRepository.insert(goal);
        } else {
            financialGoalRepository.update(goal);
        }
        return "redirect:/financial-goals";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        financialGoalRepository.delete(id);
        return "redirect:/financial-goals";
    }
}
