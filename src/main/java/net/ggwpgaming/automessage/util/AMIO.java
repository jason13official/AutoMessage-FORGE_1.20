package net.ggwpgaming.automessage.util;

import com.mojang.logging.LogUtils;
import net.ggwpgaming.automessage.AutoMessage;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AMIO {
    public static final String AMLogPrefix = "[AUTOMESSAGE LOG] ";
    public static final String AMClientLogName = "automessage-CLIENT_VIEWCOUNT_LOG.txt";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void main(String[] args) {
        LOGGER.debug("You ran AMIO as a standalone program!");
//        LinkedList<ValuesNode> linkedList = new LinkedList<>();
//
//        ArrayList<Integer> initValues = new ArrayList<>();
//
//        initValues.add(0, 0);
//        initValues.add(1, 1);
//        initValues.add(2, 2);
//
//        ValuesNode node1 = new ValuesNode("John", initValues);
//        ValuesNode node2 = new ValuesNode("Alice", initValues);
//        ValuesNode node3 = new ValuesNode("Bob", initValues);
//
//        linkedList.add(node1);
//        linkedList.add(node2);
//        linkedList.add(node3);
//
//        writeLinkedListToFile(linkedList, AMClientLogName);
//
//        System.out.println(AMLogPrefix + "Linked List written to file successfully.");
//
//        LinkedList<ValuesNode> retrievedList = readLinkedListFromFile(AMClientLogName);
//
//        System.out.println(AMLogPrefix + "Retrieved Linked List:");
//        for (ValuesNode node : retrievedList) {
//            System.out.println(AMLogPrefix + "UUID: " + node.UUID + ", Value: " + node.values);
//        }
    }

    public static void writeLinkedListToFile(LinkedList<ValuesNode> linkedList, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (ValuesNode node : linkedList) {
                writer.write(node.UUID + "," + node.values + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<ValuesNode> readLinkedListFromFile(String fileName) {
        LinkedList<ValuesNode> linkedList = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] parts = line.split(",", 2);

                    if (parts.length >= 2) {
                        String name = parts[0];
                        String valuesString = parts[1].trim().substring(1, parts[1].length() - 1);

                        String[] parts2 = valuesString.split(",");
                        ArrayList<Integer> values = new ArrayList<>();
                        for (String part : parts2) {
                            part = part.trim();
                            if (!part.isEmpty()) {
                                values.add(Integer.valueOf(part));
                            }
                        }

                        linkedList.add(new ValuesNode(name, values));
                    } else {
                        AutoMessage.println("Invalid line format: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return linkedList;
    }

//    static class ValuesNode {
//        String UUID;
//        ArrayList<Integer> values;
//
//        ValuesNode(String UUID, ArrayList<Integer> values) {
//            this.UUID = UUID;
//            this.values = values;
//        }
//    }

}


//package net.ggwpgaming.automessage.util;
//
//import com.mojang.logging.LogUtils;
//import org.slf4j.Logger;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.LinkedList;
//
//import static java.lang.String.valueOf;
//
//public class ValuesIO {
//    public static final String AMLogPrefix = "[AUTOMESSAGE LOG] ";
//    public static final String AMLogFileName = "automessage-HARD_LIMIT_LOG.txt";
//    private static final Logger LOGGER = LogUtils.getLogger();
//    public static void main(String[] args) {
//        LinkedList<ValuesNode> linkedList = new LinkedList<>();
//
//        ArrayList<Integer> initValues = new ArrayList<Integer>();
//
//        initValues.add(0, 0);
//        initValues.add(1,1);
//        initValues.add(2,2);
//
//        ValuesNode node1 = new ValuesNode("John", initValues);
//        ValuesNode node2 = new ValuesNode("Alice", initValues);
//        ValuesNode node3 = new ValuesNode("Bob", initValues);
//
//        linkedList.add(node1);
//        linkedList.add(node2);
//        linkedList.add(node3);
//
////        writeLinkedListToFile(linkedList, AMLogFileName);
//
////        System.out.println(AMLogPrefix +"Linked List written to file successfully.");
//
//        LinkedList<ValuesNode> retrievedList = readLinkedListFromFile(AMLogFileName);
//
//
//        System.out.println(AMLogPrefix +"Retrieved Linked List:");
//        for (ValuesNode node : retrievedList) {
//            System.out.println(AMLogPrefix +"UUID: " + node.UUID + ", Value: " + node.values);
//        }
//    }
//
//    public static void writeLinkedListToFile(LinkedList<ValuesNode> linkedList, String fileName) {
//        try (FileWriter writer = new FileWriter(fileName)) {
//            for (ValuesNode node : linkedList) {
////                writer.write("Name: " + node.name + ", Value: " + node.value + "\n");
//                writer.write(node.UUID + "," + node.values + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static LinkedList<ValuesNode> readLinkedListFromFile(String fileName) {
//        LinkedList<ValuesNode> linkedList = new LinkedList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (!line.isEmpty()) {
////                    System.out.println(logPrefix + "Line being read: "+line);
//
//                    // Parse the line to retrieve name and value
//                    String[] parts = line.split(",", 2);
////                    System.out.println("Line as array: "+ Arrays.toString(parts));
//
//                    if (parts.length >= 2) {
//
//                        String name = parts[0];
////                        System.out.println(name);
////                        String name = parts[0].substring(0, parts[1].length()-1);
//
//
//                        for (int i=0; i<parts.length; i++)
//                        {
//                            System.out.println(parts[i]);
//                            System.out.println("Printed!");
//                            if (i == 1)
//                            {
//                                parts[i] = parts[i].substring(1,1);
//                            }
//                            else if (i == parts.length)
//                            {
//                                parts[i] = parts[i].substring(i,parts.length-1);
//                            }
//                        }
//
//                        String valuesString = parts[1];
//                        System.out.println(AMLogPrefix +"VALUES "+ valuesString);
//
//                        String[] parts2 = valuesString.split(",");
////                        System.out.println(parts2);
//                        ArrayList<Integer> values = new ArrayList<Integer>();
//                        for (int i=0; i< parts2.length;i++) {
//                            if (parts2[i] != "")
//                                values.add(i, Integer.valueOf(parts2[i]));
//                        }
////                        System.out.println(values);
//
////                        System.out.println(logPrefix+"String Name: "+name);
////                        System.out.println(logPrefix+"String Value: "+String.valueOf(value));
//                        linkedList.add(new ValuesNode(name, values));
//                    } else {
//                        // Handle invalid line format
//                        System.out.println(AMLogPrefix +"Invalid line format: " + line);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return linkedList;
//    }
//
//}
