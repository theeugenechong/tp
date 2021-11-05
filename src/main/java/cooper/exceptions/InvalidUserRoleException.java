package cooper.exceptions;

//@@author theeugenechong

/**
 * Exception to signal that the user is trying to sign in with an unrecognised role, i.e. a role which is not one of
 * admin or employee.
 */
public class InvalidUserRoleException extends  Exception {
}
