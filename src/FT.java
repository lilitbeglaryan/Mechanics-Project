public class FT {
    Function f ;
    double period , delta ; double omega0;
    int N;

    FT(Function f, int N, double delta) {
        this.f = f;
        this.delta = delta ;
        this.N = N;
        period = N*delta;
        omega0 = 2*Math.PI/period;
    }

    double getSineCoefficient ( int n) { double sum = 0;
        double t = 0;
        for(int i = 0;i<N;i++) {
            sum += f.evaluate(t)*Math.sin(n*omega0*t); t += delta;
        }
        return 2*delta*sum/period;
    }

    double getCosineCoefficient ( int n) { double sum = 0;
        double t = 0;
        for(int i = 0;i<N;i++) {
            sum += f.evaluate(t)*Math.cos(n*omega0*t); t += delta;
        }
        return 2 * delta * (sum/period);
    }

}
 interface Function{
    public int evaluate(double x);
}