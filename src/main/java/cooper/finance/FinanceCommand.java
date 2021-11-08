package cooper.finance;

import cooper.CooperState;

//@@author ChrisLangton

/**
 * The enum to indicate the state of the finance function.
 */
public enum FinanceCommand {
    CF,
    BS,
    IDLE;

    /**
     * function changes the state of the finance function.
     * @param state the state
     * @return
     */
    public static FinanceCommand getCommandFromState(CooperState state) {
        switch (state) {
        case CF:
            return CF;
        case BS:
            return BS;
        default:
            return IDLE;
        }
    }
}
