package Model;

public class VirtualCustomers extends Customers{

    private String zoomEmail;

    public VirtualCustomers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, String divisionName, String country, int countryId, String zoomEmail) {
        super(customerId, customerName, address, postalCode, phone, divisionId, divisionName, country, countryId);
        setZoomEmail(zoomEmail);
    }

    public String getZoomEmail() {
        return zoomEmail;
    }

    public void setZoomEmail(String zoomEmail) {
        this.zoomEmail = zoomEmail;
    }
}
