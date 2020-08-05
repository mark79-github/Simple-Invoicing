$(document).ready(function () {

    $('.invoices-container').empty();
    $('.sales-container').empty();

    fetch('http://localhost:8080/all') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((invoice) => { // Render the JSON data to the HTML

            let tableRow =
                '<tr>' +
                '<td>' + String(invoice.invoiceNumber).padStart(10, '0') + '</td>' +
                '<td>' + invoice.date + '</td>' +
                '<td>' + Number(invoice.totalValue).toFixed(2) + '</td>' +
                '<td>' + invoice.receiver.name + '</td>' +
                '<td>' + (invoice.paymentType === 'CASH' ? 'Cash' : 'Transfer') + '</td>' +
                '<td>' + (invoice.statusType === 'COMPLETE' ? 'Complete' : 'Await') + '</td>' +

                // '<td><button class="status-btn" data-invoice-id="' + invoice.id + '">show items</button></td>' +
                // not working -> why ?

                '<td>' + new Date(invoice.createdOn).toLocaleString() + '</td>' +
                '<td>' +
                '<button class="invoice-btn" data-invoice-id="' + invoice.id + '">show items</button>' +
                '</td>' +
                '</tr>';
            $('.invoices-container').append(tableRow);
        }));
});

$('body').on('click', 'button.status-btn', function () {
    let invoiceId = $(this).data('invoice-id');
    var settings = {
        "url": "http://localhost:8080/invoice/set-status/" + invoiceId,
        "method": "POST"
    };

    $.ajax(settings).done(function (response) {
        console.log(response);
    });

});

$('body').on('click', 'button.invoice-btn', function () {
    let invoiceId = $(this).data('invoice-id');

    $('.sales-container').empty();

    fetch('http://localhost:8080/all/' + invoiceId + '/sales') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((sale, idx) => { // Render the JSON data to the HTML
            console.log(sale.name);

            let tableRow =
                '<tr>' +
                '<td>' + sale.name + '</td>' +
                '<td>' + sale.quantity + '</td>' +
                '<td>' + Number(sale.price).toFixed(2) + '</td>' +
                '<td>' + (sale.vatValue === 'TWENTY' ? '20' : sale.vatValue === 'ZERO' ? '9' : '0') + '%' + '</td>' +
                '</tr>';

            $('.sales-container').append(tableRow);
        }));

});

