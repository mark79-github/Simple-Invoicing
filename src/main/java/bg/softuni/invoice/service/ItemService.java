package bg.softuni.invoice.service;

import bg.softuni.invoice.model.service.ItemServiceModel;

import java.util.List;

public interface ItemService {
    List<ItemServiceModel> getAllItems();

    void saveItem(ItemServiceModel itemServiceModel);

    ItemServiceModel getItemById(String id);

    ItemServiceModel getItemByName(String name);
}
