package fr.ideria.nooblib.data;

/**
 * An object that store a data
 * @param <T> the class of the data
 */
public class Data<T> {
    /**
     * The data that is stored
     */
    private T data;

    /**
     * Constructor
     * @param data the stored data
     */
    public Data(T data){
        this.data = data;
    }

    /**
     * Returns a new Data object with the datatype of the "o" parameter
     * @param o the object with an undefined datatype
     * @return new Data with T = o.class
     */
    public static Data<?> fromObject(Object o){
        if(o instanceof String)
            return new Data<>(o.toString());
        else if(o instanceof Integer)
            return new Data<>((int) o);
        else if(o instanceof Float)
            return new Data<>((float) o);
        else if(o instanceof Double)
            return new Data<>((double) o);
        else if(o instanceof Long)
            return new Data<>((long) o);
        else if(o instanceof Byte)
            return new Data<>((byte) o);
        else if(o instanceof Short)
            return new Data<>((short) o);
        else if(o instanceof String[])
            return new Data<>((String[])o);
        else if(o instanceof int[])
            return new Data<>((int[]) o);
        else if(o instanceof float[])
            return new Data<>((float[]) o);
        else if(o instanceof double[])
            return new Data<>((double[]) o);
        else if(o instanceof long[])
            return new Data<>((long[]) o);
        else if(o instanceof byte[])
            return new Data<>((byte[]) o);
        else if(o instanceof short[])
            return new Data<>((short[]) o);

        return new Data<>(o);
    }

    /**
     * Getters and setters
     */
    public T get(){ return data; }
    public void set(T data){ this.data = data; }

    @Override public String toString() { return data.toString(); }
}