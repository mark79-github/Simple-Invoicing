package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.model.service.SaleServiceModel;
import bg.softuni.invoice.model.view.SaleViewModel;
import bg.softuni.invoice.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Service
public class SaleServiceImpl implements SaleService {

    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BigDecimal getTotalPrice(Set<SaleServiceModel> sales) {
        return sales.stream()
                .map(saleServiceModel -> this.modelMapper.map(saleServiceModel, SaleViewModel.class))
                .map(saleServiceModel -> {
                    BigDecimal price = saleServiceModel.getPrice();
                    int quantity = saleServiceModel.getQuantity();
                    return price.multiply(BigDecimal.valueOf(quantity));
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalPriceWithoutVat(Set<SaleServiceModel> sales) {
        return sales.stream()
                .map(saleServiceModel -> this.modelMapper.map(saleServiceModel, SaleViewModel.class))
                .map(saleServiceModel -> {
                    BigDecimal price = saleServiceModel.getPrice();
                    int quantity = saleServiceModel.getQuantity();
                    int vatValue = saleServiceModel.getVatValue();
                    double decimalVatValue = 1 + vatValue / 100.0;
                    return price.multiply(BigDecimal.valueOf(quantity))
                            .divide(BigDecimal.valueOf(decimalVatValue), RoundingMode.CEILING);
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
