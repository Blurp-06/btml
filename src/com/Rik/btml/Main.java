package com.Rik.btml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

import static com.Rik.btml.GetBlocks.generateBlocks;

public class Main {

    public static String fileName;
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) throws IOException {
        fileName = args[0];
        LinkedList<String> fileText = getFileContent(fileName);

        // Trimming everything
        for(int i = 0; i < fileText.size(); i++) {
            fileText.set(i, fileText.get(i).trim());
        }

        // Doing the macro stuff
        System.out.println("[Macros: "+dtf.format(LocalDateTime.now())+"] Starting...");
        fileText = macros.replaceAllWithMacroValue(fileText);
        System.out.println("[Macros: "+dtf.format(LocalDateTime.now())+"] Done...");

        // Trimming everything
        for(int i = 0; i < fileText.size(); i++) {
            fileText.set(i, fileText.get(i).trim());
        }




        System.out.println("[Blocking: "+dtf.format(LocalDateTime.now())+"] Starting...");
        LinkedList<LinkedList<String>> blocks = generateBlocks(fileText);
        blocks.remove(0);
        LinkedList<Block> finalBlocks = new LinkedList<>();
        for(LinkedList<String> block: blocks){
            finalBlocks.add(new Block(block));
        }
        System.out.println("[Blocking: "+dtf.format(LocalDateTime.now())+"] Done...");

        System.out.println("[Converting: "+dtf.format(LocalDateTime.now())+"] Starting...");
        StringBuilder html = new StringBuilder("<html>");
        StringBuilder head = new StringBuilder("<head>");
        StringBuilder body = new StringBuilder("<body>");
        for(Block block: finalBlocks){
            if(block.inHead) {
                head.append(block.generateTag());
            } else{
                body.append(block.generateTag());
            }
        }
        head.append("</head>");
        body.append("</body>");
        html.append(head).append(body).append("</html>");
        System.out.println("[Converting: "+dtf.format(LocalDateTime.now())+"] Done...");

        System.out.println("[Writing: "+dtf.format(LocalDateTime.now())+"] Starting...");
        writeFileContent("btml-index.html", html.toString());
        System.out.println("[Writing: "+dtf.format(LocalDateTime.now())+"] Done...");
    }

    // Reading the file.
    private static LinkedList<String> getFileContent(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        LinkedList<String> output = new LinkedList<>();

        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            output.add(scanner.nextLine());
        }

        scanner.close();
        return output;
    }

    private static void writeFileContent(String fileName, String html) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(html);
        fileWriter.close();
    }
}
