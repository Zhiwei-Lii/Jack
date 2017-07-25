
public interface Environment {
    public void put(String name, Object entity);

    public Object get(String name);
    
    public boolean isDefined(String name);
    
}
