/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 *

/**
 *
 * @author abhit
 */
    package StaffingAgency.People;

import StaffingAgency.Enums.CandidateStatus;
import java.util.concurrent.atomic.AtomicInteger;

public class Candidate extends Person {

    private static final AtomicInteger ID_SEQUENCE =
            new AtomicInteger(1000);

    private int yearsOfExperience;
    private CandidateStatus candidateStatus;

    public Candidate(
            String firstName,
            String lastName,
            String email,
            String phone,
            String skills,
            int yearsOfExperience
    ) {
        super(
                ID_SEQUENCE.incrementAndGet(),
                firstName,
                lastName,
                email,
                phone,
                skills
        );

        setYearsOfExperience(yearsOfExperience);
        this.candidateStatus = CandidateStatus.APPLIED;
    }

    public int getCandidateId() {
        return getPersonId();
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        if (yearsOfExperience < 0 || yearsOfExperience > 60) {
            throw new IllegalArgumentException(
                    "Years of experience must be between 0 and 60."
            );
        }

        this.yearsOfExperience = yearsOfExperience;
    }

    public CandidateStatus getCandidateStatus() {
        return candidateStatus;
    }

    public void setCandidateStatus(
            CandidateStatus candidateStatus
    ) {
        if (candidateStatus == null) {
            throw new IllegalArgumentException(
                    "Candidate status is required."
            );
        }

        this.candidateStatus = candidateStatus;
    }

    public void updateInformation(
            String firstName,
            String lastName,
            String email,
            String phone,
            String skills,
            int yearsOfExperience,
            CandidateStatus candidateStatus
    ) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
        setSkills(skills);
        setYearsOfExperience(yearsOfExperience);
        setCandidateStatus(candidateStatus);
    }

    @Override
    public String toString() {
        return getCandidateId() + " - " + getFullName();
    }
}

