package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class GraphFileParser which helps to parse a file to build a
 * <code>Graph</code>
 */
class GraphFileParser {

    private static final String REGEX =
            "(\\w+|\\w\\s)*\\s--\\w+(\\[((((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+)),)*)((\\w+=(\\[((\\w+)|\\|)+\\]|\\w+))))\\]|)-->\\s(\\w+|\\w\\s)*";
    private Pattern pattern;
    private FileReader fileReader;
    private BufferedReader reader;

    /**
     * The default constructor
     *
     * @param file the file to read
     * @throws FileNotFoundException
     */
    public GraphFileParser(File file) throws FileNotFoundException {
        fileReader = new FileReader(file);
        reader = new BufferedReader(fileReader);
        pattern = Pattern.compile(REGEX);
    }

    /**
     * Reads the next char data of the file
     *
     * @return an instance of <code>String</code> or null
     * @throws GraphFileParserException
     * @throws IOException
     */
    public String readNextLine() throws GraphFileParserException, IOException {
        String line = reader.readLine();
        if (line != null && !isLineValid(line)) {
            throw new GraphFileParserException("This line does not match the regex:\n" + line);
        } else {
            return line;
        }

    }

    /**
     * Close the parser
     *
     * @throws IOException
     */
    public void close() throws IOException {
        reader.close();
        fileReader.close();
    }

    /**
     * Checks if an instance of
     * <code>String</code> matches the regex
     *
     * @param line the line to check
     * @return true if the line matches the regex
     */
    private boolean isLineValid(String line) {
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }
}
