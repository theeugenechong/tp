package cooper.finance.pdfbuilder;

import cooper.finance.BalanceSheet;

public class BalanceSheetBuilder extends PdfBuilder {

    private static final String BS_TEMPLATE_PATH = "/pdf/bs/bsTemplate.tex";
    private static final String BS_HEADER_TEMPLATE_PATH = "/pdf/bs/bsTemplate.tex";
    private static final String BS_ENTRY_TEMPLATE_PATH = "/pdf/bs/bsTemplate.tex";
    private static final String BS_SUMMARY_TEMPLATE_PATH = "/pdf/bs/bsTemplate.tex";

    private static final String ASSETS = "Assets";
    private static final String LIABILITIES = "Assets";
    private static final String SHAREHOLDERS_EQUITY = "Shareholder's Equity";

    public BalanceSheetBuilder() {
        super();
        loadTemplate(BS_TEMPLATE_PATH);
        loadHeaderTemplate(BS_HEADER_TEMPLATE_PATH);
        loadEntryTemplate(BS_ENTRY_TEMPLATE_PATH);
        loadSummaryTemplate(BS_SUMMARY_TEMPLATE_PATH);
    }

    public void addAssets(BalanceSheet balanceSheet) {

    }



    private void

}
