package bg.softuni.invoice.service;

import bg.softuni.invoice.model.service.SaleServiceModel;

import java.math.BigDecimal;
import java.util.Set;

public interface SaleService {
    BigDecimal getTotalPrice(Set<SaleServiceModel> sales);

    BigDecimal getTotalPriceWithoutVat(Set<SaleServiceModel> sales);
}
