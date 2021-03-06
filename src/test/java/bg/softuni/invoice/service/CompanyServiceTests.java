package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.CompanyNotFoundException;
import bg.softuni.invoice.model.entity.Company;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.repository.CompanyRepository;
import bg.softuni.invoice.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class CompanyServiceTests {

    private final String NON_EXISTING_COMPANY = UUID.randomUUID().toString();

    private List<Company> companyList = new ArrayList<>();
    private Company company;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {
        this.companyService = new CompanyServiceImpl(this.companyRepository, this.modelMapper);

        this.company = new Company();
        company.setName("company");
        company.setAddress("address");
        company.setUniqueIdentifier("123456789");
        company.setSupplier(true);

        this.companyList.add(company);
    }

    @Test
    public void getCompanyById_shouldThrowExceptionIfItemNotExists() {

        Assertions.assertThrows(CompanyNotFoundException.class, () -> this.companyService.getCompanyById(NON_EXISTING_COMPANY));
    }

    @Test
    public void getCompanyByName_shouldReturnNullIfItemNotExists() {

        Assertions.assertNull(this.companyService.getCompanyByName(NON_EXISTING_COMPANY));
    }

    @Test
    public void getCompanyByUniqueIdentifier_shouldReturnNullIfItemNotExists() {

        Assertions.assertNull(this.companyService.getCompanyByUniqueIdentifier(NON_EXISTING_COMPANY));
    }

    @Test
    public void getAllCompanies_shouldReturnInvoicesCorrectly() {
        when(this.companyRepository.findAll()).thenReturn(this.companyList);

        List<CompanyServiceModel> companies = this.companyService.getAllCompanies();

        assertEquals(1, companies.size());
    }
}
