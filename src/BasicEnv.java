import java.util.HashMap;
import java.util.Map;

public class BasicEnv implements Environment {
    Map<String, Object> map;
    Environment outer;

    public BasicEnv() {
        this.map = new HashMap<String, Object>();
        this.outer = null;
    }

    public BasicEnv(Environment outer) {
        this.map = new HashMap<String, Object>();
        this.outer = outer;
    }

    public void put(String name, Object entity) {
        if (isDefined(name)) {
            throw new Error("the variable has been defined");
        }

        map.put(name, entity);
    }

    public Object get(String name) {
        Object entity = map.get(name);

        if (entity == null && outer == null) {
            throw new Error("the variable has not been found");
        }
        else if (entity == null && outer != null) {
            return outer.get(name);
        }
        else {
            return entity;
        }
    }

    public boolean isDefined(String name) {
        if (outer == null) {
            return map.containsKey(name);
        }

        return map.containsKey(name) || outer.isDefined(name);
    }

}
