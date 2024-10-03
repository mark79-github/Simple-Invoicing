package bg.softuni.invoice.service;


import bg.softuni.invoice.exception.CompanyNotFoundException;
import bg.softuni.invoice.model.entity.Company;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.repository.CompanyRepository;
import bg.softuni.invoice.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static bg.softuni.invoice.constant.ErrorMsg.COMPANY_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTests {

    private static final String NON_EXISTING_COMPANY = UUID.randomUUID().toString();

    private Company company;
    private final List<Company> companyList = new ArrayList<>();

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        company = new Company();
        company.setName("company");
        company.setAddress("address");
        company.setUniqueIdentifier("123456789");
        company.setSupplier(true);

        this.companyList.add(company);
    }

    @Test
    void getCompanyById_shouldThrowExceptionIfItemNotExists() {

        assertThatThrownBy(() -> this.companyService.getCompanyById(NON_EXISTING_COMPANY))
                .isInstanceOf(CompanyNotFoundException.class)
                .hasMessageContaining(COMPANY_NOT_FOUND);
    }

    @Test
    void getCompanyById_shouldReturnCompanyCorrectly() {

        doReturn(Optional.of(company)).when(this.companyRepository).findById(anyString());

        this.companyService.getCompanyById(NON_EXISTING_COMPANY);

        assertThat(company).isNotNull();
    }

    @Test
    void getCompanyByName_shouldReturnNullIfItemNotExists() {

        assertThat(this.companyService.getCompanyByName(NON_EXISTING_COMPANY)).isNull();
    }

    @Test
    void getCompanyByUniqueIdentifier_shouldReturnNullIfItemNotExists() {

        assertThat(this.companyService.getCompanyByUniqueIdentifier(NON_EXISTING_COMPANY)).isNull();
    }

    @Test
    void getAllCompanies_shouldReturnInvoicesCorrectly() {
        doReturn(this.companyList).when(this.companyRepository).findAll();

        List<CompanyServiceModel> companies = this.companyService.getAllCompanies();

        assertThat(companies).hasSize(1);
    }

    @Test
    void getSupplierCompany_shouldReturnSuppliersCorrectly() {
        doReturn(this.companyList).when(this.companyRepository).findBySupplier(anyBoolean());

        List<CompanyServiceModel> companies = this.companyService.getSupplierCompany(true);

        assertThat(companies).hasSize(1);
    }

    @Test
    void editCompany_shouldReturnCompanyCorrectly() {
        given(companyRepository.save(isA(Company.class))).willReturn(company);

        this.companyService.editCompany(new CompanyServiceModel());

        verify(companyRepository, times(1)).save(isA(Company.class));
        assertThat(company).isNotNull();
    }

    @Test
    void addCompany_shouldCreateSupplierCompanyCorrectly() {

        CompanyServiceModel companyServiceModel = new CompanyServiceModel();

        given(companyRepository.saveAndFlush(isA(Company.class))).willReturn(company);
        doReturn(0L).when(this.companyRepository).count();

        companyService.addCompany(companyServiceModel);

        verify(companyRepository, times(1)).saveAndFlush(isA(Company.class));
        assertThat(company.isSupplier()).isTrue();
    }

    @Test
    void addCompany_shouldCreateCustomerCompanyCorrectly() {

        CompanyServiceModel companyServiceModel = new CompanyServiceModel();

        company.setSupplier(false);
        given(companyRepository.saveAndFlush(isA(Company.class))).willReturn(company);
        doReturn(1L).when(this.companyRepository).count();

        companyService.addCompany(companyServiceModel);

        verify(companyRepository, times(1)).saveAndFlush(isA(Company.class));
        assertThat(company.isSupplier()).isFalse();
    }


}
