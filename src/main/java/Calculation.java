import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Илья on 01.05.2015.
 */
public class Calculation {
    DbConnection dbc;

    private double H = 0.05;                    //глубина канала, м;
    private double W = 0.27;                    //ширина канала, м;
    private double L = 10.6;                    //длина канала, м;

    private double Ro;                                  //плотность материала, кг/м³;
    private double C;                                   //средняя удельная теплоемкость материала, Дж/(кг∙ºС);
    private double mu0;                                 //коэффициент консистенции материала при температуре приведения, Па∙сⁿ;
    private double b;                                   //температурный коэффициент вязкости материала, 1/ºС;
    private double Tr;                                  //температура приведения, ºС;
    private double n;                                   //индекс течения материала;
    private double alfau;                               //коэффициент теплоотдачи от крышки канала к материалу, Вт/(м²∙ºС);
    private double T0;                                  //температура плавления материала, ºС;


    private double Vu = 1.5;                    //скорость движения крышки канала, м/с; вводимый параметр
    private double Tu = 210;                    //температура крышки канала, ºС;  вводимый параметр
    private double Q;                           //производительность канала, кг/с;
    private double F = 0;                       //поправочный коэффициент
    private double V = 0;                       //вектор варьируемых параметров процесса;
    private double gamma;                       //
    private double Qgamma;                      //
    private double Qalfa;                       //
    private double ae;                          //kappa
    private double alfa = 1;                    //
    private double l;                           //
    private Vector<Double> T = new Vector();    //Температура материала в каждом слое
    private double G = 0;                       //вектор геометрических параметров канала [кг/ч]
    private double deltaL = 0.1;                //Шаг
    private double m;
    private Vector<Double> h = new Vector();    //Вязкость в каждом слое

    public Calculation(DbConnection dbc) throws SQLException {
        this.dbc = new DbConnection();
        this.dbc=dbc;
    }

    public void calculate() {
        connect_with_db();

        F = 0.125 * Math.pow(H / W, 2) - 0.625 * Math.pow(H / W, 2) + 1;
        gamma = Vu / H;
        Q = W * Vu * H * F;
        Qgamma = W * H * mu0 * Math.pow(gamma, n + 1);
        Qalfa = W * (alfa / b - alfa * Tu + alfa * Tr);
        m = L / deltaL;
        int i = 0;

        while (i <= m) {
            l = i * deltaL;
            ae = ((b * Qgamma + W * alfa) / b * Qalfa) * (1 - Math.exp((-b * Qalfa * l) / (Ro * C * Q))) + Math.exp(b * (T0 - Tr - (Qalfa * l) / (Ro * C * Q)));
            T.add(Tr + (1 / b) * Math.log10(ae));
            h.add(mu0 * Math.exp(-b * (T.get(i) - Tr)) * Math.pow(gamma, n - 1));
            i++;
        }

//        for (int j = 0; j < T.size(); j++) {
//            System.out.print("Температура в " + j + " серчении = " + T.get(j));
//            System.out.print("  Вязкость в " + j + " серчении = " + h.get(j));
//            System.out.println();
//        }
//        G = Q * Ro * 3600;
//        System.out.println("Производительность= " + G);
    }

    private void connect_with_db(){
        Ro = dbc.getRo();            //плотность материала, кг/м³;
        C = dbc.getC();              //средняя удельная теплоемкость материала, Дж/(кг∙ºС);
        mu0 = dbc.getMu0();          //коэффициент консистенции материала при температуре приведения, Па∙сⁿ;
        b = dbc.getB();              //температурный коэффициент вязкости материала, 1/ºС;
        Tr = dbc.getTr();            //температура приведения, ºС;
        n = dbc.getN();              //индекс течения материала;
        alfau = dbc.getAlfau();      //коэффициент теплоотдачи от крышки канала к материалу, Вт/(м²∙ºС);
        T0 = dbc.getT0();

    }
}
