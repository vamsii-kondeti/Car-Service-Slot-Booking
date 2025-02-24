public class Service {
    private String serviceName;
    private double serviceCost;

    public Service(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost; // Rs-cost
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost; // Rs-cost
    }
}
