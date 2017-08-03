package parser;

public class Token {

    private String image;
    private int type;
    private int lineNo;

    public Token(String image, int type, int lineNo) {
        this.image = image;
        this.type = type;
        this.lineNo = lineNo;
    }

    public int type() {
        return type;
    }

    public String image() {
        return image;
    }
    
    public void setLineNo(int lineNo){
        this.lineNo = lineNo;
    }
    
    public int getLineNo() {
        return lineNo;
    }
    
    public String toString(){
        return "TokenType is " + type + ", " + "Image is " + image;
    }

}
