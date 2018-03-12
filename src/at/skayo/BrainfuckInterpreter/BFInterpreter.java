package at.skayo.BrainfuckInterpreter;

import java.io.IOException;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import java.nio.file.Paths;
import java.nio.file.Files;


public class BFInterpreter {

    private String bfcode;
    private List<Integer> targets = new ArrayList<Integer>();

    private int codePtr;
    private int memPtr;

    private int memsize = 3000;
    private int cellsize = 255;

    private int[] memory = new int[memsize];

    public static void main(String[] args) {
        String helloworld = "++++++++++\n" +
                " [\n" +
                "  >+++++++>++++++++++>+++>+<<<<-\n" +
                " ]                       Schleife zur Vorbereitung der Textausgabe\n" +
                " >++.                    Ausgabe von 'H'\n" +
                " >+.                     Ausgabe von 'e'\n" +
                " +++++++.                'l'\n" +
                " .                       'l'\n" +
                " +++.                    'o'\n" +
                " >++.                    Leerzeichen\n" +
                " <<+++++++++++++++.      'W'\n" +
                " >.                      'o'\n" +
                " +++.                    'r'\n" +
                " ------.                 'l'\n" +
                " --------.               'd'\n" +
                " >+.                     '!'\n" +
                " >.                      Zeilenvorschub\n" +
                " +++.                    Wagenrücklauf";

        String num1to7 = "// >+++++++ Brainfuck Beispielprogramm \"von 1 bis 7\" [ :-) <3 :+) :> :> ) +++++++<\n" +
                "Gib die Zahl Eins aus! // (gibt 1 aus]\n" +
                "Gib die Zahl Zwei aus! // (gibt 2 aus)\n" +
                "Gib die Zahl Drei aus! // (gibt 3 aus)\n" +
                "Gib die Zahl Vier aus! // (gibt 4 aus)\n" +
                "Gib die Zahl Fünf aus! // (gibt 5 aus)\n" +
                "Gib die Zahl Sechs aus! // (gibt 6 aus)\n" +
                "Gib die Zahl Sieben aus! // (gibt 7 aus)\n" +
                "// <3 [ :->> Das war dann auch schon das Programm. + Kommentare mit Smilies !!! <<o: ]";

        String wiki = ">++++++++[<++++++++ >-]<++.>++++++[<+++++++ >-]<++++++.>++++[<---- >-]<-.>++[<+++ >-]<++.+++++.>++[<--- >-]<--.>+++[<++++ >-]<+++.>++++[<---- >-]<--.>++[<+++ >-]<++.>++++++++[<--------- >-]<---.>++++++++[<+++++++++ >-]<+.>+++[<+++ >-]<+.+.>+++++++++[<--------- >-]<---.>++++++++[<++++++++ >-]<+++++.++++.+++++.>+++[<--- >-]<.>++++++++[<-------- >-]<-----.>+++++++++[<+++++++++ >-]<++.----.>++[<--- >-]<--.--.>+++[<+++ >-]<.>+++[<---- >-]<-.>+++[<++++ >-]<+..++++++.>+++[<---- >-]<---.>++++++++[<-------- >-]<-----.>++++++++[<++++++++ >-]<+++++.>+++[<++++ >-]<++.----.+++++.>+++[<---- >-]<---.>+++[<++++ >-]<+.>+++[<--- >-]<.>+++[<+++ >-]<+.>++++[<---- >-]<.+++++.---.>++++++++[<-------- >-]<-----.>++++++[<+++++++ >-]<++++++.>+++++[<++++++ >-]<++++.---.>++[<--- >-]<--.>+++[<+++ >-]<++.>++++[<---- >-]<-.>+++[<++++ >-]<..----.----.>+++[<++++ >-]<+.+.---.++.>++++[<---- >-]<-.++.+++++.---.>+++++++[<-------- >-]<-.>+++[<---- >-]<.>++++++++[<++++++++ >-]<+++++.>+++[<+++ >-]<.++++++.+++.>++[<--- >-]<--.+++.>+++[<---- >-]<.-.>+++[<+++ >-]<.>++++++++[<--------- >-]<------.>+++++++++[<+++++++++ >-]<+++++.>++[<--- >-]<-.--.>++++++++[<--------- >-]<-----.>+++++++[<+++++++ >-]<++.>++++[<++++ >-]<.+++++.>+++[<++++ >-]<+++.>++++[<---- >-]<--.++++.>++++[<++++ >-]<+.>++++[<----- >-]<-.>+++[<++++ >-]<+.>+++++++++[<--------- >-]<-.>+++++++[<+++++++ >-]<++++.>+++++[<+++++ >-]<++++.>++++[<---- >-]<.-.>+++[<++++ >-]<+.>++++++++[<--------- >-]<------.>++++++[<+++++++ >-]<+++.>++++++[<++++++ >-]<++++.>++++[<---- >-]<.>++[<+++ >-]<+..>++[<--- >-]<-.>+++[<++++ >-]<+.>+++++++++[<--------- >-]<-.>+++++++++[<+++++++++ >-]<++++.>++[<--- >-]<--.>++++++++[<--------- >-]<-----.>++++[<++++ >-]<+.>++[<+++ >-]<++..------.-----.>+++[<---- >-]<--.>++++++[<++++++ >-]<.>++++++[<++++++ >-]<+.----.>++++++++[<-------- >-]<-----.>+++++++[<+++++++ >-]<++.>+++++[<+++++ >-]<++++.++.>++++[<---- >-]<-.++.+++++.---.>++++++++[<-------- >-]<-----.>+++++++++[<+++++++++ >-]<++++++.>+++[<---- >-]<--.>+++[<+++ >-]<.>+++[<---- >-]<--.>++++++++[<-------- >-]<----.>++++++++[<+++++++++ >-]<+++++.>+++[<---- >-]<.>+++[<++++ >-]<+.>+++[<--- >-]<--.+++++.+++++.>+++[<---- >-]<.>+++[<+++ >-]<++.>++++++++[<--------- >-]<----.>++++++++[<++++++++ >-]<+.>++++[<+++++ >-]<.>++++[<---- >-]<--.+++++.>++++++++[<--------- >-]<.>+++++[<++++++ >-]<++++.>++++++[<+++++++ >-]<++++++.>++++[<---- >-]<-.>++[<+++ >-]<++.+++++.>++[<--- >-]<--.>+++++++[<-------- >-]<----.>+++++++[<++++++++ >-]<+.>++[<+++ >-]<++.>+++++++[<-------- >-]<-------.>+++[<---- >-]<.>+++++[<++++++ >-]<++++.>++++++[<+++++++ >-]<++++++.>++++[<---- >-]<-.>++[<+++ >-]<++.+++++.>++[<--- >-]<--.>+++++++[<-------- >-]<----...>+++[<--- >-]<-.>++++++++[<+++++++++ >-]<+++++++.>+++[<--- >-]<--.+.>+++[<++++ >-]<+.>+++++++++[<--------- >-]<-.>+++++[<++++++ >-]<++++.++++.>++++++[<------ >-]<--.>++++++++[<++++++++ >-]<+++++++.--.>+++[<+++ >-]<.>+++[<---- >-]<-.>+++[<++++ >-]<+..++++++.>++++++++[<-------- >-]<------.>";

        String cellsize = "Calculate the value 256 and test if it's zero\n" +
                "If the interpreter errors on overflow this is where it'll happen\n" +
                "++++++++[>++++++++<-]>[<++++>-]\n" +
                "+<[>-<\n" +
                "    Not zero so multiply by 256 again to get 65536\n" +
                "    [>++++<-]>[<++++++++>-]<[>++++++++<-]\n" +
                "    +>[>\n" +
                "        # Print \"32\"\n" +
                "        ++++++++++[>+++++<-]>+.-.[-]<\n" +
                "    <[-]<->] <[>>\n" +
                "        # Print \"16\"\n" +
                "        +++++++[>+++++++<-]>.+++++.[-]<\n" +
                "<<-]] >[>\n" +
                "    # Print \"8\"\n" +
                "    ++++++++[>+++++++<-]>.[-]<\n" +
                "<-]<\n" +
                "# Print \" bit cells\\n\"\n" +
                "+++++++++++[>+++>+++++++++>+++++++++>+<<<<-]>-.>-.+++++++.+++++++++++.<.\n" +
                ">>.++.+++++++..<-.>>-\n" +
                "Clean up used cells.\n" +
                "[[-]<]";

        String fehler = "+++[>+";

        BFInterpreter bfInterpreter = new BFInterpreter();

        //bfInterpreter.readString(helloworld);
        bfInterpreter.readFile("GameOfLife.bf");
        bfInterpreter.run();
    }

