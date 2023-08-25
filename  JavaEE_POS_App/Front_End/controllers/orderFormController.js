$(window).ready(function () {
    loadAllItemsForComboBox();
    getAllCustomersForComboBox();
})

function loadAllItemsForComboBox() {
    $.ajax({
        url: "http://localhost:8080/pos_app/pages/item",
        method: "get",
        success: function (resp) {
            $("#item-itemCode").empty();
            $("#item-itemCode").append(
                `<option>Select Code</option>`
            );
            for (let i in resp) {
                let item = resp[i];
                let code = item.code;
                $("#item-itemCode").append(
                    `<option>${code}</option>`
                );
            }
        },
        error: function (error) {

        }
    });
}

function getAllCustomersForComboBox() {
    $.ajax({
        url: "http://localhost:8080/pos_app/pages/customer",
        method: "get",
        success: function (rep) {
            $("#invoice-customerNIC").empty();
            $("#invoice-customerNIC").append(
                `<option>Select NIC</option>`
            );
            for (let i in rep) {
                let customer = rep[i];
                let nic = customer.nic;
                $("#invoice-customerNIC").append(
                    `<option>${nic}</option>`
                );
            }
        }
    });
}
