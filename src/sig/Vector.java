package sig;

public class Vector {
    float x,y,z,w;

    public Vector() {
        this(0,0,0,1);
    }

    public Vector(float x, float y, float z) {
        this(x,y,z,1);
    }

    public Vector(float x, float y, float z,float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
