package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.bind.CompanyAddBindingModel;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.service.CompanyService;
import bg.softuni.invoice.service.ItemService;
import bg.softuni.invoice.service.LogService;
import bg.softuni.invoice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompanyService companyService;

    @MockitoBean
    private ModelMapper modelMapper;

    @MockitoBean
    private LogService logService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private ItemService itemService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAddConfirm_withValidInput_shouldRedirectToAll() throws Exception {
        CompanyAddBindingModel validModel = new CompanyAddBindingModel();
        validModel.setName("Valid Name");
        validModel.setAddress("Valid Address");
        validModel.setUniqueIdentifier("123456789");

        CompanyServiceModel serviceModel = new CompanyServiceModel();
        serviceModel.setName(validModel.getName());
        serviceModel.setAddress(validModel.getAddress());
        serviceModel.setUniqueIdentifier(validModel.getUniqueIdentifier());

        Mockito.when(companyService.getCompanyByName(validModel.getName())).thenReturn(null);
        Mockito.when(companyService.getCompanyByUniqueIdentifier(validModel.getUniqueIdentifier())).thenReturn(null);
        Mockito.when(modelMapper.map(validModel, CompanyServiceModel.class)).thenReturn(serviceModel);

        mockMvc.perform(post("/company/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("name", validModel.getName())
                        .param("address", validModel.getAddress())
                        .param("uniqueIdentifier", validModel.getUniqueIdentifier()))
                .andExpect(redirectedUrl("/company/all"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAddConfirm_withNameConflict_shouldRedirectToAdd() throws Exception {
        CompanyAddBindingModel conflictingModel = new CompanyAddBindingModel();
        conflictingModel.setName("Existing Company");
        conflictingModel.setAddress("New Address");
        conflictingModel.setUniqueIdentifier("123456789");

        CompanyServiceModel conflictingServiceModel = new CompanyServiceModel();
        conflictingServiceModel.setName(conflictingModel.getName());

        Mockito.when(companyService.getCompanyByName(conflictingModel.getName())).thenReturn(conflictingServiceModel);

        mockMvc.perform(post("/company/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("name", conflictingModel.getName())
                        .param("address", conflictingModel.getAddress())
                        .param("uniqueIdentifier", conflictingModel.getUniqueIdentifier()))
                .andExpect(redirectedUrl("add"))
                .andExpect(flash().attributeExists("companyAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.companyAddBindingModel"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAddConfirm_withUniqueIdentifierConflict_shouldRedirectToAdd() throws Exception {
        CompanyAddBindingModel conflictingModel = new CompanyAddBindingModel();
        conflictingModel.setName("New Company");
        conflictingModel.setAddress("New Address");
        conflictingModel.setUniqueIdentifier("987654321");

        CompanyServiceModel conflictingServiceModel = new CompanyServiceModel();
        conflictingServiceModel.setUniqueIdentifier(conflictingModel.getUniqueIdentifier());

        Mockito.when(companyService.getCompanyByName(conflictingModel.getName())).thenReturn(null);
        Mockito.when(companyService.getCompanyByUniqueIdentifier(conflictingModel.getUniqueIdentifier())).thenReturn(conflictingServiceModel);

        mockMvc.perform(post("/company/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("name", conflictingModel.getName())
                        .param("address", conflictingModel.getAddress())
                        .param("uniqueIdentifier", conflictingModel.getUniqueIdentifier()))
                .andExpect(redirectedUrl("add"))
                .andExpect(flash().attributeExists("companyAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.companyAddBindingModel"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAddConfirm_withValidationErrors_shouldRedirectToAdd() throws Exception {
        mockMvc.perform(post("/company/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("name", "")
                        .param("address", "")
                        .param("uniqueIdentifier", "invalid"))
                .andExpect(redirectedUrl("add"))
                .andExpect(flash().attributeExists("companyAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.companyAddBindingModel"));
    }
}
