package cooper.finance.pdfgenerator;

import cooper.ui.FileIoUi;
import cooper.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//@@author theeugenechong

/**
 * Generates the pdf file for balance sheet / cash flow statement.
 */
public abstract class PdfGenerator {

    protected static final String TEX_LIVE_URL = "https://texlive.net/cgi-bin/latexcgi";
    protected static final String LINE_FEED = "\r\n";

    protected static final String GENERATED_FILE_DIR = "output";

    /* Identifiers used for creating the pdf */
    protected static final String TYPE_IDENTIFIER = "% {Type}";
    protected static final String DESCRIPTION_IDENTIFIER = "% {Description}";
    protected static final String AMOUNT_IDENTIFIER = "% {Amount}";
    protected static final String TOTAL_IDENTIFIER = "% {Total}";
    protected static final String CONTENT_IDENTIFIER = "% {Content}";

    protected final ArrayList<String> pdfContent;
    protected String template;
    protected String headerTemplate;
    protected String entryTemplate;
    protected String summaryTemplate;

    public PdfGenerator() {
        pdfContent = new ArrayList<>();
    }

    /**
     * Loads the file template from {@code resourcePath} and converts it to a string.
     * @param resourcePath Path of the file template
     */
    protected void loadTemplate(String resourcePath) {
        InputStream templateStream = this.getClass().getResourceAsStream(resourcePath);
        template = Util.inputStreamToString(templateStream);
    }

    /**
     * Loads the section header template from {@code resourcePath} and converts it to a string.
     * @param resourcePath Path of the section header template
     */
    protected void loadHeaderTemplate(String resourcePath) {
        // load header template
        InputStream headerTemplateStream = this.getClass().getResourceAsStream(resourcePath);
        headerTemplate = Util.inputStreamToString(headerTemplateStream);
    }

    /**
     * Loads the entry template from {@code resourcePath} and converts it to a string. An entry refers to an entry in
     * the actual balance sheet.
     * @param resourcePath Path of the entry template
     */
    protected void loadEntryTemplate(String resourcePath) {
        // load entry template
        InputStream entryTemplateStream = this.getClass().getResourceAsStream(resourcePath);
        entryTemplate = Util.inputStreamToString(entryTemplateStream);
    }

    /**
     * Loads the summary template from {@code resourcePath} and converts it to a string. Summary is the section of
     * the pdf which shows Net and Total values.
     * @param resourcePath Path of the summary template
     */
    protected void loadSummaryTemplate(String resourcePath) {
        // load summary template
        InputStream summaryTemplateStream = this.getClass().getResourceAsStream(resourcePath);
        summaryTemplate = Util.inputStreamToString(summaryTemplateStream);
    }

    /**
     * Creates the section header by replacing the identifiers from the template with {@code type}.
     */
    protected void createHeader(String type) {
        String sectionHeader = headerTemplate;
        sectionHeader = sectionHeader.replace(TYPE_IDENTIFIER, type);

        pdfContent.add(sectionHeader);
    }

    /**
     * Creates an entry by replacing the identifiers from the template with {@code description} and {@code amount} as
     * a string.
     */
    protected void createEntry(String description, Integer amount) {
        String sectionEntry = entryTemplate;
        sectionEntry = sectionEntry.replace(DESCRIPTION_IDENTIFIER, description);
        sectionEntry = sectionEntry.replace(AMOUNT_IDENTIFIER, amount.toString());

        pdfContent.add(sectionEntry);
    }

    /**
     * Creates the summary by replacing the identifiers from the template with {@code type} and {@code total} as a
     * string.
     */
    protected void createSummary(String type, Integer total) {
        String sectionSummary = summaryTemplate;
        sectionSummary = sectionSummary.replace(TYPE_IDENTIFIER, type);
        sectionSummary = sectionSummary.replace(TOTAL_IDENTIFIER, total.toString());

        pdfContent.add(sectionSummary);
    }

