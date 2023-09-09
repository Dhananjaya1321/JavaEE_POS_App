package lk.ijse.entity;

public class Customer {
    private String nic;
    private String name;
    private String tel;
    private String address;

    public Customer() {
    }

    public Customer(String nic, String name, String tel, String address) {
        this.nic = nic;
        this.name = name;
        this.tel = tel;
        this.address = address;
    }

    public Customer(String nic) {
        this.nic = nic;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Customer{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
