# Simple Invoicing

It's my final project for the [Spring Advanced - June 2020](https://softuni.bg/trainings/3026/spring-advanced-june-2020/internal) course

## Built With

* [Spring](https://spring.io/) - The web framework used
* [Thymeleaf](https://www.thymeleaf.org/) - Java template engine
* [MySQL](https://www.mysql.com/) - Database engine
* [Bootstrap](https://getbootstrap.com/) - The world’s most popular framework for building responsive web pages, as they say
* [jQuery](https://jquery.com/) - JavaScript library

## Author

* **Martin Dimitrov** - [mark79-github](https://github.com/mark79-github/)

## Functionality

### Role

* There are 3 types - ADMIN, ROUTE, USER - which are saved in the database using EventListener, but only when the repository is empty

### User

* entity with the following fields -> username, first and last name and password. Username itself is an email. Тhe first registered user takes full rights (all roles), and the next ones get registered with role authority (ROLE_USER). They are not allowed to enter the system before the user with the highest rights allows it. Each user can edit some of their own data. Only the root user (with the highest rights) can change the roles of other users. Users who have admin rights can create companies and products and edit them. Ordinary users can view the lists of companies and items, add them to the storage by entering the appropriate quantity and create an invoice. They can also remove items from the storage. 

### Company

* entity with the following fields -> name, address and unique identifier. In order for invoices to be issued, at least two companies must be established. The first introduced becomes the issuer of all invoices, and all others are contractors. 

### Item

* entity with the following fields -> name, price, image url and vat value (percentages). It is enough to have one item for the system to work. If there is a selected photo of the item, it is uploaded to Cloudinary, otherwise a default photo from the system resources folder, is used.

### Invoice

* entity with the following fields -> invoice number, date, total value, user (who compiled it), issuer (sender), contractor (receiver), payment type, status type, datetime of creation and the included items. Only some of these parameters have to be entered when creating the invoice. If the user does not have the admin role, then he can see only the invoices created by himself. When an invoice has been created with a type of payment by bank transfer, its status, is set to AWAIT. Only users with an admin role can change the invoice status to COMPLETE.

### Sale

* entity with the following fields -> name, quantity, price and vat value. A list of them is saved for each invoice. Containing accurate information of the name, price, quantity and vat value (percentages) at the time of creating the invoice.

### Log

* entity with the following fields -> route, method and a date time. Using an interceptor, store information about the made requests, method, route and user.

### Storage

* session attribute -> it's a map, which contains information about selected items and their quantities.

### Interceptor

* There are also very commonly used interceptors for the fav icon, and the title page

### Scheduler

* cron task, every five minutes delete logs from the system (in this case -> all older than 5 minutes log records)
* cron task, every five minutes checks if there are invoices with AWAIT status and changes it

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

