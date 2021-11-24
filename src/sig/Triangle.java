package sig;

public class Triangle {
    Vector A;
    Vector B;
    Vector C;
    Vector2 T;
    Vector2 U;
    Vector2 V;
    int tex; //The texture ID.
    public Triangle(Vector a, Vector b, Vector c) {
        this(a,b,c,new Vector2(),new Vector2(),new Vector2(),TextureType.UNUSED_1);
    }
    public Triangle(Vector a, Vector b, Vector c, Vector2 t, Vector2 u, Vector2 v,TextureType tex) {
        A = a;
        B = b;
        C = c;
        T = t;
        U = u;
        V = v;
        this.tex=tex.ordinal();
    }
}
