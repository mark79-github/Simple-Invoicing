package bg.softuni.invoice.model.view;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyViewModel {

    private String id;
    private String name;
    private String address;
    private String uniqueIdentifier;
    private boolean supplier;

}
