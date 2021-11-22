package sig;

public class Vector2 {
    float u,v,w;

    public Vector2() {
        this(0,0,1);
    }

    public Vector2(float u, float v) {
        this(u,v,1);
    }

    public Vector2(float y, float v, float w) {
        this.u=u;
        this.v=v;
        this.w=w;
    }
}
