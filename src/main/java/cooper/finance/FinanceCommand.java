package cooper.finance;

import cooper.CooperState;

//@@author ChrisLangton
public enum FinanceCommand {
    CF,
    BS,
    IDLE;

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
