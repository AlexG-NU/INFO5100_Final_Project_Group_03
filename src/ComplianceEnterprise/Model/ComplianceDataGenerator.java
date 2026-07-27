package ComplianceEnterprise.Model;

import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Role.ComplianceAnalyst;
import ComplianceEnterprise.Role.ComplianceManager;
import ComplianceEnterprise.Role.CredentialSpecialist;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.ContractorAssignment;
import StaffingAgency.Request.CredentialVerificationRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates the demonstration data used by the Compliance Enterprise.
 *
 * If Java Faker is added to the project libraries, names, emails, and phone
 * numbers are generated through Faker. The fallback values keep the project
 * runnable when a teammate does not have the Faker JAR installed.
 *
 * @author janet
 */
public class ComplianceDataGenerator {

    private static final String[] FIRST_NAMES = {
        "Maya", "Daniel", "Sofia", "Ethan", "Olivia", "Marcus",
        "Nina", "Jordan", "Priya", "Lucas", "Avery", "Noah"
    };

    private static final String[] LAST_NAMES = {
        "Chen", "Martinez", "Patel", "Williams", "Kim", "Johnson",
        "Nguyen", "Taylor", "Shah", "Brown", "Davis", "Wilson"
    };

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

        Object faker = createFaker();

        for (int index = 0; index < 12; index++) {
            String firstName = fakeValue(faker, "name", "firstName",
                    FIRST_NAMES[index]);
            String lastName = fakeValue(faker, "name", "lastName",
                    LAST_NAMES[index]);
            String email = fakeValue(faker, "internet", "emailAddress",
                    firstName.toLowerCase() + "." + lastName.toLowerCase()
                    + "@example.com");
            String phone = fakeValue(faker, "phoneNumber", "cellPhone",
                    String.format("949-555-%04d", 1000 + index));

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
                    String.format("DOC-%05d", index + 1),
                    expirationDateFor(index));
            credential.setStatus(statusFor(index));
            directory.addCredential(credential);

            /*
             * Include completed and assigned records so every report section
             * has meaningful data when the application first opens.
             */
            if (index < 3) {
                review.assignAnalyst(analyst);
                review.completeReview(
                        index == 2
                                ? ComplianceDecision.REJECTED
                                : ComplianceDecision.APPROVED,
                        index == 2
                                ? "Credential information could not be confirmed."
                                : "Credential information was reviewed and confirmed.");
            } else if (index < 6) {
                review.assignAnalyst(analyst);
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

    private static Object createFaker() {
        try {
            Class<?> fakerClass = Class.forName("com.github.javafaker.Faker");
            return fakerClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            return null;
        }
    }

    private static String fakeValue(Object faker, String providerMethod,
            String valueMethod, String fallback) {
        if (faker == null) {
            return fallback;
        }
        try {
            Method provider = faker.getClass().getMethod(providerMethod);
            Object providerObject = provider.invoke(faker);
            Method value = providerObject.getClass().getMethod(valueMethod);
            String generatedValue = String.valueOf(value.invoke(providerObject));
            return generatedValue == null || generatedValue.trim().isEmpty()
                    ? fallback : generatedValue.trim();
        } catch (ReflectiveOperationException ex) {
            return fallback;
        }
    }
}
