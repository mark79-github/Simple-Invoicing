$(document).ready(function () {

    $('.data-container').empty();

    fetch('http://localhost:8080/api/log') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((log) => { // Render the JSON data to the HTML

            let tableRow =
                '<tr>' +
                '<td>' + log.requestURI + '</td>' +
                '<td>' + log.method + '</td>' +
                '<td>' + new Date(log.dateTime).toLocaleString()  + '</td>' +
                '<td>' + (log.user === null ? 'anonymous' : log.user.firstName + ' ' + log.user.lastName) + '</td>' +
                '</tr>';
            $('.data-container').append(tableRow);
        }));
});