    public void readFile(String file){
        if(Files.exists(Paths.get(file))) {
            List<String> strings;
            try {
                strings = Files.readAllLines(Paths.get(file));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            StringBuilder sb = new StringBuilder();
            for (String string : strings) {
                sb.append(string);
            }
            String code = sb.toString();
            bfcode = parse(code);
        } else {
            error("Error: File " + file + " not found.");
        }
        init_memory();
        init_targets();
    }

    private void readString(String str){
        bfcode = parse(str); // parse code
        init_memory(); // set all values of memory to 0
        init_targets(); // search for [ and ] in the code
    }

    private static String parse(String code){
        StringBuilder sb = new StringBuilder(code.length());
        for(int i = 0; i < code.length(); i++){
            char c = code.charAt(i); // get character at postion i

            if(isBFchar(c)){ // if c is a Brainfuck Character...
                sb.append(c); // ...append it to bfcode
            }
        }
        return sb.toString();
    }

    private static boolean isBFchar(char character){
        String c = String.valueOf(character); // convert char to string
        String bfChars = "<>+-,.[]";
        return bfChars.contains(c);
    }

    private void init_memory(){
        for (int i = 0; i < memory.length; i++){
            memory[i] = 0;
        }
    }

    private void init_targets(){
        for (int o = 0; o < bfcode.length(); o++){
            targets.add(0); // placeholder
        }

        Stack<Integer> temp_stack = new Stack<Integer>();
        for (int i = 0; i < bfcode.length(); i++){
            char c = bfcode.charAt(i);
            if(c == '['){
                temp_stack.push(i);
            }

            if(c == ']'){
                if(temp_stack.size() == 0){
                    error("Parseing error: ] with no matching [.");
                }
                int target = temp_stack.pop();
                targets.set(i, target);
                targets.set(target, i);
            }
        }

        if(temp_stack.size() > 0){
            error("Parseing error: [ with no matching ].");
        }
    }

    private void run(){
        while (codePtr != bfcode.length()){
            char op = bfcode.charAt(codePtr);
            execute_opcode(op);
            codePtr++;
        }
    }

    private void execute_opcode(char op){
        switch (op){
            case '>':
                increasePtr();
                break;

            case '<':
                decreasePtr();
                break;

            case '+':
                increaseMem();
                break;

            case '-':
                decreaseMem();
                break;

            case '.':
                System.out.print((char)memory[memPtr]);
                break;

            case ',':
                memory[memPtr] = (int)get_input();
                break;

            case '[':
                if(memory[memPtr] == 0){
                    codePtr = targets.get(codePtr);
                }
                break;

            case ']':
                codePtr = targets.get(codePtr) - 1;
                break;
        }
    }

    private void increasePtr(){
        memPtr++;
        if (memPtr >= memsize){
            memPtr = 0;
        }
    }

    private void decreasePtr(){
        memPtr--;
        if (memPtr < 0){
            memPtr = memsize;
        }
    }

    private void increaseMem(){
        memory[memPtr]++;
        if (memory[memPtr] > cellsize){
            memory[memPtr] = 0;
        }
    }

    private void decreaseMem(){
        memory[memPtr]--;
        if (memory[memPtr] < 0){
            memory[memPtr] = cellsize;
        }
    }

    private char get_input(){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        return reader.nextLine().charAt(0);
    }

    private void error(String msg) {
//        System.out.println(msg);
//        System.exit(0);
        throw new RuntimeException(msg);
    }
}
