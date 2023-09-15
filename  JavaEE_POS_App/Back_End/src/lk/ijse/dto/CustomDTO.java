package lk.ijse.dto;

public class CustomDTO {
    private String nic;
    private String customerName;
    private String tel;
    private String address;
    private String code;
    private String itemName;
    private double price;
    private int qty;
    private String orderId;
    private String date;
    private double total;
    private double subTotal;
    private double cash;
    private int discount;
    private double balance;

    public CustomDTO() {
    }


    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "CustomDTO{" +
                "nic='" + nic + '\'' +
                ", customerName='" + customerName + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", code='" + code + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                ", subTotal=" + subTotal +
                ", cash=" + cash +
                ", discount=" + discount +
                ", balance=" + balance +
                '}';
    }
}
