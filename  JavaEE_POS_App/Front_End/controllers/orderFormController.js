$(window).ready(function () {
    loadAllItemsForComboBox();
    getAllCustomersForComboBox();
    getOrderCount();
})

let customers;
let items;

function setOrderId(orderCount) {
    $("#orderDate").val(new Date().toISOString().slice(0, 10));
    if (orderCount > 0) {
        $("#orderId").val("O00-00" + (orderCount + 1));
    } else {
        $("#orderId").val("O00-001");
    }
}//this function use to set a next order id

function getOrderCount() {
    $.ajax({
        url: "http://localhost:8080/pos_app/pages/order",
        method: "get",
        success: function (resp) {
            setOrderId(resp.ordersCount);
        }
    });
}//this function use to get an order count from database

function loadAllItemsForComboBox() {
    $.ajax({
        url: "http://localhost:8080/pos_app/pages/item",
        method: "get",
        success: function (resp) {
            $("#item-itemCode").empty();
            $("#item-itemCode").append(
                `<option>Select Code</option>`
            );

            items = resp;

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

            customers = resp;

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

/*item*/
$("#item-itemCode").click(function () {
    let code = $("#item-itemCode").val();
    if (code !== "Select Code") {
        let item = searchItem(code);
        $("#itemName").val(item.name);
        $("#itemPrice").val(item.price);
        $("#itemQTY").val(item.qty);

        $("#item-itemCode").css("border", 'solid green 2px');
        $("#itemName").css("border", 'solid green 2px');
        $("#itemPrice").css("border", 'solid green 2px');
        $("#itemQTY").css("border", 'solid green 2px');
    } else {
        $("#itemName").val("");
        $("#itemPrice").val("");
        $("#itemQTY").val("");
    }
});

$("#Quantity").keyup(function () {
    let qty = $("#Quantity").val();
    if (Number($("#Quantity").val()) !== 0 && $("#Quantity").val() !== "") {
        if (Number(qty) <= Number($("#itemQTY").val())) {
            $("#Quantity").css("border", 'solid green 2px');
        } else {
            $("#Quantity").css("border", 'solid red 2px');
        }
    } else {
        $("#Quantity").css("border", 'solid red 2px');
    }
});//this event use check to input value for quantity