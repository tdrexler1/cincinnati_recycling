import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CIN_data_sample {

    public static void main(String args[]){

        String inputFileName = args[0];
        Float pctSample = Float.parseFloat(args[1]);

        HashMap<Integer, String> lineStorage = new HashMap<>();
        //"Cp1252"
        try(BufferedReader scan = new BufferedReader( new InputStreamReader( new FileInputStream(inputFileName), "UTF-8" ) ) ){
            
            lineStorage.put(0, scan.readLine()); // header
            int currentLine = 1;

            while(scan.readLine() != null){
                String line = scan.readLine();
                if( (int)(Math.random()*(100/pctSample) ) == 0){ 
                    lineStorage.put(currentLine, line);
                    currentLine += 1;
                } 
            }
            System.out.println(lineStorage.size());
        } catch(IOException e){
            System.out.println("File " + inputFileName + " could not be located.");
        }

        String outputFileName = inputFileName.substring(0, inputFileName.length() - 4) + "_sample_" + pctSample + ".csv";

        try(BufferedWriter outputFile = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8) ) ){
            for(int ii = 0; ii< lineStorage.size(); ii++){
                outputFile.write(lineStorage.get(ii) + "\n");
            }
            outputFile.close();
        } catch(IOException e){
            System.out.println("File " + outputFileName + " could not be written.");
        }
    }
}