package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.ItemNotFoundException;
import bg.softuni.invoice.model.entity.Item;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.enumerated.VatValue;
import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.repository.ItemRepository;
import bg.softuni.invoice.service.impl.ItemServiceImpl;
import bg.softuni.invoice.service.impl.RoleServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.GlobalConstants.DEFAULT_ITEM_IMAGE_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ItemServiceTests {

    private final String NON_EXISTING_ITEM_ID = UUID.randomUUID().toString();

    private Item item;
    private List<Item> itemList = new ArrayList<>();

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void init(){
        this.itemService = new ItemServiceImpl(itemRepository, modelMapper);

        this.item = new Item();
        item.setName("item");
        item.setPrice(BigDecimal.TEN);
        item.setVatValue(VatValue.TWENTY);
        item.setImageUrl(DEFAULT_ITEM_IMAGE_FILE);

        this.itemList.add(item);
    }

    @Test
    public void getItemById_shouldThrowExceptionIfItemNotExists() {

        Assertions.assertThrows(ItemNotFoundException.class, () -> this.itemService.getItemById(NON_EXISTING_ITEM_ID));
    }

    @Test
    public void getItemByName_shouldReturnNullIfItemNotExists() {

        Assertions.assertNull(this.itemService.getItemByName(NON_EXISTING_ITEM_ID));
    }

    @Test
    public void getAllItems_shouldReturnItemsCorrectly() {
        when(this.itemRepository.findAll()).thenReturn(this.itemList);

        List<ItemServiceModel> items = this.itemService.getAllItems();

        assertEquals(1, items.size());
    }
}
