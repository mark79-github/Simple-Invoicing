package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.model.view.ItemViewModel;
import bg.softuni.invoice.service.ItemService;
import bg.softuni.invoice.web.annotation.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/storage")
public class StorageController {

    private final ModelMapper modelMapper;
    private final ItemService itemService;

    @Autowired
    public StorageController(ModelMapper modelMapper, ItemService itemService) {
        this.modelMapper = modelMapper;
        this.itemService = itemService;
    }

    @PostMapping("/add-item/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addItem(@PathVariable String id,
                          int quantity,
                          HttpSession httpSession) {

        if (httpSession.getAttribute("cart") == null) {
            httpSession.setAttribute("cart", new LinkedHashMap<String, Integer>());
        }

        Map<String, Integer> cart = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");

        ItemServiceModel itemServiceModel = this.itemService.getItemById(id);

        if (!cart.containsKey(itemServiceModel.getId())) {
            cart.put(itemServiceModel.getId(), quantity);
        } else {
            cart.put(itemServiceModel.getId(), cart.get(itemServiceModel.getId()) + quantity);
        }

        httpSession.setAttribute("cart", cart);

        return "redirect:/item/all";
    }

    @GetMapping("/details")
    @PageTitle("Storage")
    @PreAuthorize("isAuthenticated()")
    public String details(HttpSession httpSession,
                          Model model) {

        if (httpSession.getAttribute("cart") == null) {
            httpSession.setAttribute("cart", new LinkedHashMap<String, Integer>());
            httpSession.setAttribute("totalPrice", BigDecimal.ZERO);
        }

        LinkedHashMap<String, Integer> items = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");

        List<ItemViewModel> cart = new LinkedList<>();
        items.forEach((id, quantity) -> {
            ItemServiceModel itemServiceModel = this.itemService.getItemById(id);
            itemServiceModel.setQuantity(quantity);
            ItemViewModel itemViewModel = this.modelMapper.map(itemServiceModel, ItemViewModel.class);
            cart.add(itemViewModel);
        });

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cart.stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        httpSession.setAttribute("totalPrice", model.getAttribute("totalPrice"));

        return "storage/details";
    }


    @PostMapping("/remove-item/{id}")
    @PreAuthorize("isAuthenticated()")
    public String removeItem(@PathVariable String id,
                             HttpSession httpSession,
                             Model model) {

        LinkedHashMap<String, Integer> cart = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");
        cart.remove(id);

        model.addAttribute("cart", cart);

        return "redirect:/storage/details";
    }
}