
import java.util.*;
import java.math.*;
import java.util.PriorityQueue;
import java.util.Comparator;

class HuffmanNode {
    int value;
    char c;
    HuffmanNode left;
    HuffmanNode right;
}

// For comparing the nodes
class ImplementComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.value - y.value;
    }
}

public class Main {

    static Map<String, Character> m;
    //          code,  character

    static void buildTree(HuffmanNode node,String curr){
        if(node.c == '-'){
            if(node.left != null)
                buildTree((node.left),curr+"0");
            if(node.right != null)
                buildTree((node.right),curr+"1");
        }else{
            m.put(curr, node.c);
        }
    }

    static Map<Character, Integer> preprocessing(String text_before){
        Map<Character, Integer> frequency= new HashMap();
        for (char x: text_before.toCharArray()) {
            if(frequency.get(x) != null)
                frequency.put(x, frequency.get(x) + 1);
            else frequency.put(x ,1);
        }
        return frequency;
    }

    static void encode (String text){
        //             000011001001011011111
        Map<Character, Integer> frequency = preprocessing(text);
        //  character, frequency

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>(frequency.size(), new ImplementComparator());

        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            HuffmanNode hn = new HuffmanNode();

            Character key = entry.getKey();
            Integer value = entry.getValue();

            hn.c = key;
            hn.value = value;

            hn.left = null;
            hn.right = null;

            pq.add(hn);
        }
        HuffmanNode root = null;
        while(pq.size() > 1) {
            HuffmanNode x,y;
            x = pq.peek();
            pq.poll();
            y = pq.peek();
            pq.poll();

            HuffmanNode node = new HuffmanNode();

            node.left = x;
            node.right = y;
            node.value = x.value+y.value;
            node.c = '-';

            root = node;
            pq.add(node);
        }

        buildTree(root,"");
        for (Map.Entry<String, Character> entry : m.entrySet()) {
            String key = entry.getKey();
            Character value = entry.getValue();
            System.out.println(value+"  "+key);
        }
    }

    static String decode(String encoded){
        int max_val = 0;
        StringBuilder output= new StringBuilder();
        for (int value: m.values()) {
            max_val  = Math.max(max_val,value);
        }
        String curr = "";
        for (int i = 0; i <encoded.length() ; i++) {
            curr = curr + encoded.charAt(i);
            if(m.get(curr) != null){
                output.append(m.get(curr));
                curr = "";
            }
        }

        return output.toString();
    }

    public static void main(String[] args) {
        m = new HashMap();

        String text = "AAAABAACDDBBB";
        System.out.println("Encode:");
        encode(text);
        System.out.println("\nDecode:");
        String encoded = "000011001001011011111";
        System.out.println(decode(encoded));

    }
}