package cooper.exceptions;

//@@author theeugenechong

/**
 * Exception to signal that a user with a non-admin role, i.e. employee is trying to access features for an admin.
 */
public class InvalidAccessException extends Exception {
}
