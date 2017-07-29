import java.util.HashMap;
import java.util.Map;

public class BasicEnv implements Environment {
    protected Map<String, Object> map;
    protected Environment outer;

    public BasicEnv() {
        this.map = new HashMap<String, Object>();
        this.outer = null;
    }

    public BasicEnv(Environment outer) {
        this.map = new HashMap<String, Object>();
        this.outer = outer;
    }

    // 一个为r的函数和一个为r的int, 需要仔细处理一下
    public void put(String name, Object entity) {
        map.put(name, entity);
    }

    public Object get(String name) {
        Object entity = map.get(name);

        if (entity == null && outer == null) {
            throw new Error("the variable " + name + " has not been found");
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

    /* 返回定义var的env, 便于修改 */
    public Environment returnEnv(String name) {
        if (outer == null && map.containsKey(name)) {
            return this;
        }
        else if (outer == null && !map.containsKey(name)) {
            return null;
        }
        else if (outer != null && map.containsKey(name)) {
            return this;
        }
        else if (outer != null && !map.containsKey(name)) {
            return outer.returnEnv(name);
        }

        return null;
    }

}
