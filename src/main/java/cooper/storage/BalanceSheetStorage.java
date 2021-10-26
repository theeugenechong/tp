package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
import cooper.finance.BalanceSheet;
import cooper.finance.FinanceManager;
import cooper.ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class BalanceSheetStorage extends Storage {

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
            Ui.showFileWriteError(e);
            System.exit(1);
        }
    }

    private static void readBalanceSheet(Scanner fileScanner, ArrayList<Integer> balanceSheet) {
        if (fileScanner != null) {
            int bsEntryIndex = 0;
            while (fileScanner.hasNext() && bsEntryIndex <= FinanceManager.endOfSE) {
                String expense = fileScanner.nextLine();
                try {
                    int decodedExpense = decodeExpense(expense);
                    balanceSheet.add(decodedExpense);
                    addNetValues(bsEntryIndex, decodedExpense);
                    bsEntryIndex++;
                } catch (InvalidFileDataException e) {
                    Ui.showInvalidFileDataError();
                }
            }
        }
    }

    private static void addNetValues(int bsEntryIndex, int decodedExpense) {
        if (bsEntryIndex <= FinanceManager.endOfAssets) {
            FinanceManager.netAssets += decodedExpense;
        } else if (bsEntryIndex <= FinanceManager.endOfLiabilities) {
            FinanceManager.netLiabilities += decodedExpense;
        } else {
            FinanceManager.netSE += decodedExpense;
        }
    }

    private static int decodeExpense(String expense) throws InvalidFileDataException {
        if (isInvalidFileData(expense)) {
            throw new InvalidFileDataException();
        }
        return Integer.parseInt(expense);
    }

    private static boolean isInvalidFileData(String expenseAsString) {
        try {
            int dummyExpense = Integer.parseInt(expenseAsString);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    private static void writeBalanceSheet(Path filePath, ArrayList<Integer> balanceSheet) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath.toString(), false);

        for (Integer expense : balanceSheet) {
            String encodedExpense = encodeExpense(expense);
            fileWriter.write(encodedExpense + System.lineSeparator());
        }
        fileWriter.close();
    }

    private static String encodeExpense(Integer expense) {
        return expense.toString();
    }
}
