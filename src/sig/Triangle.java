package sig;

public class Triangle {
    Vector A;
    Vector B;
    Vector C;
    Vector2 T;
    Vector2 U;
    Vector2 V;
    public Triangle(Vector a, Vector b, Vector c) {
        this(a,b,c,new Vector2(),new Vector2(),new Vector2());
    }
    public Triangle(Vector a, Vector b, Vector c, Vector2 t, Vector2 u, Vector2 v) {
        A = a;
        B = b;
        C = c;
        T = t;
        U = u;
        V = v;
    }
}
