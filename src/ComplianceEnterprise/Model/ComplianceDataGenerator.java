package ComplianceEnterprise.Model;

import com.github.javafaker.Faker;
import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Role.ComplianceAnalyst;
import ComplianceEnterprise.Role.ComplianceManager;
import ComplianceEnterprise.Role.CredentialSpecialist;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.ContractorAssignment;
import StaffingAgency.Request.CredentialVerificationRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates the demonstration data used by the Compliance Enterprise.
 *
 * Java Faker creates the contractor names, emails, and phone numbers used in
 * the Compliance demonstration records.
 *
 * @author janet
 */
public class ComplianceDataGenerator {

    private static final String[] SKILLS = {
        "Java Development", "Quality Assurance", "Project Management",
        "Data Analytics", "UX Design", "Cloud Infrastructure"
    };

    private static final String[] CREDENTIAL_TYPES = {
        "Background Check", "Identity Verification", "Professional License"
    };

    private ComplianceDataGenerator() {
    }

    public static ComplianceData generate() {
        ComplianceDirectory directory = new ComplianceDirectory();
        List<Contractor> contractors = new ArrayList<>();

        /*
         * Compliance users have short usernames so they are easy to use
         * during the project demonstration.
         */
        ComplianceManager manager = new ComplianceManager(
                "Morgan Lee", "morgan.lee@compliance.com", "C.manager");
        ComplianceAnalyst analyst = new ComplianceAnalyst(
                "Jamie Cruz", "jamie.cruz@compliance.com", "C.analyst");
        CredentialSpecialist specialist = new CredentialSpecialist(
                "Taylor Reed", "taylor.reed@compliance.com", "C.specialist");

        directory.addUser(manager);
        directory.addUser(analyst);
        directory.addUser(specialist);

        // A fixed seed keeps the classroom demonstration consistent.
        Faker faker = new Faker(new Random(5100));

        for (int index = 0; index < 12; index++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String phone = faker.phoneNumber().cellPhone();

            Contractor contractor = new Contractor(
                    firstName, lastName, email, phone,
                    SKILLS[index % SKILLS.length],
                    BigDecimal.valueOf(35 + (index * 2)));
            contractors.add(contractor);

            ContractorAssignment assignment = new ContractorAssignment(
                    contractor, LocalDate.now().plusDays(7 + index));

            String credentialType =
                    CREDENTIAL_TYPES[index % CREDENTIAL_TYPES.length];
            CredentialVerificationRequest request =
                    new CredentialVerificationRequest(
                            assignment, credentialType,
                            "Verify before assignment start date.");
            VerificationReview review =
                    directory.addVerificationRequest(request);

            CredentialRecord credential = new CredentialRecord(
                    contractor, credentialType,
                    faker.bothify("DOC-#####").toUpperCase(),
                    expirationDateFor(index));
            credential.setStatus(statusFor(index));
            directory.addCredential(credential);

            // These records belong only to Compliance and simulate the
            // outside registry searched by the Credential Specialist.
            if (index != 5) {
                directory.getCredentialRegistry().addRecord(
                        new RegistryCredentialRecord(
                                contractor.getFullName(), credentialType,
                                credential.getDocumentNumber(),
                                "Sample Credential Board",
                                credential.getExpirationDate(),
                                index == 2 ? CredentialStatus.EXPIRED
                                        : CredentialStatus.VERIFIED));
            } else {
                directory.getCredentialRegistry().keepRecordMissing(
                        contractor.getFullName(), credentialType);
            }

            /*
             * Include completed and assigned records so every report section
             * has meaningful data when the application first opens.
             */
            if (index < 3) {
                review.assignAnalyst(analyst);
                review.selectRequiredCredential(credentialType);
                CredentialVerificationTask task =
                        directory.requestCredentialVerification(review);
                task.completeFromRegistry(specialist,
                        index == 2
                                ? "The submitted credential is past its expiration date."
                        : "Document number and expiration date were confirmed.");
                if (credential.getStatus() == CredentialStatus.VERIFIED) {
                    review.completeReview(ComplianceDecision.APPROVED,
                            "Credential information was reviewed and confirmed.");
                }
            } else if (index < 6) {
                review.assignAnalyst(analyst);
                review.selectRequiredCredential(credentialType);
                directory.requestCredentialVerification(review);
            }
        }

        return new ComplianceData(
                directory, contractors, manager, analyst, specialist);
    }

    private static LocalDate expirationDateFor(int index) {
        if (index == 2) {
            return LocalDate.now().minusDays(15);
        }
        if (index < 6) {
            return LocalDate.now().plusDays(15 + index);
        }
        return LocalDate.now().plusMonths(6 + index);
    }

    private static CredentialStatus statusFor(int index) {
        if (index == 2) {
            return CredentialStatus.EXPIRED;
        }
        if (index < 2) {
            return CredentialStatus.VERIFIED;
        }
        if (index < 6) {
            return CredentialStatus.SUBMITTED;
        }
        return CredentialStatus.SUBMITTED;
    }

}
