package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByName(String name);

    Optional<Company> findByUniqueIdentifier(String uniqueIdentifier);

    List<Company> findBySupplier(boolean supplier);
}
