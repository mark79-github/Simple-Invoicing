package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.model.view.ItemViewModel;
import bg.softuni.invoice.service.ItemService;
import bg.softuni.invoice.web.annotation.PageTitle;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static bg.softuni.invoice.constant.GlobalConstants.CART_ITEMS_COUNT;
import static bg.softuni.invoice.constant.GlobalConstants.CART_TOTAL_PRICE;

@Controller
@RequestMapping("/storage")
@ControllerAdvice
@RequiredArgsConstructor
public class StorageController {

    private final ModelMapper modelMapper;
    private final ItemService itemService;

    @PostMapping("/add-item/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addItem(@PathVariable String id,
                          int quantity,
                          HttpSession httpSession) {

        if (httpSession.getAttribute("cart") == null) {
            httpSession.setAttribute("cart", new LinkedHashMap<String, Integer>());
        }

        @SuppressWarnings("unchecked")
        Map<String, Integer> cart = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");

        ItemServiceModel itemServiceModel = this.itemService.getItemById(id);

        if (!cart.containsKey(itemServiceModel.getId())) {
            cart.put(itemServiceModel.getId(), quantity);
        } else {
            cart.put(itemServiceModel.getId(), cart.get(itemServiceModel.getId()) + quantity);
        }

        httpSession.setAttribute("cart", cart);
        httpSession.setAttribute(CART_ITEMS_COUNT, cart.size());

        return "redirect:/item/all";
    }

    @GetMapping("/details")
    @PageTitle("Storage")
    @PreAuthorize("isAuthenticated()")
    public String details(HttpSession httpSession,
                          Model model) {

        if (httpSession.getAttribute("cart") == null) {
            httpSession.setAttribute("cart", new LinkedHashMap<String, Integer>());
            httpSession.setAttribute(CART_TOTAL_PRICE, BigDecimal.ZERO);
        }

        @SuppressWarnings("unchecked")
        LinkedHashMap<String, Integer> items = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");

        List<ItemViewModel> cart = new LinkedList<>();
        items.forEach((id, quantity) -> {
            ItemServiceModel itemServiceModel = this.itemService.getItemById(id);
            itemServiceModel.setQuantity(quantity);
            ItemViewModel itemViewModel = this.modelMapper.map(itemServiceModel, ItemViewModel.class);
            cart.add(itemViewModel);
        });

        model.addAttribute("cart", cart);
        model.addAttribute(CART_TOTAL_PRICE, cart.stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        httpSession.setAttribute(CART_TOTAL_PRICE, model.getAttribute(CART_TOTAL_PRICE));

        return "storage/details";
    }


    @PostMapping("/remove-item/{id}")
    @PreAuthorize("isAuthenticated()")
    public String removeItem(@PathVariable String id,
                             HttpSession httpSession,
                             Model model) {

        @SuppressWarnings("unchecked")
        LinkedHashMap<String, Integer> cart = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");
        cart.remove(id);

        model.addAttribute("cart", cart);
        httpSession.setAttribute(CART_ITEMS_COUNT, cart.size());

        return "redirect:/storage/details";
    }

    @ModelAttribute("cartItemsCount")
    public int getCartData(HttpSession httpSession) {
        Integer cartItemsCount = (Integer) httpSession.getAttribute(CART_ITEMS_COUNT);
        if (cartItemsCount == null){
            return 0;
        }
        return cartItemsCount;
    }

    @ModelAttribute(CART_TOTAL_PRICE)
    public BigDecimal getTotalValue(HttpSession httpSession) {
        BigDecimal totalValue = (BigDecimal) httpSession.getAttribute(CART_TOTAL_PRICE);
        if (totalValue != null) {
            return totalValue;
        }
        return BigDecimal.ZERO;
    }
}