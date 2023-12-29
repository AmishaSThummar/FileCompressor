package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    private int counter;  //unique id assigned to each integer
    private int totalNodes;
    private String inputString, encodedStr, decodedStr;;
    public HashMap<Character, Integer> hmapWC;  // for occurrence count
    public HashMap<Character, String> hmapCode; // for code(character/code)
    public HashMap<String, Character> hmapCodeR; // for code(code/character)
    private PriorityQueue<Node> pq;  // for MinHeap
    private Node root;   //root of the tree

    public Huffman(String inputString, boolean showOutput){

        this.counter = 0;
        this.totalNodes = 0;
        this.inputString = inputString;
        hmapWC = new HashMap<Character, Integer>();
        hmapCode = new HashMap<Character, String>();
        hmapCodeR = new HashMap<String, Character>();

        pq = new PriorityQueue<Node>(1,new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if (n1.freq < n2.freq)
                    return -1;
                else if (n1.freq > n2.freq)
                    return 1;
                return 0;
            }
        });

        countWord();  // STEP 1: Count frequency of word
        buildTree();  // STEP 2: Build Huffman Tree
        buildCodeTable();  // STEP 4: Build Huffman Code Table
    }

    // Inner class
    private class Node{
        int uid, freq;
        char ch;
        Node left, right;

        // Constructor for class Node
        private Node(Character ch, Integer freq, Node left, Node right){
            uid = ++counter;
            this.freq = freq;  //frequency of that character
            this.ch = ch;
            this.left = left;
            this.right = right;
        }
    }

    private void countWord(){
        Character ch;
        Integer freq;

        for (int i=0; i<inputString.length(); i++){
            ch = new Character(inputString.charAt(i));
            if (hmapWC.containsKey(ch) == false)
                freq = new Integer(1);
            else
                freq = hmapWC.get(ch) + 1;
            hmapWC.put(ch, freq);
        }
    }

    private void buildTree(){
        buildMinHeap();  // Set all leaf nodes into MinHeap
        Node left, right;
        while (! pq.isEmpty()){
            left = pq.poll();
            totalNodes++;
            if (pq.peek() != null){
                right = pq.poll();
                totalNodes++;
                root = new Node('\0', left.freq + right.freq, left, right);
            }
            else{  // only left child. right=null
                root = new Node('\0', left.freq, left, null);
            }

            if (pq.peek() != null){
                pq.offer(root);
            }
            else{  // = Top root. Finished building the tree.
                totalNodes++;
                break;
            }
        }
    }

    private void buildMinHeap(){
        for (Map.Entry<Character, Integer> entry: hmapWC.entrySet()){
            Character ch = entry.getKey();
            Integer freq = entry.getValue();
            Node n = new Node(ch, freq, null, null);
            pq.offer(n);
        }
    }

    private void buildCodeTable(){
        String code = "";
        Node n = root;
        buildCodeRecursion(n, code);  // Recursion
    }

    private void buildCodeRecursion(Node n, String code){
        if (n != null){
            if (! isLeaf(n)){  // n = internal node
                buildCodeRecursion(n.left, code + '0');
                buildCodeRecursion(n.right, code + '1');
            }
            else{  // n = Leaf node
                hmapCode.put(n.ch, code); // for {character:code}
                hmapCodeR.put(code, n.ch); // for {code:character}
            }
        }
    }

    private boolean isLeaf(Node n) {
        return (n.left == null) && (n.right == null);
    }

    public String encode(){
        StringBuilder sb = new StringBuilder();
        Character ch;
        for(int i=0; i<inputString.length(); i++){
            ch = inputString.charAt(i);
            sb.append(hmapCode.get(ch));
        }
        encodedStr = sb.toString();
        String hmapFile = "E:/Sem8/File Compressor/src/main/resources/hashMapRFile.ser";
        storeHashMapToFile(hmapCodeR, hmapFile);

        return encodedStr;
    }

    private static void storeHashMapToFile(HashMap<String,Character> hashMap, String file){

        try(FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(hashMap);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public String decode(){
        StringBuilder sb = new StringBuilder();
        String t = "";

        for(int i=0; i<inputString.length(); i++){
            t += inputString.charAt(i);
            if (hmapCodeR.containsKey(t)){
                sb.append(hmapCodeR.get(t));
                t = "";
            }
        }
        decodedStr = sb.toString();
        return decodedStr;
    }
}
