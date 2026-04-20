package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.Account;
import wealthmanagement.wealth_management_app.repository.AccountRepository;
import wealthmanagement.wealth_management_app.repository.ClientPageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository accountRepository;
    private final ClientPageRepository clientPageRepository;

    public AccountController(AccountRepository accountRepository,
                             ClientPageRepository clientPageRepository) {
        this.accountRepository = accountRepository;
        this.clientPageRepository = clientPageRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accounts/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("clients", clientPageRepository.findAllClientOptions());
        return "accounts/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
        model.addAttribute("account", account);
        model.addAttribute("clients", clientPageRepository.findAllClientOptions());
        return "accounts/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Account account) {
        if (account.getAccountId() == null) {
            accountRepository.insert(account);
        } else {
            accountRepository.update(account);
        }
        return "redirect:/accounts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        accountRepository.delete(id);
        return "redirect:/accounts";
    }
}
