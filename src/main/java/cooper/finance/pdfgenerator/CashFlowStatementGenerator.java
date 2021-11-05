package cooper.finance.pdfgenerator;

import cooper.finance.CashFlow;
import cooper.finance.FinanceManager;
import cooper.ui.FileIoUi;
import cooper.ui.FinanceUi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//@@author theeugenechong

/**
 * Generates the current cash flow statement as a pdf file formatted using LaTeX templates.
 */
public class CashFlowStatementGenerator extends PdfGenerator {

    /* Paths of the files containing the LaTeX templates */
    private static final String CF_TEMPLATE_PATH = "/pdf/cf/cfTemplate.tex";
    private static final String CF_HEADER_TEMPLATE_PATH = "/pdf/cf/cfHeaderTemplate.tex";
    private static final String CF_ENTRY_TEMPLATE_PATH = "/pdf/cf/cfEntryTemplate.tex";
    private static final String CF_SUMMARY_TEMPLATE_PATH = "/pdf/cf/cfSummaryTemplate.tex";

    /* Content of the headers to be added to each section of the cash flow statement */
    private static final String OPERATING_ACTIVITIES = "Operating Activities";
    private static final String INVESTING_ACTIVITIES = "Investing Activities";
    private static final String FINANCING_ACTIVITIES = "Financing Activities";
    private static final String FREE_CASH_FLOW = "Free Cash Flow";

    /* Names of the files created */
    private static final String CF_PDF_FILE = "/CashFlowStatement.pdf";
    private static final String CF_BACKUP_FILE = "/backupCf.txt";

    /* Index of entries in the cash flow statement*/
    private static final int START_OF_OA = 0;
    private static final int START_OF_IA = 5;
    private static final int START_OF_FA = 7;
    private static final int FCF_INDEX = 9;

    /**
     * The constructor loads the templates from the template files.
     */
    public CashFlowStatementGenerator() {
        super();
        loadTemplate(CF_TEMPLATE_PATH);
        loadHeaderTemplate(CF_HEADER_TEMPLATE_PATH);
        loadEntryTemplate(CF_ENTRY_TEMPLATE_PATH);
        loadSummaryTemplate(CF_SUMMARY_TEMPLATE_PATH);
    }

    /**
     * Add the OA section from {@code cashFlow} into {@code pdfContent}.
     * @param cashFlow Cash flow statement containing the entries to be added to the pdf file.
     */
    public void addCfFromOperatingActivities(CashFlow cashFlow) {
        ArrayList<Integer> cf = cashFlow.getCashFlowStatement();
        createHeader(OPERATING_ACTIVITIES);
        for (int i = START_OF_OA; i <= FinanceManager.endOfOA; i++) {
            createEntry(FinanceUi.CASH_FLOW_UI[i].trim(), cf.get(i));
        }
        createSummary(OPERATING_ACTIVITIES, FinanceManager.netOA);
    }

    /**
     * Add the IA section from {@code cashFlow} into {@code pdfContent}.
     * @param cashFlow Cash flow statement containing the entries to be added to the pdf file.
     */
    public void addCfFromInvestingActivities(CashFlow cashFlow) {
        ArrayList<Integer> cf = cashFlow.getCashFlowStatement();
        createHeader(INVESTING_ACTIVITIES);
        for (int i = START_OF_IA; i <= FinanceManager.endOfIA; i++) {
            createEntry(FinanceUi.CASH_FLOW_UI[i].trim(), cf.get(i));
        }
        createSummary(INVESTING_ACTIVITIES, FinanceManager.netIA);
    }

    /**
     * Add the FA section from {@code cashFlow} into {@code pdfContent}.
     * @param cashFlow Cash flow statement containing the entries to be added to the pdf file.
     */
    public void addCfFromFinancingActivities(CashFlow cashFlow) {
        ArrayList<Integer> cf = cashFlow.getCashFlowStatement();
        createHeader(FINANCING_ACTIVITIES);
        for (int i = START_OF_FA; i <= FinanceManager.endOfFA; i++) {
            createEntry(FinanceUi.CASH_FLOW_UI[i].trim(), cf.get(i));
        }
        createSummary(FINANCING_ACTIVITIES, FinanceManager.netFA);
    }

    /**
     * Adds Free Cash Flow section from {@code cashFlow} into {@code pdfContent}.
     * @param cashFlow Cash flow statement containing the entries to be added to the pdf file.
     */
    public void addFreeCashFlow(CashFlow cashFlow) {
        ArrayList<Integer> cf = cashFlow.getCashFlowStatement();
        createHeader(FREE_CASH_FLOW);
        createEntry(FREE_CASH_FLOW, cf.get(FCF_INDEX));
    }

    /**
     * Sends the LaTeX file representing the cash flow statement to be compiled by an online LaTeX editor by making a
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
                createPdf(buffer, CF_PDF_FILE);
            } else {
                FileIoUi.showPostRequestError();
            }
        } catch (MalformedURLException e) {
            FileIoUi.showMalformedUrlError();
        } catch (IOException e) {
            FileIoUi.showConnectionError();
            createBackup(formTexFile(), CF_BACKUP_FILE);
        } finally {
            pdfContent.clear();
        }
    }
}
