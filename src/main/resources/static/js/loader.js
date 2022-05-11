$(document).ready(function () {

    $('.data-container').empty();
    $('.sales-container').empty();

    fetch('http://localhost:8080/api/invoice') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((invoice) => { // Render the JSON data to the HTML

            let tableRow =
                '<tr>' +
                '<td>' + String(invoice.invoiceNumber).padStart(10, '0') + '</td>' +
                '<td>' + invoice.date + '</td>' +
                '<td>' + Number(invoice.totalValue).toFixed(2) + '</td>' +
                '<td>' + invoice.receiver.name + '</td>' +
                '<td>' + (invoice.paymentType === 'CASH' ? 'Cash' : 'Transfer') + '</td>' +
                '<td>' + (invoice.statusType === 'COMPLETE' ?
                'Complete' :
                '<button class="status-btn" data-invoice-id="' + invoice.id + '">change</button>') + '</td>' +
                '<td>' + new Date(invoice.createdOn).toLocaleString() + '</td>' +
                '<td>' +
                '<button class="invoice-btn" data-invoice-id="' + invoice.id + '">show items</button>' +
                '</td>' +
                '</tr>';
            $('.data-container').append(tableRow);
        }));
});

$('body').on('click', 'button.invoice-btn', function () {
    let invoiceId = $(this).data('invoice-id');

    $('.sales-container').empty();

    fetch('http://localhost:8080/api/invoice/' + invoiceId + '/sales') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((sale) => { // Render the JSON data to the HTML
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

$('body').on('click', 'button.status-btn', function () {

    let invoiceId = $(this).data('invoice-id');
    let clickedBtn = $(this);

    $.ajax({
        url: "/invoice/set-status",
        type: "post",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        data: {
            id: invoiceId
        },
        success: function () {
            $(clickedBtn).replaceWith("<span>" + "Complete" + "</span>");
        },
        error: function () {
            alert("something very bad happened");
        }
    });

});

