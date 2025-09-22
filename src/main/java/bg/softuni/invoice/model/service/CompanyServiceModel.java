package bg.softuni.invoice.model.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyServiceModel {

    private String id;
    private String name;
    private String address;
    private String uniqueIdentifier;
    private boolean supplier;

}
