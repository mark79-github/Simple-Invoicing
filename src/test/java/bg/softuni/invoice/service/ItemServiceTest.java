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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static bg.softuni.invoice.constant.ErrorMsg.ITEM_NOT_FOUND;
import static bg.softuni.invoice.constant.GlobalConstants.DEFAULT_ITEM_IMAGE_FILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    private static final String NON_EXISTING_ITEM_ID = UUID.randomUUID().toString();

    private Item item;
    private final List<Item> itemList = new ArrayList<>();

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void init() {

        item = new Item();
        item.setId(NON_EXISTING_ITEM_ID);
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
    void getItemById_shouldReturnItemServiceModelIfExists() {

        doReturn(Optional.of(item)).when(itemRepository).findById(anyString());

        ItemServiceModel model = itemService.getItemById(NON_EXISTING_ITEM_ID);

        assertThat(model.getId()).isEqualTo(item.getId());
    }

    @Test
    void getItemByName_shouldReturnNullIfItemNotExists() {

        doReturn(Optional.empty()).when(itemRepository).findByName(anyString());

        ItemServiceModel itemServiceModel = itemService.getItemByName(NON_EXISTING_ITEM_ID);

        assertThat(itemServiceModel).isNull();
    }

    @Test
    void getItemByName_shouldReturnItemIfItemExists() {

        doReturn(Optional.of(this.item)).when(this.itemRepository).findByName(anyString());
        this.itemService.getItemByName(NON_EXISTING_ITEM_ID);
        assertThat(this.item).isNotNull();
    }

    @Test
    void getAllItems_shouldReturnItemsCorrectly() {
        when(this.itemRepository.findAll()).thenReturn(this.itemList);

        List<ItemServiceModel> items = this.itemService.getAllItems();

        assertThat(items).hasSize(1);
    }

    @Test
    void saveItem_shouldCreateItemCorrectly() {

        ItemServiceModel itemServiceModel = new ItemServiceModel();
        given(itemRepository.saveAndFlush(isA(Item.class))).willReturn(item);

        itemService.saveItem(itemServiceModel);

        verify(itemRepository, times(1)).saveAndFlush(isA(Item.class));
    }
}
