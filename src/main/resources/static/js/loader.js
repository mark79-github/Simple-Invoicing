// $( document ).ready(function() {
//     $( "#loadInvoices" ).trigger( "click" );
// });

$(document).ready(function () {
// $('#loadInvoices').click(() => {

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
                '<td>' + invoice.paymentType + '</td>' +
                '<td>' + invoice.statusType + '</td>' +
                '<td>' + new Date(invoice.createdOn).toLocaleString() + '</td>' +
                '<td>' +
                '<button class="invoice-btn" data-invoice-id="' + invoice.id + '">show items</button>' +
                '</td>' +
                '</tr>';
            $('.invoices-container').append(tableRow);
        }));
});


$('body').on('click', 'button.invoice-btn', function () {
    let invoiceId = $(this).data('invoice-id');
    // console.log("Invoice ID is " + invoiceId)

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
                '<td>' + sale.vatValue + '%' + '</td>' +
                '</tr>';

            $('.sales-container').append(tableRow);
        }));

});

