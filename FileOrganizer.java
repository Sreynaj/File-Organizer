import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileOrganizer {

    // Define the file extensions and corresponding folders
    private static String[][] extensionsAndFolders = {
            {"pdf", "PDFFiles"},
            {"docx", "WordFiles"},
            {"xlsx", "ExcelFiles"},
            {"csv", "ExcelFiles"},
            // Add more extensions and folders as needed
    };    

    // Specify the source folder
    private static String sourceFolder = "C:/Users/PC/Documents/IFL/CE";

    public static void main(String[] args) {

        try {
            
            createSubFolder(sourceFolder);

            // Organize files in the source folder
            organizeFiles(sourceFolder);

            System.out.println("Files organized successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSubFolder(String sourceFolder) {
        for (String[] extensionAndfolder : extensionsAndFolders) {
            String folderName = extensionAndfolder[1];

            File folder = new File(sourceFolder, folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
    }

    private static void organizeFiles(String sourceFolder) throws IOException {
        File[] files = new File(sourceFolder).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

                    // Find the corresponding folder for the file extension
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

    private static String findDestinationSubFolder(String extension) {
        
        for (String[] extensionAndFolder : extensionsAndFolders) {
            if (extensionAndFolder[0].equalsIgnoreCase(extension)) {
                return extensionAndFolder[1];
            }
        }
        return null; // Return null if no corresponding folder found
    }
}
