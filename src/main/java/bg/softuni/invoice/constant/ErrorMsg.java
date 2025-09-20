package bg.softuni.invoice.constant;

import lombok.NoArgsConstructor;

import static bg.softuni.invoice.constant.GlobalConstants.CHARACTERS_LONG;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ErrorMsg {

    public static final String USER_NOT_FOUND = "User with id %s not found";
    public static final String USERNAME_NOT_FOUND = "Username %s not found";
    public static final String ROLE_NOT_FOUND = "Role not found!";
    public static final String ITEM_NOT_FOUND = "Item not found!";
    public static final String INVOICE_NOT_FOUND = "Invoice not found!";
    public static final String COMPANY_NOT_FOUND = "Company not found!";

    //    regex pattern from https://emailregex.com/
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    public static final String NAME_REGEX = "^[A-ZА-Я][a-zа-яA-ZА-Я]+$";
    public static final String UNIQUE_IDENTIFIER_REGEX = "^(\\d{9}|\\d{13})$";
    public static final int STRING_MIN_LENGTH = 3;
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static final String NAME_MIN_LENGTH = "name must be at least " + STRING_MIN_LENGTH + CHARACTERS_LONG;
    public static final String FIRST_NAME_MIN_LENGTH = "first name must be at least " + STRING_MIN_LENGTH + CHARACTERS_LONG;
    public static final String LAST_NAME_MIN_LENGTH = "last name must be at least " + STRING_MIN_LENGTH + CHARACTERS_LONG;
    public static final String ADDRESS_MIN_LENGTH = "address must be at least " + STRING_MIN_LENGTH + CHARACTERS_LONG;
    public static final String PASSWORD_MIN_LENGTH = "password must be at least " + STRING_MIN_LENGTH + CHARACTERS_LONG;
    public static final String UNIQUE_IDENTIFIER_LENGTH = "unique identifier must be 9 or 13 digits";
    public static final String SUPPLIER_NOT_NULL = "supplier flag can not be null";
    public static final String SENDER_NOT_EMPTY = "sender must not be empty";
    public static final String RECEIVER_NOT_EMPTY = "receiver must not be empty";
    public static final String PAYMENT_TYPE_NOT_EMPTY = "payment type must not be empty";
    public static final String VALUE_POSITIVE = "value must be positive";
    public static final String PRICE_POSITIVE = "price must have positive value";
    public static final String DATE_NOW_OR_FUTURE = "date cannot be in the past";
    public static final String DATE_NOT_EMPTY = "date cannot be empty";
    public static final String IMAGE_SOURCE_NOT_EMPTY = "image source can not be empty";
    public static final String EMAIL_NOT_EMPTY = "email cannot be empty";
    public static final String PASSWORD_NOT_EMPTY = "password cannot be empty";
    public static final String EMAIL_NOT_CORRECT = "email is not correct";
    public static final String FIRST_NAME_FIRST_LETTER_UPPERCASE = "first name must be in lower case except for the first one";
    public static final String LAST_NAME_FIRST_LETTER_UPPERCASE = "last name must be in lower case except for the first one";
    public static final String DATE_PAST = "date can only be in the past";
    public static final String AUTHORITY_NOT_EMPTY = "authority cannot be empty";
    public static final String URI_NOT_EMPTY = "request URI cannot be empty";
    public static final String METHOD_NOT_EMPTY = "method cannot be empty";
    public static final String VAT_VALUE_NOT_EMPTY = "vat value cannot be empty";
    public static final String PRICE_NOT_EMPTY = "price cannot be empty";
    public static final String NAME_NOT_EMPTY = "name cannot be empty";
    public static final String ADDRESS_NOT_EMPTY = "address cannot be empty";
    public static final String UNIQUE_IDENTIFIER_NOT_EMPTY = "unique identifier cannot be empty";
    public static final String VALUE_NOT_EMPTY = "total value cannot be empty";
    public static final String STATUS_TYPE_NOT_EMPTY = "status type must not be empty";
    public static final String INVOICE_NUMBER_NOT_EMPTY = "invoice number must not be empty";
    public static final String USER_NOT_EMPTY = "user must not be empty";
    public static final String INVOICE_NUMBER_POSITIVE = "invoice number must be positive";

}
