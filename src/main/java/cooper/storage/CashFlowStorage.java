package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
import cooper.finance.BalanceSheet;
import cooper.finance.CashFlow;
import cooper.finance.FinanceManager;
import cooper.ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class CashFlowStorage extends Storage {

    public CashFlowStorage(String filePath) { super(filePath); }

    public void loadCashFlowStatement(CashFlow cooperCashFlowStatement) {
        ArrayList<Integer> cashFlowStatement = cooperCashFlowStatement.getCashFlowStatement();
        Scanner fileScanner = getScanner(filePath);
        readCashFlowStatement(fileScanner, cashFlowStatement);
    }

    public void saveCashFlowStatement(CashFlow cooperCashFlowStatement) {
        try {
            writeCashFlowStatement(filePath, cooperCashFlowStatement.getCashFlowStatement());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void readCashFlowStatement(Scanner fileScanner, ArrayList<Integer> cashFlowStatement) {
        if (fileScanner != null) {
            while (fileScanner.hasNext()) {
                String expense = fileScanner.nextLine();
                try {
                    int decodedExpense = decodeExpense(expense);
                    cashFlowStatement.add(decodedExpense);
                } catch (InvalidFileDataException e) {
                    Ui.showInvalidFileDataError();
                }
            }
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
            int expense = Integer.parseInt(expenseAsString);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    private static void writeCashFlowStatement(Path filePath, ArrayList<Integer> cashFlowStatement) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath.toString(), false);

        for (Integer expense : cashFlowStatement) {
            String encodedExpense = encodeExpense(expense);
            fileWriter.write(encodedExpense + System.lineSeparator());
        }
        fileWriter.close();
    }

    private static String encodeExpense(Integer expense) {
        return expense.toString();
    }
}
