package com.Rik.btml;

import java.util.HashMap;
import java.util.LinkedList;

public class macros {
    public static HashMap<String, String> macros = new HashMap<>();

    public static LinkedList<Integer> indexToRemove = new LinkedList<>();
    public static LinkedList<String> replaceAllWithMacroValue(LinkedList<String> fileText){
        // Replacing text with macro value
        @SuppressWarnings("unchecked")
        LinkedList<String> output = (LinkedList<String>) fileText.clone();
        int lineCounter = 0;
        for(String line: output){
            if(line.startsWith("#define")){
                String[] splitLine = line.split(" ", 3);
                macros.put(splitLine[1], splitLine[2]);
                indexToRemove.add(lineCounter);
            }else{
                for(String macroName: macros.keySet()){
                    output.set(lineCounter, output.get(lineCounter).replace(macroName, macros.get(macroName)));
                }
            }
            lineCounter++;
        }

        indexToRemove.sort(null);
        for(int i = indexToRemove.size() - 1; i >= 0; i--){
            output.remove(output.get(indexToRemove.get(i)));
        }

        return output;
    }
}
