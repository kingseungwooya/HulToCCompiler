import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public enum Token{
        READ("Hul?"), WRITE("Hul!"), INC("Hul>"), DEC("Hul<"),
        BLOCK_BEGIN("Hul{"), BLOCK_END("}"), FAIL("fail");
        String str;
        Token(String s) {
            this.str =s;
        }
    }
    private ArrayList<Token> tokens = new ArrayList<>();
    //가장 바깥자료가 마지막에 위치하고 바깥자료 먼저 출력해야함으로 스텍 쓰기
    private Deque<Integer> maxList = new ArrayDeque<>();

    public Deque<Integer> getMaxList() {
        return maxList;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public Parser(String msg) {
        parsing(msg);
    }

    public void parsing(String msg){
        //split함수 이용해 msg들 분리
        String[] msgList = msg.split("[\n\t \r\n]+");

        //리스트에 정수가 있다면 따로 처리하기 위해 분리함
        for(String s: msgList){
            if(s.matches("[+-]?\\d*(\\.\\d+)?")){
                //문자열이 정수인지 확인 후 정수면 deque에 저장
                maxList.push(Integer.parseInt(s));
            }
            else{
                tokens.add(findToken(s));
            }
        }
    }
    private Token findToken(String msg){
        if(msg.equals(Token.BLOCK_BEGIN.str)) return Token.BLOCK_BEGIN;
        if(msg.equals(Token.BLOCK_END.str)) return Token.BLOCK_END;
        if(msg.equals(Token.READ.str)) return Token.READ;
        if(msg.equals(Token.WRITE.str)) return Token.WRITE;
        if(msg.equals(Token.DEC.str)) return Token.DEC;
        if(msg.equals(Token.INC.str)) return Token.INC;
        return Token.FAIL;
    }
}
