package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.Transaction;
import wealthmanagement.wealth_management_app.repository.AccountRepository;
import wealthmanagement.wealth_management_app.repository.AssetRepository;
import wealthmanagement.wealth_management_app.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;

    public TransactionController(TransactionRepository transactionRepository,
                                 AccountRepository accountRepository,
                                 AssetRepository assetRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.assetRepository = assetRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("accounts", accountRepository.findAllAccountOptions());
        model.addAttribute("assets", assetRepository.findAll());
        return "transactions/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, Model model) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + id));
        model.addAttribute("transaction", transaction);
        model.addAttribute("accounts", accountRepository.findAllAccountOptions());
        model.addAttribute("assets", assetRepository.findAll());
        return "transactions/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Transaction transaction) {
        if (transaction.getTxnId() == null) {
            transactionRepository.insert(transaction);
        } else {
            transactionRepository.update(transaction);
        }
        return "redirect:/transactions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        transactionRepository.delete(id);
        return "redirect:/transactions";
    }
}
