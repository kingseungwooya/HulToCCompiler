import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class HulToC {
    /**
     *
     Hul? standard input으로 정수를 읽어서 변수에 저장한다.
     Hul? standard output으로 변수를 출력한다.
     Hul> 변수의 값을 1증가시킨다.
     Hul< 변수의 값을 1감소시킨다.
     Hul{ <명령들> } <반복 횟수>
     <명령들>을 <반복 횟수>번 수행한다
     */
    Parser parser;
    FileWriter fw;
    String hulText;
    // 들여쓰기 { -> ++, } ->  --
    static int lines=1;
    //정수가 입력되었을 때 순서를 부여하기 위한 값
    static int countNum=0;


    public Parser getParser() {
        return parser;
    }

    public HulToC(String msg, FileWriter fw) throws IOException {
        this.fw = fw;
        //기본 셋팅 항상 일관되게
        this.hulText=msg;
        this.fw.append("#include <stdio.h>\nint main() {\n\tint _hul;\n");

        //Parser객체를 생성과 동시에 void parsing함수로 인해 tokens혹은 numlist에 저장됌
        parser = new Parser(msg);
        //입력된 정수들 이터레이터로 꺼내주고 변수로 만들어주기
        Iterator<Integer> itr = this.getParser().getMaxList().iterator();
        //정수가 입력된 갯수만큼만 만들어줘야함
        int count= this.getParser().getMaxList().size();
        for(int i=0; i<count;i++) {
            this.fw.append("\tint max" + i + " = " + itr.next() + ";\n");
        }

    }
    //tokens를 순회할 함수
    public Parser.Token next() {
        if(parser.getTokens().isEmpty()){
            return null;
        }
        //return parser.getTokens().get(0);
        return parser.getTokens().remove(0);
    }

    //이상태는 이미 tokens와 maxList가 다 완성 되어있는 상태 각 값들마다 출력형식을 정한다,.
    public void translate(Parser.Token token) throws IOException {
        switch (token){
            case READ:{
                //standard input으로 정수를 읽어서 변수에 저장한다.
                fw.append(("\t").repeat(lines)+"printf(\"input: \");\n"+("\t").repeat(lines)+"scanf(\"%d\", &_hul);\n");
                break;
            }
            case WRITE:{
                //standard output으로 변수를 출력한다.
                fw.append(("\t").repeat(lines)+"printf(\"%d\", _hul);\n");
                break;
            }
            case INC:{
                //변수의 값을 1증가시킨다.
                fw.append(("\t").repeat(lines)+"_hul++;\n");
                break;
            }
            case DEC:{
                //변수의 값을 1감소시킨다.
                fw.append(("\t").repeat(lines)+"_hul--;\n");
                break;
            }
            //Hul{ <명령들> } <반복 횟수>
            //<명령들>을 <반복 횟수>번 수행한다.
            case BLOCK_BEGIN:{
                fw.append(("\t").repeat(lines)+"for (int i"+countNum+"=0; i"+countNum+" < max+"+countNum+"; i"+countNum+"++){\n ");
                countNum++;
                lines++;
                break;
            }
            case BLOCK_END:{
                fw.append(("\t").repeat(lines)+"}"+this.getParser().getMaxList().removeLast()+"\n");
                lines--;
                break;
            }
            default: {
                fw.append("문제발생 !");
            }
        }
    }
    //cmd 윈도우 명령어 처리기를 이용하기
}
