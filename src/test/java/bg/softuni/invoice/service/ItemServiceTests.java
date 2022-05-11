package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.ItemNotFoundException;
import bg.softuni.invoice.model.entity.Item;
import bg.softuni.invoice.model.enumerated.VatValue;
import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.repository.ItemRepository;
import bg.softuni.invoice.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static bg.softuni.invoice.constant.ErrorMsg.ITEM_NOT_FOUND;
import static bg.softuni.invoice.constant.GlobalConstants.DEFAULT_ITEM_IMAGE_FILE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ItemServiceTests {

    private final String NON_EXISTING_ITEM_ID = UUID.randomUUID().toString();

    private final List<Item> itemList = new ArrayList<>();

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
//        this.itemService = new ItemServiceImpl(itemRepository, modelMapper);

        Item item = new Item();
        item.setName("item");
        item.setPrice(BigDecimal.TEN);
        item.setVatValue(VatValue.TWENTY);
        item.setImageUrl(DEFAULT_ITEM_IMAGE_FILE);

        this.itemList.add(item);
    }

    @Test
    void getItemById_shouldThrowExceptionIfItemNotExists() {

        assertThatThrownBy(() -> this.itemService.getItemById(NON_EXISTING_ITEM_ID))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining(ITEM_NOT_FOUND);
    }

    @Test
    void getItemByName_shouldReturnNullIfItemNotExists() {

        assertThat(this.itemService.getItemByName(NON_EXISTING_ITEM_ID)).isNull();
    }

    @Test
    void getAllItems_shouldReturnItemsCorrectly() {
        when(this.itemRepository.findAll()).thenReturn(this.itemList);

        List<ItemServiceModel> items = this.itemService.getAllItems();

        assertThat(items).asList().hasSize(1);
    }
}
