package cooper.finance.pdfgenerator;

import cooper.finance.BalanceSheet;
import cooper.finance.FinanceManager;
import cooper.ui.FileIoUi;
import cooper.ui.FinanceUi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BalanceSheetGenerator extends PdfGenerator {

    private static final String BS_TEMPLATE_PATH = "/pdf/bs/bsTemplate.tex";
    private static final String BS_HEADER_TEMPLATE_PATH = "/pdf/bs/bsHeaderTemplate.tex";
    private static final String BS_ENTRY_TEMPLATE_PATH = "/pdf/bs/bsEntryTemplate.tex";
    private static final String BS_SUMMARY_TEMPLATE_PATH = "/pdf/bs/bsSummaryTemplate.tex";

    private static final String ASSETS = "Assets";
    private static final String LIABILITIES = "Liabilities";
    private static final String SHAREHOLDERS_EQUITY = "Shareholder's Equity";

    private static final String BS_PDF_FILE = "/BalanceSheet.pdf";
    private static final String BS_BACKUP_FILE = "/backupBs.txt";

    public BalanceSheetGenerator() {
        super();
        loadTemplate(BS_TEMPLATE_PATH);
        loadHeaderTemplate(BS_HEADER_TEMPLATE_PATH);
        loadEntryTemplate(BS_ENTRY_TEMPLATE_PATH);
        loadSummaryTemplate(BS_SUMMARY_TEMPLATE_PATH);
    }

    public void addAssets(BalanceSheet balanceSheet) {
        ArrayList<Integer> bs = balanceSheet.getBalanceSheet();
        createHeader(ASSETS);
        for (int i = 0; i <= FinanceManager.endOfAssets; i++) {
            createEntry(FinanceUi.BALANCE_SHEET_UI[i].trim(), bs.get(i));
        }
        createSummary(ASSETS, FinanceManager.netAssets);
    }

    public void addLiabilities(BalanceSheet balanceSheet) {
        ArrayList<Integer> bs = balanceSheet.getBalanceSheet();
        createHeader(LIABILITIES);
        for (int i = 6; i <= FinanceManager.endOfLiabilities; i++) {
            createEntry(FinanceUi.BALANCE_SHEET_UI[i].trim(), bs.get(i));
        }
        createSummary(LIABILITIES, FinanceManager.netLiabilities);
    }

    public void addShareholderEquity(BalanceSheet balanceSheet) {
        ArrayList<Integer> bs = balanceSheet.getBalanceSheet();
        createHeader(SHAREHOLDERS_EQUITY);
        for (int i = 10; i <= FinanceManager.endOfSE; i++) {
            createEntry(FinanceUi.BALANCE_SHEET_UI[i].trim(), bs.get(i));
        }
        createSummary(SHAREHOLDERS_EQUITY, FinanceManager.netSE);
    }

    public void addBalance() {
        int balance = FinanceManager.netAssets - FinanceManager.netLiabilities - FinanceManager.netSE;
        String balanceAsString = Integer.valueOf(balance).toString();
        template = template.replace("% {Balance}", balanceAsString);
    }

    @Override
    public void compilePdfAndSend() {
        String texFileToCompile = formTexFile();

        try {
            URL url = new URL(TEX_LIVE_URL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            sendPdf(con, texFileToCompile);

            // This is the perfect place to add logging
            String reply = con.getResponseMessage();
            int replyCode = con.getResponseCode();
            if (replyCode == 200) {
                // send success
                byte[] buffer = con.getInputStream().readAllBytes();
                createPdf(buffer, BS_PDF_FILE);
            } else {
                FileIoUi.showPostRequestError();
            }
        } catch (MalformedURLException e) {
            FileIoUi.showMalformedUrlError();
        } catch (IOException e) {
            FileIoUi.showConnectionError();
            createBackup(formTexFile(), BS_BACKUP_FILE);
        } finally {
            pdfContent.clear();
        }
    }
}
