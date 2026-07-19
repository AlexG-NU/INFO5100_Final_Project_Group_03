/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.People;

/**
 *
 * @author abhit
 */

import StaffingAgency.Enums.AvailabilityStatus;
import StaffingAgency.Enums.EmploymentStatus;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Contractor extends Person {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(2000);

    private BigDecimal payRate;
    private AvailabilityStatus availabilityStatus;
    private EmploymentStatus employmentStatus;

    public Contractor(String firstName, String lastName, String email, String phone,
                       String skills, BigDecimal payRate) {
        super(ID_SEQUENCE.incrementAndGet(), firstName, lastName, email, phone, skills);
        setPayRate(payRate);
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
        this.employmentStatus = EmploymentStatus.ACTIVE;
    }

    /** Alias for Janet's compliance code, which expects "contractorId" by name. */
    public int getContractorId() {
        return getPersonId();
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        if (payRate == null || payRate.signum() < 0) {
            throw new IllegalArgumentException("payRate must be a non-negative amount");
        }
        this.payRate = payRate;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void updateAvailability(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public void updateInformation(String phone, BigDecimal payRate) {
        setPhone(phone);
        setPayRate(payRate);
    }
}
    

