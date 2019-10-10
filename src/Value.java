public class Value {
    int value;
    Value(int i){
        this.value=i;
    }

    public int getValue(){
        return value;
    }

    public String toString(){
        return value+"";
    }

    public boolean equals(int i){
        return i==value;
    }
}
