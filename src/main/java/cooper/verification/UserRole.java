package cooper.verification;

//@@author theeugenechong
/**
 * Represents the roles of members working at a startup - administrators and employees.
 * Different roles have access to different features of cOOPer.
 */
public enum UserRole {
    ADMIN("admin"),
    EMPLOYEE("employee");

    private final String roleDescription;

    UserRole(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public String toString() {
        return roleDescription;
    }
}
