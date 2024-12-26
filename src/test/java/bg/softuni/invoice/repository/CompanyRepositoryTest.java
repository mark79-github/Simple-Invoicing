package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Company;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CompanyRepositoryTest {

    private static final String TEST_COMPANY_NAME = "Test Company";
    private static final String TEST_ADDRESS = "123 Test Street";
    private static final String TEST_IDENTIFIER = "123456789";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByUniqueIdentifier_whenCompanyExists_shouldReturnCompany() {
        persistTestCompany(TEST_COMPANY_NAME, TEST_ADDRESS, TEST_IDENTIFIER, true);

        Optional<Company> result = companyRepository.findByUniqueIdentifier(TEST_IDENTIFIER);

        assertTrue(result.isPresent());
        assertEquals(TEST_COMPANY_NAME, result.get().getName());
        assertEquals(TEST_ADDRESS, result.get().getAddress());
        assertEquals(TEST_IDENTIFIER, result.get().getUniqueIdentifier());
        assertTrue(result.get().isSupplier());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"nonexistent"})
    void shouldReturnEmpty_whenCompanyDoesNotExist(String uniqueIdentifier) {
        Optional<Company> result = companyRepository.findByUniqueIdentifier(uniqueIdentifier);
        assertFalse(result.isPresent());
    }

    private void persistTestCompany(String name, String address, String uniqueIdentifier, boolean isSupplier) {
        Company company = new Company();
        company.setName(name);
        company.setAddress(address);
        company.setUniqueIdentifier(uniqueIdentifier);
        company.setSupplier(isSupplier);
        entityManager.persist(company);
    }
}
