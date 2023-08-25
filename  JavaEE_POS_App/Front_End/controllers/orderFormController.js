$(window).ready(function () {
    loadAllItemsForComboBox();
    getAllCustomersForComboBox();
})

let customers;
let items;

function loadAllItemsForComboBox() {
    $.ajax({
        url: "http://localhost:8080/pos_app/pages/item",
        method: "get",
        success: function (resp) {
            $("#item-itemCode").empty();
            $("#item-itemCode").append(
                `<option>Select Code</option>`
            );

            items=resp;

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
        success: function (resp) {
            $("#invoice-customerNIC").empty();
            $("#invoice-customerNIC").append(
                `<option>Select NIC</option>`
            );

            customers=resp;

            for (let i in resp) {
                let customer = resp[i];
                let nic = customer.nic;
                $("#invoice-customerNIC").append(
                    `<option>${nic}</option>`
                );
            }
        }
    });
}

/*invoice*/
$("#invoice-customerNIC").click(function () {
    let nic = $("#invoice-customerNIC").val();
    if (nic !== "Select NIC") {
        let customer = searchCustomer(nic);
        $("#customerName").val(customer.name);
        $("#customerTel").val(customer.tel);
        $("#customerAddress").val(customer.address);

        $("#invoice-customerNIC").css("border", 'solid green 2px');
        $("#customerName").css("border", 'solid green 2px');
        $("#customerTel").css("border", 'solid green 2px');
        $("#customerAddress").css("border", 'solid green 2px');
    } else {
        $("#customerName").val("");
        $("#customerTel").val("");
        $("#customerAddress").val("");
    }
});

function searchCustomer(nic) {
    return customers.find(function (customer) {
        return customer.nic === nic;
    });//return to matched customer object
}
