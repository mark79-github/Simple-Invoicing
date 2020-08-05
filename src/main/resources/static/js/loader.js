$('#loadInvoices').click(() => {

    $('.invoices-container').empty();
    $('.sales-container').empty();

    fetch('http://localhost:8080/all') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((invoice, idx) => { // Render the JSON data to the HTML

            let tableRow =
                '<tr>' +
                '<td>' + invoice.invoiceNumber + '</td>' +
                '<td>' + invoice.totalValue + '</td>' +
                '<td>' +
                '<button class="invoice-btn" data-invoice-id="' + invoice.id + '">show items</button>' +
                '</td>' +
                // '<td>' +
                // '<button class="delete-btn" data-author-id="'+ author.id +  '">Delete</button>' +
                // '</td>' +
                '</tr>';

            $('.invoices-container').append(tableRow);
        }));
});

$('body').on('click', 'button.invoice-btn', function () {
    let invoiceId = $(this).data('invoice-id');
    console.log("Invoice ID is " + invoiceId)

    $('.sales-container').empty();

    fetch('http://localhost:8080/all/' + invoiceId + '/sales') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((sale, idx) => { // Render the JSON data to the HTML
            console.log(sale.name);

            let tableRow =
                '<tr>' +
                '<td>' + sale.name + '</td>' +
                '<td>' + sale.quantity + '</td>' +
                '<td>' + sale.price + '</td>' +
                '<td>' + sale.vatValue + '%' + '</td>' +
                '</tr>';

            $('.sales-container').append(tableRow);
        }));

});

