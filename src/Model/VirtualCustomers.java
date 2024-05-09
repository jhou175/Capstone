package Model;

public class VirtualCustomers extends Customers {

    private String zoomEmail;
    private int virtualCustomerId;
    private int customerId;

    public VirtualCustomers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, int virtualCustomerId, String zoomEmail) {
        super(customerId, customerName, address, postalCode, phone, divisionId);
        setZoomEmail(zoomEmail);
    }

    public VirtualCustomers(int virtualCustomerId, String zoomEmail, int customerId) {
        super(customerId);
        this.virtualCustomerId = virtualCustomerId;
        this.zoomEmail = zoomEmail;
        this.customerId = customerId;
    }

    public String getZoomEmail() {
        return zoomEmail;
    }

    public int getVirtualID() {
        return virtualCustomerId;
    }

    public void setVirtualCustomerId(int virtualCustomerId) {
        this.virtualCustomerId = virtualCustomerId;
    }

    public void setZoomEmail(String zoomEmail) {
        this.zoomEmail = zoomEmail;
    }
}
