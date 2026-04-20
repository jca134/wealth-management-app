package wealthmanagement.wealth_management_app.controller;

import wealthmanagement.wealth_management_app.model.Asset;
import wealthmanagement.wealth_management_app.repository.AssetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/assets")
public class AssetController {

    private final AssetRepository assetRepository;

    public AssetController(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assets", assetRepository.findAll());
        return "assets/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("isNew", true);
        return "assets/form";
    }

    @GetMapping("/edit/{symbol}")
    public String editForm(@PathVariable("symbol") String symbol, Model model) {
        Asset asset = assetRepository.findBySymbol(symbol)
                .orElseThrow(() -> new IllegalArgumentException("Asset not found: " + symbol));
        model.addAttribute("asset", asset);
        model.addAttribute("isNew", false);
        return "assets/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Asset asset, @RequestParam(value = "isNew", defaultValue = "false") boolean isNew) {
        if (isNew) {
            assetRepository.insert(asset);
        } else {
            assetRepository.update(asset);
        }
        return "redirect:/assets";
    }

    @PostMapping("/delete/{symbol}")
    public String delete(@PathVariable("symbol") String symbol) {
        assetRepository.delete(symbol);
        return "redirect:/assets";
    }
}
