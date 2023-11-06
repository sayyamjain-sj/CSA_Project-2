import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class addStruct
{
    // instance variables - replace the example below with your own
    private String Command;
    public static int locVal = 0;
    public String endVal = "";
    public static HashMap<String,String > result = new HashMap<String,String>();
    private  ArrayList<String> val;
    // Constructor for objects of class StringStruct

    public addStruct(String Command, ArrayList<String> val)
    {
        System.out.println(result);
        this.Command = Command;
        this.val = val;
        String opCode = "";
        String[] str = new String[5];
        switch(Command) {

            case "LOC":
                locVal = Integer.parseInt(val.get(0));
                endVal = String.format("%04X",locVal);
                break;
            case "LDX":
                // code block
                opCode = "100001";
                str = val.get(0).split(",");
                if(str.length == 2)
                    opCode = opCode + "00" + toBin(str[0], 2) + "0" + toBin(str[1], 5);
                else if(str.length == 3)
                    opCode = opCode + "00" +  toBin(str[0], 2) + toBin(str[2], 1) + toBin(str[1], 5);
                result.put(String.format("%04X",locVal++),binaryToHex(opCode));
                break;
            case "LDR":
                opCode = "000001";

                str = val.get(0).split(",");
                if(str.length == 3)
                    opCode = opCode  + toBin(str[0], 2) + toBin(str[1], 2) + "0" + toBin(str[2], 5);
                else if(str.length == 4)
                    opCode = opCode  + toBin(str[0], 2) + toBin(str[1], 2) + toBin(str[3], 1) + toBin(str[2], 5);
                result.put(String.format("%04X",locVal++),binaryToHex(opCode));
                break;
            case "HLT":
                result.put(String.format("%04X",locVal), String.format("0000"));
                result.put(endVal, String.format("%04X",locVal));
                String filePath = "/Users/krishna009/Downloads/BasicMachineSimulator-dev 2/output.txt";
                TreeMap<String, String> sortedMap = new TreeMap<>(result);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    // Iterate through the HashMap and write key-value pairs to the file
                    for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                        writer.write(entry.getKey() + " "+ entry.getValue());
                        writer.newLine(); // Add a newline for each entry
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "JZ":
                str = val.get(0).split(",");
                opCode = "001000";
                if(str.length == 3)
                    opCode = opCode   + toBin(str[0], 2) + toBin(str[1], 2) + "0" + toBin(str[2], 5);
                else if(str.length == 4)
                    opCode = opCode  + toBin(str[0], 2) + toBin(str[1], 2) + toBin(str[3], 1) + toBin(str[2], 5);
                result.put(String.format("%04X",locVal++),binaryToHex(opCode));
                break;
            case "LDA":
                str = val.get(0).split(",");
                opCode  = "000011";
                if(str.length == 3)
                    opCode = opCode + toBin(str[0], 2) + toBin(str[1], 2) + "0" + toBin(str[2], 5);
                else if(str.length == 4)
                    opCode = opCode + toBin(str[0], 2) + toBin(str[1], 2) + toBin(str[3], 1) + toBin(str[2], 5);
                result.put(String.format("%04X",locVal++),binaryToHex(opCode));
                break;
                // code block
            case "Data":
                if (val.get(0).equals( "End")) {
                    result.put(String.format("%04x",locVal++), String.format("0400"));
                }
                else{
                    result.put(String.format("%04x",locVal++), String.format("%04X",Integer.parseInt(String.valueOf(val.get(0)))));
                }

                break;
        }
    }
    public static String toBin(String num, int length){
        String bin =  Integer.toBinaryString(Integer.parseInt(num));
        while(bin.length() < length){
            bin = "0" + bin;
        }
        return bin;
    }
    public static String binaryToHex(String binary){
        int val = binaryToDec(binaryStringToBinary(binary));
        return decimalToHex(val);

    }
    public static boolean[] binaryStringToBinary(String s){
        boolean[] bin = new boolean[s.length()];
        for(int i = 0; i<bin.length; i++){
            bin[i] = ('1' == s.charAt(i));
        }
        return bin;
    }

    public static String decimalToHex(int val){
        String hex = Integer.toHexString(val).toUpperCase();
        while(hex.length() < 4){
            hex = "0" + hex;
        }
        return hex;
    }

    public static int binaryToDec(boolean[] bin){
        int dec = 0;
        int multiplier = 1;
        for(int i = bin.length-1; i>=0; i--){
            dec += multiplier * (bin[i] ? 1 : 0);
            multiplier *=2;
        }
        return dec;
    }
}
