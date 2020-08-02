package bg.softuni.invoice.constant;

public class ErrorMsg {

    public final static String USER_NOT_FOUND = "User with id %s not found";
    public final static String USERNAME_NOT_FOUND = "Username %s not found";
    public final static String ROLE_NOT_FOUND = "Role not found!";
    public final static String ITEM_NOT_FOUND = "Item not found!";
    public final static String INVOICE_NOT_FOUND = "Invoice not found!";
    public final static String COMPANY_NOT_FOUND = "Company not found!";

    //    regex pattern from https://emailregex.com/
    public final static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    public final static String NAME_REGEX = "^[A-ZА-Я][a-zа-яA-ZА-Я]+$";
    public final static String UNIQUE_IDENTIFIER_REGEX = "^(\\d{9}|\\d{13})$";
    public final static int STRING_MIN_LENGTH = 3;
    public final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public final static String NAME_MIN_LENGTH = "name must be at least " + STRING_MIN_LENGTH + "characters long";
    public final static String FIRST_NAME_MIN_LENGTH = "first name must be at least " + STRING_MIN_LENGTH + "characters long";
    public final static String LAST_NAME_MIN_LENGTH = "last name must be at least " + STRING_MIN_LENGTH + "characters long";
    public final static String ADDRESS_MIN_LENGTH = "address must be at least " + STRING_MIN_LENGTH + "characters long";
    public final static String PASSWORD_MIN_LENGTH = "password must be at least " + STRING_MIN_LENGTH + "characters long";
    public final static String UNIQUE_IDENTIFIER_LENGTH = "unique identifier must be 9 or 13 digits";
    public final static String SUPPLIER_NOT_NULL = "supplier flag can not be null";
    public final static String SENDER_NOT_EMPTY = "sender must not be empty";
    public final static String RECEIVER_NOT_EMPTY = "receiver must not be empty";
    public final static String PAYMENT_TYPE_NOT_EMPTY = "payment type must not be empty";
    public final static String VALUE_POSITIVE = "value must be positive";
    public final static String PRICE_POSITIVE = "price must have positive value";
    public final static String DATE_NOW_OR_FUTURE = "date cannot be in the past";
    public final static String DATE_NOT_EMPTY = "date cannot be empty";
    public final static String IMAGE_SOURCE_NOT_EMPTY = "image source can not be empty";
    public final static String EMAIL_NOT_EMPTY = "email cannot be empty";
    public final static String PASSWORD_NOT_EMPTY = "password cannot be empty";
    public final static String EMAIL_NOT_CORRECT = "email is not correct";
    public final static String FIRST_NAME_FIRST_LETTER_UPPERCASE = "first name must be in lower case except for the first one";
    public final static String LAST_NAME_FIRST_LETTER_UPPERCASE = "last name must be in lower case except for the first one";
    public final static String DATE_PAST = "date can only be in the past";
    public final static String AUTHORITY_NOT_EMPTY = "authority cannot be empty";



}
