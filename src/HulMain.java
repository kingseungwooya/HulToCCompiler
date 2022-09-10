import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HulMain {
    public static void main(String[] args) {

        String msg = "Hul<";
        HulToC hulToC;

        //읽어오기
        Scanner sc = new Scanner(System.in);
        //입력 로컬경로에 있는 .hul파일만 입력 가능 !!!
        String filePath = sc.nextLine();
        // String filePath = "test5.hul";
        try(FileInputStream stream = new FileInputStream(filePath)){
            byte[] rb = new byte[stream.available()];
            ///file이 끝에 도달하면 -1반환
            while(stream.read(rb)!=-1){}
            msg = new String(rb);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //쓰기
        try(FileWriter fw = new FileWriter("test.c")) {
            hulToC = new HulToC(msg, fw);//이 순간 모든 command들이 enum타입에 따라 tokens ArrayList에 담김

            while (!hulToC.getParser().getTokens().isEmpty()){
                hulToC.translate(hulToC.next()); //Parser에 있는 tokens

            }
            fw.append("\treturn 0;\n}\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
