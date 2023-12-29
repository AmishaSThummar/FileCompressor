import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Encode{

    public static void huffEncode(String inpuString, boolean showOutput){

        Huffman ht = new Huffman(inpuString, showOutput);

        if (showOutput){
			System.out.println("\n============= Word Frequency =============");
			for (Map.Entry<Character, Integer> entry: ht.hmapWC.entrySet()){
				String key = entry.getKey().toString();
				int val = entry.getValue();
				if (key.equals("\n"))
					key = "\\n";
				System.out.println(key + " Freq:  " + val);
			}
			
			System.out.println("\n========== Huffman Code for each character =============");
			for (Map.Entry<Character, String> entry: ht.hmapCode.entrySet()){
				String key = entry.getKey().toString();
				String val = entry.getValue();
				if (key.equals("\n"))
					key = "\\n";
				System.out.println(key + ": " + val); 
			}
			System.out.println();
		}
	    
	    System.out.print("* Encoding the text...");
	    String e = ht.encode();

		// save encoded output to zip file 
		try(FileOutputStream fos = new FileOutputStream("compress.zip");
			ZipOutputStream zos = new ZipOutputStream(fos)){

				ZipEntry entry = new ZipEntry("exp.txt");
				zos.putNextEntry(entry);
				byte[] data = e.getBytes();
				zos.write(data, 0, data.length);
				zos.closeEntry();

		}catch(IOException ex){
			ex.printStackTrace();
		}

		String outputString = ht.decode();
		myassert(inpuString.equals(outputString)) ;   // Check if original text and decoded text is exactly same
		try{
			Files.writeString(Paths.get("outputFile.txt"), outputString, StandardCharsets.UTF_8);
		}
		catch(IOException ex){

		}

	    System.out.println(" DONE");
	    
	    
	    double sl = inpuString.length() * 7 ;
	    double el = e.length();
	    System.out.println("\n========== RESULT ==========");
	    System.out.println("Original string cost = " + (int)sl + " bits") ;
	    System.out.println("Encoded  string cost = " + (int)el + " bits") ;
	    double r = ((el - sl)/sl) * 100 ;
	    System.out.println("% reduction = " + (-r)) ;
    }

    public static String readFile(String fname){

        // file content as string
        StringBuilder strbuBuilder = new StringBuilder();

        File filename = new File(fname);
        
        // read the input file
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String line = in.readLine();
			while (line != null){
				strbuBuilder.append(line + "\n");
				line = in.readLine();
			}
        }
        catch (IOException e){
			System.out.println(e);
		}
        return strbuBuilder.toString();
    }   

    public static void compress(){

        String inputFile = "inputFile.txt";
        String inputString = readFile(inputFile);
        boolean showOutput = true;
        huffEncode(inputString, showOutput);
    }


    public static void main(String[] args){
        System.out.println(".............Start Compression..............");
        compress();
    }

	public static void myassert(boolean  x) {
	    if (!x) {
	    	throw new IllegalArgumentException("Assert fail") ;
	    }
    }
    
}