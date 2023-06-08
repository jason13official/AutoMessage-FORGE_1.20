package net.ggwpgaming.automessage.util;

import java.util.ArrayList;

public class ValuesNode {
    public String UUID;
    public ArrayList<Integer> values;

    public ValuesNode(String UUID, ArrayList<Integer> values) {
        this.UUID = UUID;
        this.values = values;
    }
}
