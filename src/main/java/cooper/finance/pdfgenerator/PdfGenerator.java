package cooper.finance.pdfbuilder;

import cooper.util.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PdfBuilder {

    protected static final String TEX_LIVE_URL = "https://texlive.net/cgi-bin/latexcgi";
    protected static final String LINE_FEED = "\r\n";

    protected final ArrayList<String> pdfContent;
    protected String template;
    protected String headerTemplate;
    protected String entryTemplate;
    protected String summaryTemplate;

    public PdfBuilder() {
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
        sectionSummary = sectionSummary.replace()
    }

    protected String compilePdf() {
        StringBuilder compiledContent = new StringBuilder();
        for (String content : pdfContent) {
            compiledContent.append(content).append("\n");
        }
        return template.replace("% {content}", compiledContent.toString());
    }


    private void sendPdf(HttpURLConnection con, String invoiceCompiled) {
        /*The following code mimic this curl command
        curl -v -L -X POST -o document.pdf -F return=pdf -F engine=pdflatex
        -F 'filecontents[]=' -F 'filename[]=document.tex' 'https://texlive.net/cgi-bin/latexcgi'
        1. there is extra 2 -- at every boundary
        2. there is extra 2 -- at last boundary
        **/

        try {
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=--12345678");
            con.setDoOutput(true);
            // Note: This is the POST multipart/form-data request packet format
            // +"Content-Type: multipart/form-data" + lineFeed
            byte[] input = ("----12345678" + LINE_FEED
                    + "Content-Disposition: form-data; name=\"filecontents[]\"" + LINE_FEED + LINE_FEED
                    + invoiceCompiled + LINE_FEED
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
        } catch (ProtocolException e) {
            System.out.println("Protocol exception occurred");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncoding exception");
        } catch (IOException e) {
            System.out.println("Cannot write to http connection output stream");
        }
    }

    //https://www.baeldung.com/httpurlconnection-post
    public void compileInvoiceAndSend() {
        String compiledPdf = compilePdf();

        try {
            URL url = new URL(TEX_LIVE_URL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            sendPdf(con, compiledPdf);

            // TODO: This is the perfect place to add logging
            String reply = con.getResponseMessage();
            int replyCode = con.getResponseCode();
            if (replyCode == 200) {
                // send success
                byte[] buffer = con.getInputStream().readAllBytes();
                processPostResponse(buffer);
            } else {
                System.out.println("Error encountered when sending post request! Any fallback plan?");
            }
        } catch (MalformedURLException e) {
            System.out.println("Incorrect url");
        } catch (IOException e) {
            System.out.println("Unable to open connection");
        }
    }

    // https://www.baeldung.com/java-download-file
    private void processPostResponse(byte[] response) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "/output.pdf");
            fileOutputStream.write(response);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

