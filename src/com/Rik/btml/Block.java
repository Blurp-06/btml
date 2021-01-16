package com.Rik.btml;

import java.util.HashMap;
import java.util.LinkedList;

public class Block {

    private String inner = "";
    private boolean selfClosed = false;
    public boolean inHead = false;
    private final HashMap<String, String> attributes = new HashMap<>();
    private String tagName;

    public Block(LinkedList<String> block){
        for(String line: block){
            if(line.startsWith("!")){
                tagName = line.replace("!", "");
                continue;
            } else if(line.equals(">closed")){
                selfClosed = true;
                continue;
            } else if(line.startsWith(">inner")){
                inner = line.split(" ", 2)[1];
                continue;
            }else if(line.equals(">head")) {
                inHead = true;
                continue;
            }
            String[] splitLine = line.split(" ", 2);
            attributes.put(splitLine[0].replace(">", ""), splitLine[1]);
        }
    }

    public String generateTag(){
        StringBuilder output = new StringBuilder();
        StringBuilder attributeString = new StringBuilder();

        for(String key: attributes.keySet()){
            attributeString.append(" ").append(key).append("=\"").append(attributes.get(key)).append("\"");
        }

        if(selfClosed){
            output.append("<").append(tagName).append(attributeString).append("/>");
        } else{
            output.append("<").append(tagName).append(attributeString).append(">").append(inner).append("</").append(tagName).append(">");
        }
        return output.toString();
    }
}
