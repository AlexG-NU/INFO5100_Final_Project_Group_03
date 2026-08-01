package ComplianceEnterprise.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/** Small in-memory registry used to demonstrate an external credential lookup. */
public class SimulatedCredentialRegistry {

    private final List<RegistryCredentialRecord> records = new ArrayList<>();
    private final Set<String> intentionalMissingRecords = new HashSet<>();

    public void addRecord(RegistryCredentialRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("Registry record is required.");
        }
        records.add(record);
    }

    public RegistryCredentialRecord find(String contractorName,
            String credentialType) {
        for (RegistryCredentialRecord record : records) {
            if (record.getContractorName().equalsIgnoreCase(contractorName)
                    && record.getCredentialType().equalsIgnoreCase(credentialType)) {
                return record;
            }
        }
        return null;
    }

    public void keepRecordMissing(String contractorName, String credentialType) {
        intentionalMissingRecords.add(key(contractorName, credentialType));
    }

    public boolean shouldAutoGenerate(String contractorName, String credentialType) {
        return !intentionalMissingRecords.contains(key(contractorName, credentialType));
    }

    private String key(String contractorName, String credentialType) {
        return contractorName.trim().toLowerCase() + "|"
                + credentialType.trim().toLowerCase();
    }

    public List<RegistryCredentialRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }
}
