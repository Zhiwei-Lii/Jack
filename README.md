
# Jack解释器
 
## 语法
### 程序结构
**classFile** </br>
importDec* class+

**importDec** </br>
'import' classPath ';'

**class** </br>
'class' className '{' classVarDec* subroutineDec* '}'

**classVarDec** </br>
'static' type varName (',' varName)* ';' </br>
'field' type varName (',' varName)* ';' 

**type** </br>
'int' | 'char' | 'boolean' | className

**suborutineDec** </br>
('constructor' | 'function' | 'method' ) ('void' | type) subroutineName '(' parameterList ')' subroutineBody          

**parameterList** </br>
((type varName) (',' type varName)*)?

**subroutineBody** </br>
'{' statements '}'

### 语句
**statements** </br>
statement*

**statement** </br>
letStatement | ifStatement | whileStatement | doStatement | returnStatement

**letStatement** </br>
'let' varName ('[' expression ']')? '=' expression ';'

**ifStatement** </br>
'if' '(' expression ')' '{' statements '}'

**varDecStatement** </br>
'var' type varName (',' varName)* ';'

**whileStatement** </br>
'while' '(' expression ')' '{' statements '}'

**doStatement** </br>
'do' subroutineCall ';'

**returnStatement** </br>
'return' expression? ';'

### 表达式

**expression** </br>
term (op term)*

**term** </br>
integerConstant | stringConstant | keywordConstant | charConstant | varName | varName '[' expression ']' | subroutineCall | '(' expression ')' | unaryOp term

**subroutineCall** </br>
subroutineName '(' expressionList ')' 
(className | varName) '.' subroutineName '(' expressionList ')'

**expressionList** </br>
(expression (',' expression)*)?

**op** </br>
'+' | '-' | '*' | '/' | '&' | '&&' | '|' | '||' | '<' | '<=' | '>' | '>=' | '==' | '!='

**unaryOp** </br>
'-' | '~'

**keywordConstant** </br>
'true' | 'false' | 'this' | 'null'

## Native库函数
### Array类
- function Array new(int size); 

### String类
- method char charAt(int index);
- method int length();

### Input类
- function int readInt(); 
- function String readLn();

### Output类
- function void printLn(JackObject object);

## Example
基本的语法参见Example.jack

运行解释器的方法:

```
java Main Example.jack
```
