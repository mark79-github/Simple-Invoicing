package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.bind.ItemAddBindingModel;
import bg.softuni.invoice.model.bind.ItemEditBindingModel;
import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.model.view.ItemViewModel;
import bg.softuni.invoice.service.CloudinaryService;
import bg.softuni.invoice.service.ItemService;
import bg.softuni.invoice.web.annotation.PageTitle;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.GlobalConstants.DEFAULT_ITEM_IMAGE_FILE;

@Controller
@RequestMapping("/item")
public class ItemController {

    private static final String ITEM_ADD_BINDING_MODEL = "itemAddBindingModel";
    private static final String ITEM_EDIT_BINDING_MODEL = "itemEditBindingModel";
    private final ItemService itemService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ItemController(ItemService itemService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.itemService = itemService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PageTitle("Item add")
    @PreAuthorize("hasRole('ADMIN')")
    public String add(Model model) {

        if (!model.containsAttribute(ITEM_ADD_BINDING_MODEL)) {
            model.addAttribute(ITEM_ADD_BINDING_MODEL, new ItemAddBindingModel());
        }

        return "item/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addConfirm(@Valid
                                 @ModelAttribute(name = ITEM_ADD_BINDING_MODEL) ItemAddBindingModel itemAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("imageUrl") MultipartFile multipartFile) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ITEM_ADD_BINDING_MODEL, itemAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemAddBindingModel", bindingResult);
            return "redirect:add";
        }

        ItemServiceModel itemServiceModel = this.itemService.getItemByName(itemAddBindingModel.getName());

        if (itemServiceModel != null) {
            redirectAttributes.addFlashAttribute(ITEM_ADD_BINDING_MODEL, itemAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemAddBindingModel", bindingResult);
            bindingResult.rejectValue("name", "error.itemAddBindingModel", "name already exists");

            return "redirect:add";
        }

        itemServiceModel = this.modelMapper.map(itemAddBindingModel, ItemServiceModel.class);
        if (!itemAddBindingModel.getImageUrl().isEmpty()) {
            itemServiceModel.setImageUrl(this.cloudinaryService.uploadImage(itemAddBindingModel.getImageUrl()));
        } else {
            itemServiceModel.setImageUrl(DEFAULT_ITEM_IMAGE_FILE);
        }

        this.itemService.saveItem(itemServiceModel);

        return "redirect:all";
    }

    @GetMapping("/all")
    @PageTitle("Item all")
    @PreAuthorize("isAuthenticated()")
    public String all(Model model) {

        if (!model.containsAttribute("items")) {

            List<ItemViewModel> itemViewModels = this.itemService.getAllItems()
                    .stream()
                    .map(itemServiceModel -> this.modelMapper.map(itemServiceModel, ItemViewModel.class))
                    .collect(Collectors.toList());

            model.addAttribute("items", itemViewModels);
            model.addAttribute("comparator", Comparator.comparing(ItemViewModel::getName));
        }

        return "item/all";
    }

    @GetMapping("/details/{id}")
    @PageTitle("Item details")
    @PreAuthorize("isAuthenticated()")
    public String details(@PathVariable String id,
                          @ModelAttribute("itemViewModel") ItemViewModel itemViewModel) {

        ItemServiceModel itemServiceModel = this.itemService.getItemById(id);
        this.modelMapper.map(itemServiceModel, itemViewModel);

        return "item/details";
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Item edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(@PathVariable String id,
                       Model model) {

        if (!model.containsAttribute(ITEM_EDIT_BINDING_MODEL)) {
            ItemServiceModel itemServiceModel = this.itemService.getItemById(id);
            ItemEditBindingModel itemEditBindingModel = this.modelMapper.map(itemServiceModel, ItemEditBindingModel.class);
            model.addAttribute(ITEM_EDIT_BINDING_MODEL, itemEditBindingModel);
        }

        return "item/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editConfirm(@PathVariable String id,
                              @Valid @ModelAttribute ItemEditBindingModel itemEditBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ITEM_EDIT_BINDING_MODEL, itemEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemEditBindingModel", bindingResult);
            return "redirect:{id}";
        }

        ItemServiceModel itemServiceModel = this.itemService.getItemByName(itemEditBindingModel.getName());

        if (itemServiceModel != null && !itemServiceModel.getId().equals(id)) {
            redirectAttributes.addFlashAttribute(ITEM_EDIT_BINDING_MODEL, itemEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemEditBindingModel", bindingResult);
            bindingResult.rejectValue("name", "error.itemEditBindingModel", "name already exists");

            return "redirect:{id}";
        }

        itemServiceModel = this.modelMapper.map(itemEditBindingModel, ItemServiceModel.class);

        if (!itemEditBindingModel.getNewImageUrl().isEmpty()) {
            itemServiceModel.setImageUrl(this.cloudinaryService.uploadImage(itemEditBindingModel.getNewImageUrl()));
        }

        this.itemService.saveItem(itemServiceModel);

        return "redirect:/item/details/{id}";
    }


}
