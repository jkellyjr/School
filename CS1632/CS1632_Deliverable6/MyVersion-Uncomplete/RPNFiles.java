
import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import java.util.Scanner;
import java.util.List;

public class RPNFiles {
    private String outputFilename;
    private String[] files;
    private Scanner input;

    public RPNFiles(String[] files) {
        outputFilename = "%1632fun%please##dont!!@@take44this2name0.txt";
        this.files = files;
        concatFiles();
    }

    public void concatFiles() {
        Path output = Paths.get(outputFilename);
        Charset charset = StandardCharsets.UTF_8;
        for (String file: files) {
            Path tmp = Paths.get(file);
            try {
                List<String> lines = Files.readAllLines(tmp, charset);
                Files.write(output, lines, StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public Scanner getScanner() {
        try {
            return new Scanner(new File(outputFilename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
