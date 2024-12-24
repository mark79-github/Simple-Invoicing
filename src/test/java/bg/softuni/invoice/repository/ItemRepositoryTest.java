package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Item;
import bg.softuni.invoice.model.enumerated.VatValue;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemRepositoryTest {

    private static final String ITEM_NAME = "Test Item";
    private static final BigDecimal ITEM_PRICE = new BigDecimal("100.00");
    private static final String IMAGE_URL = "https://example.com/image.jpg";
    private static final VatValue VAT_VALUE = VatValue.ZERO;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findByName_shouldReturnItem_whenItemExists() {
        createAndPersistItem(ITEM_NAME, ITEM_PRICE, IMAGE_URL, VAT_VALUE);

        Optional<Item> foundItem = itemRepository.findByName(ITEM_NAME);

        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo(ITEM_NAME);
        assertThat(foundItem.get().getPrice()).isEqualByComparingTo(ITEM_PRICE);
        assertThat(foundItem.get().getImageUrl()).isEqualTo(IMAGE_URL);
        assertThat(foundItem.get().getVatValue()).isEqualTo(VAT_VALUE);
    }

    @Test
    void findByName_shouldReturnEmpty_whenItemDoesNotExist() {
        Optional<Item> foundItem = itemRepository.findByName("Nonexistent Item");

        assertThat(foundItem).isNotPresent();
    }

    private void createAndPersistItem(String name, BigDecimal price, String imageUrl, VatValue vatValue) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setImageUrl(imageUrl);
        item.setVatValue(vatValue);
        entityManager.persist(item);
    }
}
