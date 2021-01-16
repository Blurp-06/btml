package com.Rik.btml;

import java.util.LinkedList;

public class GetBlocks {
    public static LinkedList<LinkedList<String>> generateBlocks(LinkedList<String> fileText){
        LinkedList<LinkedList<String>> blocks = new LinkedList<>();
        LinkedList<String> block = new LinkedList<>();
        for(String line : fileText){
            if (line.startsWith("!")) {
                @SuppressWarnings("unchecked")
                LinkedList<String> toAdd = (LinkedList<String>) block.clone();
                blocks.add(toAdd);
                block.clear();
                block.add(line);
            } else if (line.startsWith(">") && !block.isEmpty()) {
                block.add(line);
            }
        }
        @SuppressWarnings("unchecked")
        LinkedList<String> toAdd = (LinkedList<String>) block.clone();
        blocks.add(toAdd);
        block.clear();
        return blocks;
    }
}
