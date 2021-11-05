package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
import cooper.finance.CashFlow;
import cooper.finance.FinanceManager;
import cooper.ui.FileIoUi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//@@author ChrisLangton

public class CashFlowStorage extends Storage {

    protected static final String CASH_FLOW_STATEMENT_TXT = "cashFlowStatement.txt";

    public CashFlowStorage(String filePath) {
        super(filePath);
    }

    public void loadCashFlowStatement(CashFlow cooperCashFlowStatement) {
        ArrayList<Integer> cashFlowStatement = cooperCashFlowStatement.getCashFlowStatement();
        Scanner fileScanner = getScanner(filePath);
        readCashFlowStatement(fileScanner, cashFlowStatement);
    }

    public void saveCashFlowStatement(CashFlow cooperCashFlowStatement) {
        try {
            writeCashFlowStatement(filePath, cooperCashFlowStatement.getCashFlowStatement());
        } catch (IOException e) {
            FileIoUi.showFileWriteError(e);
            System.exit(1);
        }
    }

    private void readCashFlowStatement(Scanner fileScanner, ArrayList<Integer> cashFlowStatement) {
        if (fileScanner == null) {
            return;
        }

        int cfEntryIndex = 0;
        while (fileScanner.hasNext() && cfEntryIndex <= FinanceManager.freeCashFlow) {
            String expense = fileScanner.nextLine();
            try {
                int decodedExpense = decodeExpense(expense);
                cashFlowStatement.set(cfEntryIndex, decodedExpense);
                addNetValues(cfEntryIndex, decodedExpense);
                cfEntryIndex++;
            } catch (InvalidFileDataException e) {
                FileIoUi.showInvalidFileDataError(e);
            }
        }
    }

    private void addNetValues(int cfEntryIndex, int decodedExpense) {
        if (cfEntryIndex <= FinanceManager.endOfOA) {
            FinanceManager.netOA += decodedExpense;
        } else if (cfEntryIndex <= FinanceManager.endOfIA) {
            FinanceManager.netIA += decodedExpense;
        } else {
            FinanceManager.netFA += decodedExpense;
        }
    }

    private int decodeExpense(String expense) throws InvalidFileDataException {
        if (isInvalidFileData(expense)) {
            throw new InvalidFileDataException(CASH_FLOW_STATEMENT_TXT);
        }
        return Integer.parseInt(expense);
    }

    private static boolean isInvalidFileData(String expenseAsString) {
        try {
            int expense = Integer.parseInt(expenseAsString);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    private void writeCashFlowStatement(String filePath, ArrayList<Integer> cashFlowStatement)
            throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (Integer expense : cashFlowStatement) {
            String encodedExpense = encodeExpense(expense);
            fileWriter.write(encodedExpense + System.lineSeparator());
        }
        fileWriter.close();
    }

    private String encodeExpense(Integer expense) {
        return expense.toString();
    }
}
