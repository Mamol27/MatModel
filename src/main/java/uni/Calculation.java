package uni;

import java.util.Vector;


public class Calculation {

    private double H;                    //глубина канала, м;
    private double W;                    //ширина канала, м;
    private double L;                    //длина канала, м;

    private double Ro;                                  //плотность материала, кг/м³;
    private double C;                                   //средняя удельная теплоемкость материала, Дж/(кг∙ºС);
    private double mu0;                                 //коэффициент консистенции материала при температуре приведения, Па∙сⁿ;
    private double b;                                   //температурный коэффициент вязкости материала, 1/ºС;
    private double Tr;                                  //температура приведения, ºС;
    private double n;                                   //индекс течения материала;
    private double alfau;                               //коэффициент теплоотдачи от крышки канала к материалу, Вт/(м²∙ºС);
    private double T0;                                  //температура плавления материала, ºС;


    private double Vu;                                  //скорость движения крышки канала, м/с; вводимый параметр
    private double Tu;                                  //температура крышки канала, ºС;  вводимый параметр
    private double Q;                                   //производительность канала, кг/с;
    private double F = 0;                               //поправочный коэффициент
    private Vector<Double> T = new Vector<>();          //Температура материала в каждом слое
    private Vector<Double> h = new Vector<>();          //Вязкость в каждом слое
    private Vector<Double> layer = new Vector<>();
    private double deltaL = 0.1;                        //Шаг

    double gamma = 0;
    double qgamma = 0;
    double qalfa = 0;
    double ae = 0;


    public Calculation(Object ro, Object c, Object tr, Object mu0, Object b, Object t0, Object n, Object alfau) {
        Ro = (double) ro;
        C = (double) c;
        this.mu0 = (double) mu0;
        this.b = (double) b;
        Tr = (double) tr;
        this.n = (double) n;
        this.alfau = (double) alfau;
        T0 = (double) t0;
    }

    public void calculate() {
        T.clear();
        h.clear();
        layer.clear();

        F = 0.125 * Math.pow(H / W, 2) - 0.625 * Math.pow(H / W, 2) + 1;
        gamma = Vu / H;
        Q = W * Vu * H * F;
        qgamma = W * H * mu0 * Math.pow(gamma, n + 1);
        qalfa = W * (alfau / b - alfau * Tu + alfau * Tr);
        double m = L / deltaL + 1;
        int i = 0;

        while (i <= m) {
            double l = i * deltaL;
            ae = ((b * qgamma + W * alfau) / (b * qalfa)) * (1 - Math.exp((-b * qalfa * l) / (Ro * C * Q))) + Math.exp(b * (T0 - Tr - (qalfa * l) / (Ro * C * Q)));
            T.add(Tr + (1 / b) * Math.log(ae));
            h.add(mu0 * Math.exp(-b * (T.get(i) - Tr)) * Math.pow(gamma, n - 1));
            layer.add(l);
            i++;
        }


        double g = Q * Ro * 3600;
//        System.out.println("Производительность= " + G);
    }

    public Vector<Double> getT() {
        return T;
    }

    public Vector<Double> getH() {
        return h;
    }

    public Vector<Double> getLayer() {
        return layer;
    }

    public void setH(double h) {
        H = h;
    }

    public void setW(double w) {
        W = w;
    }

    public void setL(double l) {
        L = l;
    }

    public void setRo(double ro) {
        Ro = ro;
    }

    public void setC(double c) {
        C = c;
    }

    public void setMu0(double mu0) {
        this.mu0 = mu0;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setTr(double tr) {
        Tr = tr;
    }

    public void setN(double n) {
        this.n = n;
    }

    public void setAlfau(double alfau) {
        this.alfau = alfau;
    }

    public void setT0(double t0) {
        T0 = t0;
    }

    public void setVu(double vu) {
        Vu = vu;
    }

    public void setTu(double tu) {
        Tu = tu;
    }

    public void setQ(double q) {
        Q = q;
    }

    public void setF(double f) {
        F = f;
    }

    public void setDeltaL(double deltaL) {
        this.deltaL = deltaL;
    }
}
