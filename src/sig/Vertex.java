package sig;

public class Vertex {
    float x,y,z,w;

    public Vertex() {
        this(0,0,0,1);
    }

    public Vertex(float x, float y, float z) {
        this(x,y,z,1);
    }

    public Vertex(float x, float y, float z,float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
