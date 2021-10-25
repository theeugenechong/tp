package cooper.finance.pdfgenerator;

import cooper.finance.CashFlow;
import cooper.finance.FinanceManager;
import cooper.ui.FinanceUI;
import cooper.ui.Ui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CashFlowStatementGenerator extends PdfGenerator {

    private static final String CF_TEMPLATE_PATH = "/pdf/cf/cfTemplate.tex";
    private static final String CF_HEADER_TEMPLATE_PATH = "/pdf/cf/cfHeaderTemplate.tex";
    private static final String CF_ENTRY_TEMPLATE_PATH = "/pdf/cf/cfEntryTemplate.tex";
    private static final String CF_SUMMARY_TEMPLATE_PATH = "/pdf/cf/cfSummaryTemplate.tex";

    private static final String OPERATING_ACTIVITIES = "Operating Activities";
    private static final String INVESTING_ACTIVITIES = "Investing Activities";
    private static final String FINANCING_ACTIVITIES = "Financing Activities";

    private static final String CF_PDF_FILE = "CashFlowStatement.pdf";
    private static final String CF_BACKUP_FILE = "backupCf.txt";

    public CashFlowStatementGenerator() {
        super();
        loadTemplate(CF_TEMPLATE_PATH);
        loadHeaderTemplate(CF_HEADER_TEMPLATE_PATH);
        loadEntryTemplate(CF_ENTRY_TEMPLATE_PATH);
        loadSummaryTemplate(CF_SUMMARY_TEMPLATE_PATH);
    }

    public void addCfFromOperatingActivities(CashFlow cashFlow) {
        ArrayList<Integer> cf = cashFlow.getCashFlowStatement();
        createHeader(OPERATING_ACTIVITIES);
        for (int i = 0; i <= FinanceManager.endOfOA; i++) {
            createEntry(FinanceUI.cashFlowUI[i].trim(), cf.get(i));
        }
        createSummary(OPERATING_ACTIVITIES, FinanceManager.netOA);
    }

    public void addCfFromInvestingActivities(CashFlow cashFlow) {
        ArrayList<Integer> cf = cashFlow.getCashFlowStatement();
        createHeader(INVESTING_ACTIVITIES);
        for (int i = 5; i <= FinanceManager.endOfIA; i++) {
            createEntry(FinanceUI.cashFlowUI[i].trim(), cf.get(i));
        }
        createSummary(INVESTING_ACTIVITIES, FinanceManager.netIA);
    }

    public void addCfFromFinancingActivities(CashFlow cashFlow) {
        ArrayList<Integer> cf = cashFlow.getCashFlowStatement();
        createHeader(FINANCING_ACTIVITIES);
        for (int i = 7; i <= FinanceManager.endOfFA; i++) {
            createEntry(FinanceUI.cashFlowUI[i].trim(), cf.get(i));
        }
        createSummary(FINANCING_ACTIVITIES, FinanceManager.netFA);
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
                createPdf(buffer, CF_PDF_FILE);
            } else {
                Ui.showPostRequestError();
            }
        } catch (MalformedURLException e) {
            Ui.showMalformedUrlError();
        } catch (IOException e) {
            Ui.showConnectionError();
            createBackup(formTexFile(), CF_BACKUP_FILE);
        } finally {
            pdfContent.clear();
        }
    }
}
