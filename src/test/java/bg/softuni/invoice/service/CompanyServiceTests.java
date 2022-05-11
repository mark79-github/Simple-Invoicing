package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.CompanyNotFoundException;
import bg.softuni.invoice.model.entity.Company;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.repository.CompanyRepository;
import bg.softuni.invoice.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CompanyServiceTests {

    private final String NON_EXISTING_COMPANY = UUID.randomUUID().toString();

    private final List<Company> companyList = new ArrayList<>();

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {
        this.companyService = new CompanyServiceImpl(this.companyRepository, this.modelMapper);

        Company company = new Company();
        company.setName("company");
        company.setAddress("address");
        company.setUniqueIdentifier("123456789");
        company.setSupplier(true);

        this.companyList.add(company);
    }

    @Test
    void getCompanyById_shouldThrowExceptionIfItemNotExists() {

        Assertions.assertThrows(CompanyNotFoundException.class, () -> this.companyService.getCompanyById(NON_EXISTING_COMPANY));
    }

    @Test
    void getCompanyByName_shouldReturnNullIfItemNotExists() {

        Assertions.assertNull(this.companyService.getCompanyByName(NON_EXISTING_COMPANY));
    }

    @Test
    void getCompanyByUniqueIdentifier_shouldReturnNullIfItemNotExists() {

        Assertions.assertNull(this.companyService.getCompanyByUniqueIdentifier(NON_EXISTING_COMPANY));
    }

    @Test
    void getAllCompanies_shouldReturnInvoicesCorrectly() {
        when(this.companyRepository.findAll()).thenReturn(this.companyList);

        List<CompanyServiceModel> companies = this.companyService.getAllCompanies();

        assertEquals(1, companies.size());
    }
}
