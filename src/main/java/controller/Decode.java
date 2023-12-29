package controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Decode {

    public static void deCompress(){

        String filePath = "E:/Sem8/File Compressor/src/main/resources/hashMapRFile.ser";
        HashMap<String,Character> hashMapR = readHashMapFromFile(filePath);

        // for(Map.Entry<String,Character> entry:hashMapR.entrySet()){
        // 	String key = entry.getKey();
        // 	Character value = entry.getValue();

        // 	System.out.println(key + ":" + value+"\n");
        // }

        System.out.print("* Decoding the encoded text...");

        String zipFilePath = "E:/Sem8/File Compressor/src/main/resources/compress.zip";
        String entryName = "E:/Sem8/File Compressor/src/main/resources/exp.txt";

        try(ZipFile zipFile = new ZipFile(zipFilePath)){
            ZipEntry entry = zipFile.getEntry(entryName);

            if(entry != null){
                try(InputStream inputStream = zipFile.getInputStream(entry);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while((bytesRead = inputStream.read(buffer)) != -1){
                        baos.write(buffer, 0, bytesRead);
                    }

                    String extractedString = baos.toString("UTF-8");

                    Huffman ht = new Huffman(extractedString, true);
                    ht.hmapCodeR = hashMapR;
                    String outpuString = ht.decode();
                    String outputFilePath = "E:/Sem8/File Compressor/src/main/resources/outputFile.txt";
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))){
//                        Files.writeString(Paths.get("outputFile.txt"), outpuString, StandardCharsets.UTF_8);
                        writer.write((outpuString));
                    }
                    catch(IOException ex){

                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String, Character> readHashMapFromFile(String filePath){

        try(FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){

            return (HashMap<String, Character>) objectInputStream.readObject();
        }
        catch(IOException|ClassNotFoundException e){
            e.printStackTrace();;
            return null;
        }
    }

    public static void main(String[] args){
        deCompress();
        System.out.println("Decompressed successfully");
    }

}
