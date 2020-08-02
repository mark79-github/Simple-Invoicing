package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.exception.ItemNotFoundException;
import bg.softuni.invoice.model.entity.Item;
import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.repository.ItemRepository;
import bg.softuni.invoice.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.ErrorMsg.ITEM_NOT_FOUND;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ItemServiceModel> getAllItems() {
        return this.itemRepository
                .findAll()
                .stream()
                .map(item -> this.modelMapper.map(item, ItemServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveItem(ItemServiceModel itemServiceModel) {
        Item item = this.modelMapper.map(itemServiceModel, Item.class);
        this.itemRepository.saveAndFlush(item);
    }

    @Override
    public ItemServiceModel getItemById(String id) {
        return this.itemRepository
                .findById(id)
                .map(item -> this.modelMapper.map(item, ItemServiceModel.class))
                .orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));
    }

    @Override
    public ItemServiceModel getItemByName(String name) {
        return this.itemRepository
                .findByName(name)
                .map(item -> this.modelMapper.map(item, ItemServiceModel.class))
                .orElse(null);
//                .orElseThrow(()-> new ItemNotFoundException(ITEM_NOT_FOUND));
    }
}
