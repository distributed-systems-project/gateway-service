package pl.edu.agh.distributedsystems.gateway.security;

public class EmployeePrincipal {

    private String employeeId;
    private Long hotelId;
    private String position;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "EmployeePrincipal{" +
                "employeeId='" + employeeId + '\'' +
                ", hotelId=" + hotelId +
                ", position='" + position + '\'' +
                '}';
    }
}
