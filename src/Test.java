import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String msg = "Hul<";
        HulToC hulToC=null;
        Pattern pattern = Pattern.compile("Hul*");
        Matcher matcher = pattern.matcher(msg);
        System.out.println(matcher.find());

        //읽어오기
        String filePath = "test5.hul";
        try(FileInputStream stream = new FileInputStream(filePath)){
            byte[] rb = new byte[stream.available()];
            ///file이 끝에 도달하면 -1반환
            while(stream.read(rb)!=-1){}
            msg = new String(rb);
            //파일 제대로 읽어오는지 확인
            System.out.println(msg);
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
