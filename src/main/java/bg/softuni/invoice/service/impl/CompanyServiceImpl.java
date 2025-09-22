package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.exception.CompanyNotFoundException;
import bg.softuni.invoice.model.entity.Company;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.repository.CompanyRepository;
import bg.softuni.invoice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static bg.softuni.invoice.constant.ErrorMsg.COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompanyServiceModel getCompanyByName(String name) {
        return this.companyRepository
                .findByName(name)
                .map(company -> this.modelMapper.map(company, CompanyServiceModel.class))
                .orElse(null);
    }

    @Override
    public CompanyServiceModel getCompanyById(String id) {
        return this.companyRepository
                .findById(id)
                .map(company -> this.modelMapper.map(company, CompanyServiceModel.class))
                .orElseThrow(() -> new CompanyNotFoundException(COMPANY_NOT_FOUND));
    }

    @Override
    public CompanyServiceModel getCompanyByUniqueIdentifier(String uniqueIdentifier) {
        return this.companyRepository
                .findByUniqueIdentifier(uniqueIdentifier)
                .map(company -> this.modelMapper.map(company, CompanyServiceModel.class))
                .orElse(null);
    }

    @Override
    public void addCompany(CompanyServiceModel companyServiceModel) {
        Company company = this.modelMapper.map(companyServiceModel, Company.class);

        if (this.companyRepository.count() == 0) {
            company.setSupplier(true);
        }

        this.companyRepository.saveAndFlush(company);
    }

    @Override
    public List<CompanyServiceModel> getAllCompanies() {
        return this.companyRepository
                .findAll()
                .stream()
                .map(company -> this.modelMapper.map(company, CompanyServiceModel.class))
                .toList();
    }

    @Override
    public List<CompanyServiceModel> getSupplierCompany(boolean supplier) {
        return this.companyRepository
                .findBySupplier(supplier)
                .stream()
                .map(company -> this.modelMapper.map(company, CompanyServiceModel.class))
                .toList();
    }

    @Override
    public void editCompany(CompanyServiceModel companyServiceModel) {
        Company company = this.modelMapper.map(companyServiceModel, Company.class);
        this.companyRepository.save(company);
    }
}
