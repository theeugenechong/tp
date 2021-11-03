package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
import cooper.finance.BalanceSheet;
import cooper.finance.FinanceManager;
import cooper.ui.FileIoUi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//@@author ChrisLangton

public class BalanceSheetStorage extends Storage {

    protected static final String BALANCE_SHEET_TXT = "balanceSheet.txt";

    public BalanceSheetStorage(String filePath) {
        super(filePath);
    }

    public void loadBalanceSheet(BalanceSheet cooperBalanceSheet) {
        ArrayList<Integer> balanceSheet = cooperBalanceSheet.getBalanceSheet();
        Scanner fileScanner = getScanner(filePath);
        readBalanceSheet(fileScanner, balanceSheet);
    }

    public void saveBalanceSheet(BalanceSheet cooperBalanceSheet) {
        try {
            writeBalanceSheet(filePath, cooperBalanceSheet.getBalanceSheet());
        } catch (IOException e) {
            FileIoUi.showFileWriteError(e);
            System.exit(1);
        }
    }

    private void readBalanceSheet(Scanner fileScanner, ArrayList<Integer> balanceSheet) {
        if (fileScanner == null) {
            return;
        }

        int bsEntryIndex = 0;
        while (fileScanner.hasNext() && bsEntryIndex <= FinanceManager.endOfSE) {
            String expense = fileScanner.nextLine();
            try {
                int decodedExpense = decodeExpense(expense);
                balanceSheet.set(bsEntryIndex, decodedExpense);
                addNetValues(bsEntryIndex, decodedExpense);
                bsEntryIndex++;
            } catch (InvalidFileDataException e) {
                FileIoUi.showInvalidFileDataError(e);
            }
        }
    }

    private void addNetValues(int bsEntryIndex, int decodedExpense) {
        if (bsEntryIndex <= FinanceManager.endOfAssets) {
            FinanceManager.netAssets += decodedExpense;
        } else if (bsEntryIndex <= FinanceManager.endOfLiabilities) {
            FinanceManager.netLiabilities += decodedExpense;
        } else {
            FinanceManager.netSE += decodedExpense;
        }
    }

    private int decodeExpense(String expense) throws InvalidFileDataException {
        if (isInvalidFileData(expense)) {
            throw new InvalidFileDataException(BALANCE_SHEET_TXT);
        }
        return Integer.parseInt(expense);
    }

    private boolean isInvalidFileData(String expenseAsString) {
        try {
            int dummyExpense = Integer.parseInt(expenseAsString);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    private void writeBalanceSheet(String filePath, ArrayList<Integer> balanceSheet) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (Integer expense : balanceSheet) {
            String encodedExpense = encodeExpense(expense);
            fileWriter.write(encodedExpense + System.lineSeparator());
        }
        fileWriter.close();
    }

    private String encodeExpense(Integer expense) {
        return expense.toString();
    }
}
