package cooper.finance.pdfgenerator;

import cooper.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class PdfGenerator {

    protected static final String TEX_LIVE_URL = "https://texlive.net/cgi-bin/latexcgi";
    protected static final String LINE_FEED = "\r\n";
    protected static final String GENERATED_FILE_DIR = System.getProperty("user.dir") + "/output/";

    protected final ArrayList<String> pdfContent;
    protected String template;
    protected String headerTemplate;
    protected String entryTemplate;
    protected String summaryTemplate;

    public PdfGenerator() {
        pdfContent = new ArrayList<>();
    }

    protected void loadTemplate(String resourcePath) {
        // load invoice template
        InputStream invoiceTemplateStream = this.getClass().getResourceAsStream(resourcePath);
        template = Util.inputStreamToString(invoiceTemplateStream);
    }

    protected void loadHeaderTemplate(String resourcePath) {
        // load entry template
        InputStream headerTemplateStream = this.getClass().getResourceAsStream(resourcePath);
        headerTemplate = Util.inputStreamToString(headerTemplateStream);
    }

    protected void loadEntryTemplate(String resourcePath) {
        // load entry template
        InputStream entryTemplateStream = this.getClass().getResourceAsStream(resourcePath);
        entryTemplate = Util.inputStreamToString(entryTemplateStream);
    }

    protected void loadSummaryTemplate(String resourcePath) {
        // load entry template
        InputStream summaryTemplateStream = this.getClass().getResourceAsStream(resourcePath);
        summaryTemplate = Util.inputStreamToString(summaryTemplateStream);
    }

    protected void createHeader(String type) {
        String sectionHeader = headerTemplate;
        sectionHeader = sectionHeader.replace("% {Type}", type);

        pdfContent.add(sectionHeader);
    }

    protected void createEntry(String description, Integer amount) {
        String sectionEntry = entryTemplate;
        sectionEntry = sectionEntry.replace("% {Description}", description);
        sectionEntry = sectionEntry.replace("% {Amount}", amount.toString());

        pdfContent.add(sectionEntry);
    }

    protected void createSummary(String type, Integer total) {
        String sectionSummary = summaryTemplate;
        sectionSummary = sectionSummary.replace("% {Type}", type);
        sectionSummary = sectionSummary.replace("% {Total}", total.toString());

        pdfContent.add(sectionSummary);
    }

    protected String formTexFile() {
        StringBuilder compiledContent = new StringBuilder();
        for (String content : pdfContent) {
            compiledContent.append(content).append(System.lineSeparator());
        }
        return template.replace("% {Content}", compiledContent.toString());
    }

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

    //https://www.baeldung.com/httpurlconnection-post
    public abstract void compilePdfAndSend();

    protected void createBackup(String backupFileName) {
        try {
            File backupFile = new File(GENERATED_FILE_DIR + backupFileName);
            if (!backupFile.exists()) {
                Files.createDirectories(Paths.get(GENERATED_FILE_DIR));
                Files.createFile(Paths.get(GENERATED_FILE_DIR + backupFileName));
            }

            String compiledPdf = formTexFile();
            FileWriter fileWriter = new FileWriter(GENERATED_FILE_DIR + backupFileName, false);
            fileWriter.write(compiledPdf);
            fileWriter.close();
            System.out.println("The backup file has been created successfully.");
        } catch (IOException e) {
            System.out.println("There was a problem creating the backup file.");
        }
    }

    // https://www.baeldung.com/java-download-file
    protected void createPdf(byte[] response, String pdfName) {
        try {
            File generatedPdf = new File(GENERATED_FILE_DIR + pdfName);
            if (!generatedPdf.exists()) {
                Files.createDirectories(Paths.get(GENERATED_FILE_DIR));
                Files.createFile(Paths.get(GENERATED_FILE_DIR + pdfName));
            }

            FileOutputStream fileOutputStream = new FileOutputStream(GENERATED_FILE_DIR + pdfName);
            fileOutputStream.write(response);
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("There was a problem generating the PDF file.");
        }
    }
}

