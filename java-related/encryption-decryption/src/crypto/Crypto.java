package crypto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Crypto {
    public static void main(String[] args) throws Exception {
        String mode = "enc";
        String data = "";
        String dataFromFile = "";
        String outFileName = "";
        String input = "";
        String algType = "shift";
        int key = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in": {
                    String fileName = args[i + 1];
                    dataFromFile = new String(Files.readAllBytes(Paths.get(fileName)));
                }
                break;
                case "-out":
                    outFileName = args[i + 1];
                    break;
                case "-alg":
                    algType = args[i + 1];
                    break;
                default:
            }
        }

        input = data;

        // If there is no -data, and there is no -in the program
        // should assume that the data is an empty string.
        if (data.equals("") && dataFromFile.equals("")) {
            input = "";
        }

        // If there are both -data and -in arguments,
        // your program should prefer -data over -in.
        if (!data.equals("") && !dataFromFile.equals("")) {
            input = data;
        }

        if (data.equals("") && !dataFromFile.equals("")) {
            input = dataFromFile;
        }


        char[] str = input.toCharArray();

        String rs = "";

        switch (mode) {
            case "enc":
                rs = encode(str, key, algType);
                break;
            case "dec":
                rs = decode(str, key, algType);
                break;
            default:
        }

        // If there is no -out argument,
        // the program must print data to the standard output.
        if (!outFileName.equals("")) {
            File file = new File(outFileName);

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(rs);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println(rs);
        }
    }


    public static String encode(char[] str, int key, String algType) throws Exception {
        StringBuilder builder = new StringBuilder();
        Algorithm algorithm = AlgorithmFactory.make(algType);

        if (algorithm == null) {
            throw new Exception("Error: no algorithm");
        }

        for (Character c : str) {
            builder.append(algorithm.encode(c, key));
        }

        return builder.toString();
    }

    public static String decode(char[] str, int key, String algType) throws Exception {
        StringBuilder builder = new StringBuilder();
        Algorithm algorithm = AlgorithmFactory.make(algType);

        if (algorithm == null) {
            throw new Exception("Error: no algorithm");
        }

        for (Character c : str) {
            builder.append(algorithm.decode(c, key));
        }
        return builder.toString();
    }
}

interface Algorithm {
    char encode(char c, int key);

    char decode(char c, int key);
}

class ShiftAlg implements Algorithm {

    @Override
    public char encode(char c, int key) {
        if (c >= 'A' && c <= 'Z') {
            c = (char) (c + key);
            if (c > 'Z') {
                c = (char) (c - 26);
            }
        }

        if (c >= 'a' && c <= 'z') {
            c = (char) (c + key);
            if (c > 'z') {
                c = (char) (c - 26);
            }
        }

        return c;
    }


    @Override
    public char decode(char c, int key) {
        if (c >= 'A' && c <= 'Z') {
            c = (char) (c - key);
            if (c < 'A') {
                c = (char) (c + 26);
            }
        }

        if (c >= 'a' && c <= 'z') {
            c = (char) (c - key);
            if (c < 'a') {
                c = (char) (c + 26);
            }
        }

        return c;
    }
}

class UnicodeAlg implements Algorithm {

    @Override
    public char encode(char c, int key) {
        return (char) (c + key);
    }

    @Override
    public char decode(char c, int key) {
        return (char) (c - key);
    }
}

class AlgorithmFactory {
    public static Algorithm make(String type) {
        switch (type) {
            case "shift":
                return new ShiftAlg();
            case "unicode":
                return new UnicodeAlg();
            default:
                return null;
        }
    }
}
