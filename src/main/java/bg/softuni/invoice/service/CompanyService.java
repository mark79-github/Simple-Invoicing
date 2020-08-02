package bg.softuni.invoice.service;

import bg.softuni.invoice.model.service.CompanyServiceModel;

import java.util.List;

public interface CompanyService {
    CompanyServiceModel getCompanyByName(String name);

    CompanyServiceModel getCompanyById(String id);

    CompanyServiceModel getCompanyByUniqueIdentifier(String uniqueIdentifier);

    void addCompany(CompanyServiceModel companyServiceModel);

    List<CompanyServiceModel> getAllCompanies();

    List<CompanyServiceModel> getSupplierCompany(boolean supplier);

    void editCompany(CompanyServiceModel companyServiceModel);
}
