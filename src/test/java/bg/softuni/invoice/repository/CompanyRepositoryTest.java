package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Company;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
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

    @Test
    void testFindByName_whenCompanyExists_shouldReturnCompany() {
        persistTestCompany(TEST_COMPANY_NAME, TEST_ADDRESS, TEST_IDENTIFIER, true);

        Optional<Company> result = companyRepository.findByName(TEST_COMPANY_NAME);

        assertTrue(result.isPresent());
        assertEquals(TEST_COMPANY_NAME, result.get().getName());
        assertEquals(TEST_ADDRESS, result.get().getAddress());
        assertEquals(TEST_IDENTIFIER, result.get().getUniqueIdentifier());
        assertTrue(result.get().isSupplier());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Unknown Company"})
    void shouldReturnEmpty_whenCompanyNameDoesNotExist(String name) {
        Optional<Company> result = companyRepository.findByName(name);
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

    @Test
    void testFindBySupplier_whenSupplierIsTrue_shouldReturnSupplierCompanies() {
        persistTestCompany("Supplier1", "Address1", "111111111", true);
        persistTestCompany("Supplier2", "Address2", "222222222", true);
        persistTestCompany("NonSupplier", "Address3", "333333333", false);

        List<Company> result = companyRepository.findBySupplier(true);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(Company::isSupplier));
    }

    @Test
    void testFindBySupplier_whenSupplierIsFalse_shouldReturnNonSupplierCompanies() {
        persistTestCompany("Supplier1", "Address1", "111111111", true);
        persistTestCompany("Supplier2", "Address2", "222222222", true);
        persistTestCompany("NonSupplier1", "Address3", "333333333", false);
        persistTestCompany("NonSupplier2", "Address4", "444444444", false);

        List<Company> result = companyRepository.findBySupplier(false);

        assertEquals(2, result.size());
        assertTrue(result.stream().noneMatch(Company::isSupplier));
    }

    @Test
    void testFindBySupplier_whenNoCompaniesMatch_shouldReturnEmptyList() {
        persistTestCompany("Supplier1", "Address1", "111111111", true);

        List<Company> result = companyRepository.findBySupplier(false);

        assertTrue(result.isEmpty());
    }
}