    /**
     * Combines all the strings in {@code pdfContent} into a continuous string to be sent to a LaTeX editor.
     * @return a string representing the {@code .tex} file to be compiled.
     */
    protected String formTexFile() {
        StringBuilder compiledContent = new StringBuilder();
        for (String content : pdfContent) {
            compiledContent.append(content).append(System.lineSeparator());
        }
        return template.replace(CONTENT_IDENTIFIER, compiledContent.toString());
    }

    //@@author Rrraaaeee
    /**
     * Sends the tex file formed to be compiled at the URL.
     * The following code mimic this curl command:
     * {@code curl -v -L -X POST -o document.pdf -F return=pdf -F engine=pdflatex}
     * -F 'filecontents[]=' -F 'filename[]=document.tex' 'https://texlive.net/cgi-bin/latexcgi'
     * 1. there is extra 2 -- at every boundary
     * 2. there is extra 2 -- at last boundary
     */
    protected void sendPdf(HttpURLConnection con, String texFileToCompile) throws IOException {

        try {
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=--12345678");
            con.setDoOutput(true);
            // Note: This is the POST multipart/form-data request packet format
            // +"Content-Type: multipart/form-data" + lineFeed
            byte[] input = ("----12345678" + LINE_FEED
                    + "Content-Disposition: form-data; name=\"filecontents[]\"" + LINE_FEED + LINE_FEED
                    + texFileToCompile + LINE_FEED
                    + "----12345678" + LINE_FEED
                    + "Content-Disposition: form-data; name=\"filename[]\"" + LINE_FEED + LINE_FEED
                    + "document.tex" + LINE_FEED
                    + "----12345678" + LINE_FEED
                    + "Content-Disposition: form-data; name=\"engine\"" + LINE_FEED + LINE_FEED
                    + "pdflatex" + LINE_FEED
                    + "----12345678" + LINE_FEED
                    + "Content-Disposition: form-data; name=\"return\"" + LINE_FEED + LINE_FEED
                    + "pdf" + LINE_FEED
                    + "----12345678" + LINE_FEED
                    + "--").getBytes(StandardCharsets.UTF_8);
            OutputStream connectionOutput = con.getOutputStream();
            connectionOutput.write(input, 0, input.length);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    //@@author theeugenechong
    //https://www.baeldung.com/httpurlconnection-post
    public abstract void compilePdfAndSend();


    /**
     * Creates the backup file in the event that the creation of the pdf file which requires an internet connection
     * fails.
     * @param texFileToCompile a string representing the contents of the tex file to be compiled
     * @param backupFileName name of the backup file
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void createBackup(String texFileToCompile, String backupFileName) {
        try {
            File backupFile = new File(GENERATED_FILE_DIR + backupFileName);
            if (!backupFile.exists()) {
                File backUpDir = new File(GENERATED_FILE_DIR);
                backUpDir.mkdir();
                backupFile.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(GENERATED_FILE_DIR + backupFileName, false);
            fileWriter.write(texFileToCompile);
            fileWriter.close();
            FileIoUi.showBackupFileSuccessfullyCreated();
        } catch (IOException e) {
            FileIoUi.showBackupFileCreationError(e);
        }
    }

    /**
     * Creates the pdf file.
     * @param response JSON response received from the online LaTeX editor
     * @param pdfName name of the pdf file created
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    // https://www.baeldung.com/java-download-file
    protected void createPdf(byte[] response, String pdfName) {
        try {
            File generatedPdf = new File(GENERATED_FILE_DIR + pdfName);
            if (!generatedPdf.exists()) {
                File pdfDir = new File(GENERATED_FILE_DIR);
                pdfDir.mkdir();
                generatedPdf.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(GENERATED_FILE_DIR + pdfName);
            fileOutputStream.write(response);
            fileOutputStream.close();
            FileIoUi.showPdfSuccessfullyGenerated();
        } catch (IOException e) {
            FileIoUi.showPdfGenerationError(e);
        }
    }
}

