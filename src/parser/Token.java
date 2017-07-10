package parser;

public class Token {

    private String image;
    private int type;

    public Token(String image, int type) {
        this.image = image;
        this.type = type;
    }

    public int type() {
        return type;
    }

    public String image() {
        return image;
    }
    
    public String toString(){
        return "TokenType is " + type + ", " + "Image is " + image;
    }

}
