import static java.lang.Math.*;

public class Spring {
    private double k;

    /* EX #1 */
    public Spring(){
        this.k = 1.0;
    }

    public Spring(double stiffness){
        if(stiffness <=0)
            return;
        this.k = stiffness;
    }
    public double getK(){
        return this.k;
    }
    private void setK(double newK){
        if(newK <=0)
            return;
        this.k = newK;
    }

    public double[] move(double t, double dt, double x0, double v0)
    {
        int intervals = (int)(t/dt);
        double[] discretized_motion = new double[intervals+1];
        double omega = sqrt(k); // again mass is 1
        int j = 0;

        for (double i = 0; i <= t ; i += dt)
        {
            double xi = (x0 * cos(omega*i)) + (v0/omega * sin(omega*i)); // now v0 is not necessarily 0
            discretized_motion[j] = xi;  // record the coordinates
            j++;
        }

        return discretized_motion;

    }

    public double[] move(double t, double dt, double x0){
        int intervals = (int)(t/dt);
        double[] discretized_motion = new double[intervals+1];
        double omega0 = sqrt(k); // as sqrt(k/m) m is unit mass(i.e. m=1), write just sqrt(k)
        int j = 0;

        for (double i = 0; i <= t ; i += dt)
        {
            double xi = (x0 * cos(omega0*i));  // no need to add (v0/omega0)*sin(omeaga0*t) as v0 is assumed to be 0
            discretized_motion[j] = xi;
            j++;  // could also use (int)i when referring to the indices of the array
        }

        return discretized_motion;
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0)
    {
        int intervals = (int)((t1-t0)/dt); // now t0 is not necessarily equal to 0
        double[] discretized_motion = new double[intervals+1];
        double omega = sqrt(k); // mass is 1
        int j = 0;

        for (double current_t= t0; current_t<= t1 ; current_t += dt)
        {
            double xt = (x0 * cos(omega*current_t)) + (v0/omega * sin(omega*current_t));
            discretized_motion[j] = xt;
            j++;
        }

        return discretized_motion;
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m)
    {
        int intervals = (int)((t1-t0)/dt);
        double[] discretized_motion = new double[intervals+1];
        double omega = sqrt(k/m); // now m may be not 1
        int j = 0;

        for (double current_t = t0; current_t <= t1 ; current_t += dt)
        {
            double xt = (x0 * cos(omega*current_t)) + (v0/omega * sin(omega*current_t));
            discretized_motion[j] = xt;
            j++;
        }

        return discretized_motion;
    }


    /* EX # 2.1*/
    public Spring inSeries(Spring that){
        Spring newSpring= new Spring();
        double newStiffness= (that.getK() * this.getK()) / (that.getK() + this.getK());  // (k1*k2)/(k1+k2) if connected in series
        newSpring.setK(newStiffness);
        return newSpring;
    }

    public Spring inParallel(Spring that){
        Spring newSpring= new Spring();
        double newStiffness = that.getK() + this.getK(); // when joined in parallel, k'=k1+k2+...k_n
        newSpring.setK(newStiffness);
        return newSpring;
    }

    public static void main(String[] args){
        Spring s1=new Spring(10);
        Spring s2= new Spring(20);
        System.out.println(round(s1.inSeries(s2).getK()) + "is the new stiffness, in series");
        System.out.print(s1.inParallel(s2).getK() + "is the new stiffness, in parallel");
    }

}
