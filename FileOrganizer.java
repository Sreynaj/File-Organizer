import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FileOrganizer {

    // Define the file extensions and corresponding folders for each plan
    private static String[][] freeExtensionsAndFolders = {
            {"pdf", "PDFFiles"},
            {"docx", "WordFiles"},
            {"xlsx", "ExcelFiles"},
            // Add more extensions for the Free plan as needed
    };

    private static String[][] basicExtensionsAndFolders = {
            {"csv", "ExcelFiles"},
            {"png", "PNGFiles"},
            // Add more extensions for the Basic plan as needed
    };

    private static String[][] proExtensionsAndFolders = {
            {"ptxx", "PowerPointsFiles"},
            {"JPG", "JPGFiles"},
            {"zip", "ZIPFiles"},
            // Add more extensions for the Pro plan as needed
    };

    private String userPlan;
    private String localPath;

    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to File Organizer!");
        System.out.println("Please select your plan (Free, Basic, Pro):");
        userPlan = scanner.nextLine();

        System.out.println("Enter your local path:");
        localPath = scanner.nextLine();
    }

    public void organizeFiles() {
        try {
            createSubFolder(localPath);

            // Organize files in the source folder
            processFiles(localPath);

            System.out.println("Files organized successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSubFolder(String sourceFolder) {
        String[][] extensionsAndFolders;
        switch (userPlan) {
            case "Free":
                extensionsAndFolders = freeExtensionsAndFolders;
                break;
            case "Basic":
                extensionsAndFolders = basicExtensionsAndFolders;
                break;
            case "Pro":
                extensionsAndFolders = proExtensionsAndFolders;
                break;
            default:
                System.out.println("Invalid plan. Files will not be organized.");
                return;
        }

        for (String[] extensionAndfolder : extensionsAndFolders) {
            String folderName = extensionAndfolder[1];

            File folder = new File(sourceFolder, folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
    }

    private void processFiles(String sourceFolder) throws IOException {
        File[] files = new File(sourceFolder).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

                    // Find the corresponding folder for the file extension based on the user's plan
                    String destinationSubFolder = findDestinationSubFolder(extension);

                    if (destinationSubFolder != null) {
                        // Move the file to the appropriate destination folder
                        Path sourcePath = file.toPath();
                        Path destinationPath = Path.of(sourceFolder, destinationSubFolder, fileName);
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }

    private String findDestinationSubFolder(String extension) {
        String[][] extensionsAndFolders;
        switch (userPlan) {
            case "Free":
                extensionsAndFolders = freeExtensionsAndFolders;
                break;
            case "Basic":
                extensionsAndFolders = basicExtensionsAndFolders;
                break;
            case "Pro":
                extensionsAndFolders = proExtensionsAndFolders;
                break;
            default:
                return null;
        }

        for (String[] extensionAndFolder : extensionsAndFolders) {
            if (extensionAndFolder[0].equalsIgnoreCase(extension)) {
                return extensionAndFolder[1];
            }
        }
        return null; // Return null if no corresponding folder found
    }

    public static void main(String[] args) {
        FileOrganizer organizer = new FileOrganizer();
        organizer.getUserInput();
        organizer.organizeFiles();
    }
}