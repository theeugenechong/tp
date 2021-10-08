package cooper.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import cooper.ui.Ui;
import cooper.parser.CommandParser;
import cooper.command.Command;
import cooper.verification.SignInDetails;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.exceptions.InvalidArgumentException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.verification.Verifier;

public class Storage {

    private File storageFile;
    private ArrayList<String> historyInputs;

    public Storage() {
        historyInputs = new ArrayList<String>();
        String baseDir = System.getProperty("user.dir") + "/tmp";
        String fileName = "/storage.txt";
        Path folderDir = Paths.get(baseDir);
        Path fileDir = Paths.get(baseDir + fileName);
        try {
            if (Files.notExists(folderDir)) {
                Files.createDirectories(folderDir);
            }
        } catch (IOException e) {
            Ui.showInvalidFilePathError();
            // Wrong URI, no storage file is created
            storageFile = null;
            return;
        }
        // load storage file
        storageFile = new File(fileDir.toString());
    }

    public void loadLoginDetails(Verifier cooperVerifier) {
        Ui.suppressOutput();
        try {
            Scanner sc = new Scanner(storageFile);
            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.split(" ")[0].equals("register")) {
                    cooperVerifier.verify(input);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            Ui.showNoStorage();
        }
        Ui.unSuppressOutput();
        return;
    }



    public void loadResources(SignInDetails signInDetails, FinanceManager cooperFinanceManager, 
            MeetingManager cooperMeetingManager) {
        Ui.suppressOutput();
        try {
            Scanner sc = new Scanner(storageFile);
            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (!input.split(" ")[0].equals("register")) { // do not load register instructions
                    Command command = CommandParser.parse(input);
                    command.execute(signInDetails, cooperFinanceManager, cooperMeetingManager);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            Ui.showNoStorage();
        } catch (InvalidArgumentException e) {
            Ui.showInvalidCommandArgumentError();
        } catch (NumberFormatException e) {
            Ui.showInvalidNumberError();
        } catch (UnrecognisedCommandException e) {
            Ui.showUnrecognisedCommandError();
        }
        Ui.unSuppressOutput();
        return;
    }


    public void saveCommand(String input) {
        historyInputs.add(input);
    }

    public void saveStorage() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile, true));
            for (String input : historyInputs) {
                writer.write(input);
                writer.newLine();
            }
            writer.close();
            historyInputs.clear();
        } catch (IOException e) {
            Ui.showInvalidFilePathError();
        }
    }












}
