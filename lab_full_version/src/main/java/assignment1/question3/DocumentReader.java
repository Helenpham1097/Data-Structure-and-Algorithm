package assignment1.question3;

import java.io.*;
import java.util.Locale;

public class DocumentReader {

    /** INSTRUCTION TO READ FILE (doc.txt file was created in package question 3 if you want to use
     * Add this doc.txt file path to the argument or
     *   Eg. File file = new File("/Users/phamtrang/Downloads/Grad_Semester2/COMP610_DSA/LabExercises/src/main/java/assignment1/question3/doc.txt");
     *   OR
     * java -classpath folder_contains_compile_packages assignment1.question3.DocumentReader path_to_file
     *   Eg. java -classpath target/classes assignment1.question3.DocumentReader /Users/phamtrang/Downloads/Grad_Semester2/COMP610_DSA/LabExercises/src/main/java/assignment1/question3/doc.txt
     */

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(args[0]);

        SelfOrganizingArrayList<String> document = new SelfOrganizingArrayList<>();
        try (BufferedReader br
                = new BufferedReader(new FileReader(file))) {
            String st;
            while ((st = br.readLine()) != null) {
                String [] words =  st.split(" ");
                for(String word: words) {
                    String lowerCase =  word.toLowerCase(Locale.ROOT);
                    if(!document.contains(lowerCase)) {
                        document.add(lowerCase);
                    }
                }
            }

            System.out.println(document);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
