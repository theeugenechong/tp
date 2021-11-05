package cooper.finance.pdfgenerator;

import cooper.finance.BalanceSheet;
import cooper.finance.FinanceManager;
import cooper.ui.FileIoUi;
import cooper.ui.FinanceUi;
import cooper.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//@@author theeugenechong

/**
 * Generates the current balance sheet as a pdf file formatted using LaTeX templates.
 */
public class BalanceSheetGenerator extends PdfGenerator {

    /* Paths of the files containing the LaTeX templates */
    private static final String BS_TEMPLATE_PATH = "/pdf/bs/bsTemplate.tex";
    private static final String BS_HEADER_TEMPLATE_PATH = "/pdf/bs/bsHeaderTemplate.tex";
    private static final String BS_ENTRY_TEMPLATE_PATH = "/pdf/bs/bsEntryTemplate.tex";
    private static final String BS_SUMMARY_TEMPLATE_PATH = "/pdf/bs/bsSummaryTemplate.tex";
    private static final String BS_CHECK_VALUE_TEMPLATE_PATH = "/pdf/bs/bsCheckValue.tex";

    /* Index of entries in the balance sheet*/
    protected static final int START_OF_ASSETS = 0;
    protected static final int START_OF_LIABILITIES = 6;
    protected static final int START_OF_SE = 10;

    /* Extra identifier for the check value of the balance sheet*/
    protected static final String CHECK_VALUE_IDENTIFIER = "% {Balance}";

    /* Extra template attribute for the check value of the balance sheet*/
    private String checkValueTemplate;

    /* Content of the headers to be added to each section of the balance sheet */
    private static final String ASSETS = "Assets";
    private static final String LIABILITIES = "Liabilities";
    private static final String SHAREHOLDERS_EQUITY = "Shareholder's Equity";

    /* Names of the files created */
    private static final String BS_PDF_FILE = "/BalanceSheet.pdf";
    private static final String BS_BACKUP_FILE = "/backupBs.txt";

    /**
     * The constructor loads the templates from the template files.
     */
    public BalanceSheetGenerator() {
        super();
        loadTemplate(BS_TEMPLATE_PATH);
        loadHeaderTemplate(BS_HEADER_TEMPLATE_PATH);
        loadEntryTemplate(BS_ENTRY_TEMPLATE_PATH);
        loadSummaryTemplate(BS_SUMMARY_TEMPLATE_PATH);
        loadCheckValueTemplate();
    }

    /**
     * Loads the check value template from {@code resourcePath} and converts it to a string.
     */
    private void loadCheckValueTemplate() {
        InputStream checkValueTemplateStream = this.getClass().getResourceAsStream(BS_CHECK_VALUE_TEMPLATE_PATH);
        checkValueTemplate = Util.inputStreamToString(checkValueTemplateStream);
    }

    /**
     * Add the assets section from {@code balanceSheet} into {@code pdfContent}.
     * @param balanceSheet Balance sheet containing the entries to be added to the pdf file.
     */
    public void addAssets(BalanceSheet balanceSheet) {
        ArrayList<Integer> bs = balanceSheet.getBalanceSheet();
        createHeader(ASSETS);
        for (int i = START_OF_ASSETS; i <= FinanceManager.endOfAssets; i++) {
            createEntry(FinanceUi.BALANCE_SHEET_UI[i].trim(), bs.get(i));
        }
        createSummary(ASSETS, FinanceManager.netAssets);
    }

    /**
     * Add the liabilities section from {@code balanceSheet} into {@code pdfContent}.
     * @param balanceSheet Balance sheet containing the entries to be added to the pdf file.
     */
    public void addLiabilities(BalanceSheet balanceSheet) {
        ArrayList<Integer> bs = balanceSheet.getBalanceSheet();
        createHeader(LIABILITIES);
        for (int i = START_OF_LIABILITIES; i <= FinanceManager.endOfLiabilities; i++) {
            createEntry(FinanceUi.BALANCE_SHEET_UI[i].trim(), bs.get(i));
        }
        createSummary(LIABILITIES, FinanceManager.netLiabilities);
    }

    /**
     * Add the shareholder's equity section from {@code balanceSheet} into {@code pdfContent}.
     * @param balanceSheet Balance sheet containing the entries to be added to the pdf file.
     */
    public void addShareholderEquity(BalanceSheet balanceSheet) {
        ArrayList<Integer> bs = balanceSheet.getBalanceSheet();
        createHeader(SHAREHOLDERS_EQUITY);
        for (int i = START_OF_SE; i <= FinanceManager.endOfSE; i++) {
            createEntry(FinanceUi.BALANCE_SHEET_UI[i].trim(), bs.get(i));
        }
        createSummary(SHAREHOLDERS_EQUITY, FinanceManager.netSE);
    }

    /**
     * Creates the final check value of the balance sheet by replacing the identifier from the template
     * with {@code balance}.
     * @param balance check value of balance sheet which is assets + liabilities - SE
     */
    private void createCheckValue(String balance) {
        String checkValue = checkValueTemplate.replace(CHECK_VALUE_IDENTIFIER, balance);

        pdfContent.add(checkValue);
    }

    /**
     * Computes and adds the check value of the {@code balanceSheet} into {@code pdfContent}.
     */
    public void addCheckValue() {
        int balance = FinanceManager.netAssets + FinanceManager.netLiabilities - FinanceManager.netSE;
        String balanceAsString = Integer.valueOf(balance).toString();
        createCheckValue(balanceAsString);
    }

    /**
     * Sends the LaTeX file representing the balance sheet to be compiled by an online LaTeX editor by making a
     * JSON post request. In the event that there is a connection problem, an error message is printed and a backup
     * {@code .txt} file is created.
     */
    @Override
    public void compilePdfAndSend() {
        String texFileToCompile = formTexFile();

        try {
            URL url = new URL(TEX_LIVE_URL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            sendPdf(con, texFileToCompile);

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
