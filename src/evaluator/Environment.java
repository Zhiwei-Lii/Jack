package evaluator;

public interface Environment {
    
    // 在该层放入定义
    public void put(String name, Object entity);

    public Object get(String name);
    
    public boolean isDefined(String name);
    
    // 返回定义该变量的env, 修改变量定义的时候必须知道定义该变量的env
    public Environment returnEnv(String name);
    
}
