package Lab3;

public class Regression_3factors {
    public  static double[][] mini_kohren = {{9985, 9750, 9392, 9057, 8772},
            {9669, 8709, 7977, 7457, 7071},
            {9065, 7679, 6841, 6287, 5892},
            {8412, 6838, 5981, 5440, 5063},
            {7808, 6161, 5321, 4803,4447}};

    public static double[] mini_student = {12.71, 4.303, 3.182, 2.776, 2.571, 2.447, 2.365, 2.306, 2.262, 2.228, 2.201, 2.179, 2.16, 2.145, 2.131};

    public static double[][] mini_fisher = {{164.4, 199.5, 215.7, 224.6, 230.2},
            {18.5, 19.2, 19.2, 19.3, 19.3},
            {10.1, 9.6, 9.3, 9.1, 9.0},
            {7.7, 6.9, 6.6, 6.4, 6.3},
            {6.6, 5.8, 5.4, 5.2, 5.1},
            {6.0, 5.1, 4.8, 4.5, 4.4},
            {5.5, 4.7, 4.4, 4.1, 4.0},
            {5.3, 4.5, 4.1, 3.8, 3.7},
            {5.1, 4.3, 3.9, 3.6, 3.5},
            {5.0, 4.1, 3.7, 3.5, 3.3}};

    public static void main(String[] args) {
        int[][] array_x = new int[4][3]; // матриця факторів
        int n = array_x.length;
        double[][] array_xn = new double[array_x.length][array_x[0].length + 1]; // матриця нормованих факторів
        int x1min = -15, x1max = 30, x2min = -20, x2max = 40, x3min = -15, x3max = -5;
        double[] x0 = new double[array_x[0].length];
        double[] dx = new double[x0.length];
        int m = 3; // кількість провведених експериментів
        double[][] array_y = new double[array_x.length][m]; // матриця функцій відгуку
        double q = 0.05; // рівень значимості
        double fp; //експериментальний критерій Фішера
        int x0_el = 1; // додаткові елементи до матриці нормованих факторів

        double ymax = 200 + (x1max + x2max + x3max)/3;
        double ymin = 200 + (x1min + x2min + x3min)/3;

        double y1 = 0, y2 = 0, y3 = 0, y4 = 0; // середні значення функцій відгуку в рядках

        double b0, b1, b2, b3; // коефіцієнти рівняння регресії


        array_x[0][0] = x1min;
        array_x[1][0] = x1min;
        array_x[2][0] = x1max;
        array_x[3][0] = x1max;
        array_x[0][1] = x2min;
        array_x[1][1] = x2max;
        array_x[2][1] = x2min;
        array_x[3][1] = x2max;
        array_x[0][2] = x3min;
        array_x[1][2] = x3max;
        array_x[2][2] = x3max;
        array_x[3][2] = x3min;

        x0[0] = (double)(x1max + x1min)/2;
        dx[0] = x1max - x0[0];

        x0[1] = (double)(x2max + x2min)/2;
        dx[1] = x2max - x0[1];

        x0[2] = (double)(x3max + x3min)/2;
        dx[2] = x3max - x0[2];

        for (int i = 0; i < array_xn.length; i++){
            for (int j = 1; j < array_xn[i].length; j++){
                array_xn[i][j] = (array_x[i][j - 1] - x0[j - 1])/dx[j - 1];
            }
        }

        System.out.println("\nМатриця нормованих факторів:");
        for (int i = 0; i < array_xn.length; i++){
            for (int j = 0; j < array_xn[i].length; j++){
                if(j == 0){
                    System.out.print(x0_el + "  ");
                }
                else{
                    double help = array_xn[i][j] * 1000;
                    help = Math.round(help);
                    System.out.print((help/1000) + "  ");
                }
            }
            System.out.println();
        }

        for (int i = 0; i < array_y.length; i++){
            for (int j = 0; j < array_y[i].length; j++){
                array_y[i][j] = (int)(Math.random()*(ymax - ymin + 1) + ymin + 1);
            }
        }
        System.out.println("\nМатриця функцій відгуку:");
        for (int i = 0; i < array_y.length; i++){
            for (int j = 0; j < array_y[i].length; j++){
                System.out.print(array_y[i][j] + "  ");
            }
            System.out.println();
        }

        for (int i = 0; i < array_y[0].length; i++){
            y1 += array_y[0][i];
            y2 += array_y[1][i];
            y3 += array_y[2][i];
            y4 += array_y[3][i];
        }
        y1 /= m;
        y2 /= m;
        y3 /= m;
        y4 /= m;

        double mx1 = 0, mx2 = 0, mx3 = 0;
        double my;
        double a1, a2, a3;
        double a11 = 0, a22 = 0, a33 = 0, a12 = 0, a13 = 0, a23 = 0;
        for (int i = 0; i < 4; i++){
            mx1 += array_x[i][0];
            mx2 += array_x[i][1];
            mx3 += array_x[i][2];
            a11 += array_x[i][0]*array_x[i][0];
            a22 += array_x[i][1]*array_x[i][1];
            a33 += array_x[i][2]*array_x[i][2];
            a12 += array_x[i][0]*array_x[i][1];
            a13 += array_x[i][0]*array_x[i][2];
            a23 += array_x[i][1]*array_x[i][2];
        }
        mx1 /= n;
        mx2 /= n;
        mx3 /= n;
        my = (y1 + y2 + y3 + y4)/n;
        a1 = (array_x[0][0]*y1 + array_x[1][0]*y2 + array_x[2][0]*y3 + array_x[3][0]*y4)/n;
        a2 = (array_x[0][1]*y1 + array_x[1][1]*y2 + array_x[2][1]*y3 + array_x[3][1]*y4)/n;
        a3 = (array_x[0][2]*y1 + array_x[1][2]*y2 + array_x[2][2]*y3 + array_x[3][2]*y4)/n;
        a11 /= n;
        a22 /= n;
        a33 /= n;
        a12 /= n;
        a13 /= n;
        a23 /= n;

        double[][] delta_0 = {{my, mx1, mx2, mx3}, {a1, a11, a12, a13}, {a2, a12, a22, a23}, {a3, a13, a23, a33}};
        double[][] delta_1 = {{1, my, mx2, mx3}, {mx1, a1, a12, a13}, {mx2, a2, a22, a23}, {mx3, a3, a23, a33}};
        double[][] delta_2 = {{1, mx1, my, mx3}, {mx1, a11, a1, a13}, {mx2, a12, a2, a23}, {mx3, a13, a3, a33}};
        double[][] delta_3 = {{1, mx1, mx2, my}, {mx1, a11, a12, a1}, {mx2, a12, a22, a2}, {mx3, a13, a23, a3}};
        double[][] delta = {{1, mx1, mx2, mx3}, {mx1, a11, a12, a13}, {mx2, a12, a22, a23}, {mx3, a13, a23, a33}};

        b0 = Delta(delta_0)/Delta(delta);
        b1 = Delta(delta_1)/Delta(delta);
        b2 = Delta(delta_2)/Delta(delta);
        b3 = Delta(delta_3)/Delta(delta);

        if(b1 < 0){
            if(b2 < 0){
                if(b3 < 0){
                    System.out.format("Рівняння регресії: %n y = %.1f %.1f x1 %.1f x2 %.1f x3", b0, b1, b2, b3);
                }
                else{
                    System.out.format("Рівняння регресії: %n y = %.1f %.1f x1 %.1f x2 + %.1f x3", b0, b1, b2, b3);
                }
            }
            else if(b3 < 0){
                System.out.format("Рівняння регресії: %n y = %.1f %.1f x1 + %.1f x2 %.1f x3", b0, b1, b2, b3);
            }
            else{
                System.out.format("Рівняння регресії: %n y = %.1f %.1f x1 + %.1f x2 + %.1f x3", b0, b1, b2, b3);
            }
        }
        else{
            if(b2 < 0){
                if(b3 < 0){
                    System.out.format("Рівняння регресії: %n y = %.1f + %.1f x1 %.1f x2 %.1f x3", b0, b1, b2, b3);
                }
                else{
                    System.out.format("Рівняння регресії: %n y = %.1f + %.1f x1 %.1f x2 + %.1f x3", b0, b1, b2, b3);
                }
            }
            else if(b3 < 0){
                System.out.format("Рівняння регресії: %n y = %.1f + %.1f x1 + %.1f x2 %.1f x3", b0, b1, b2, b3);
            }
            else{
                System.out.format("Рівняння регресії: %n y = %.1f + %.1f x1 + %.1f x2 + %.1f x3", b0, b1, b2, b3);
            }
        }

        System.out.println();

        double disp1 = 0, disp2 = 0, disp3 = 0, disp4 = 0;
        double exp_kohren;
        for(int i = 0; i < array_y[0].length; i++){
            disp1 += Math.pow((array_y[0][i] - y1), 2);
            disp2 += Math.pow((array_y[1][i] - y2), 2);
            disp3 += Math.pow((array_y[2][i] - y3), 2);
            disp4 += Math.pow((array_y[3][i] - y4), 2);
        }

        disp1 /= m;
        disp2 /= m;
        disp3 /= m;
        disp4 /= m;

        exp_kohren = (Math.max((Math.max(disp1, disp2)), (Math.max(disp3, disp4))))/(disp1 + disp2 + disp3 + disp4);

        if(exp_kohren < (mini_kohren[n-2][(m-1) - 1])/10000){
            System.out.println("Дисперсія однорідна");
        }

        double s = Math.sqrt((disp1 + disp2 + disp3 + disp4)/(n*n*m));
        double beta0 = 0, beta1 = 0, beta2 = 0, beta3 = 0;
        double t0, t1, t2, t3, t_tabl;
        double[] array_mid_y = {y1, y2, y3, y4};

        for(int i = 0; i < n; i++){
            beta0 += array_mid_y[i]*x0_el;
            beta1 += array_mid_y[i]*array_xn[i][0];
            beta2 += array_mid_y[i]*array_xn[i][1];
            beta3 += array_mid_y[i]*array_xn[i][2];
        }
        beta0 /= n;
        beta1 /= n;
        beta2 /= n;
        beta3 /= n;

        t0 = beta0/s;
        t1 = beta1/s;
        t2 = beta2/s;
        t3 = beta3/s;
        /*System.out.format("%.3f  %.3f  %.3f  %.3f", t0, t1, t2, t3);
        System.out.println();*/

        t_tabl = mini_student[(m-1)*n - 1];
        int d = 0; // кількість значущих коефіцієнтів при рівні значимості q = 0.05
        if(t0 < t_tabl){
            System.out.println("b0 - незначний коефіцієнт => вилучається з рівняння");
            b0 = 0;
        }
        else{
            d += 1;
        }
        if(t1 < t_tabl){
            System.out.println("b1 - незначний коефіцієнт => вилучається з рівняння");
            b1 = 0;
        }
        else{
            d += 1;
        }
        if(t2 < t_tabl){
            System.out.println("b2 - незначний коефіцієнт => вилучається з рівняння");
            b2 = 0;
        }
        else{
            d += 1;
        }
        if(t3 < t_tabl){
            System.out.println("b3 - незначний коефіцієнт => вилучається з рівняння");
            b3 = 0;
        }
        else{
            d += 1;
        }

        double[] some_array_mid_y = new double[n];
        for(int i = 0; i < some_array_mid_y.length; i++){
            some_array_mid_y[i] = b0 + b1*array_x[i][0] + b2*array_x[i][1] + b3*array_x[i][2];
        }

        double s_ad = 0;
        for(int i = 0; i < n; i++){
            s_ad += Math.pow((some_array_mid_y[i] - array_mid_y[i]),2);
        }
        s_ad *= m;
        s_ad /= n-d;
        fp = (s_ad*n)/(disp1 + disp2 + disp3 + disp4);

        if(fp > mini_fisher[(m-1)*n - 1][n - d - 1]){
            System.out.println("Рівняння регресії неадекватно оригіналу при рівні значимості " + q);
        }
        else{
            System.out.println("Рівняння регресії адекватно оригіналу при рівні значимості " + q);
        }

    }

    public static double Kramer(double[][] a1/*, double[][] a2*/){ //метод для розрахунку визначників 3*3
        /*double result;
        double delta;*/
        double resh;
        resh = a1[0][0]*a1[1][1]*a1[2][2] + a1[0][1]*a1[1][2]*a1[2][0] + a1[1][0]*a1[0][2]*a1[2][1] -
                a1[0][2]*a1[1][1]*a1[2][0] - a1[0][0]*a1[1][2]*a1[2][1] - a1[0][1]*a1[1][0]*a1[2][2];
        /*delta = a2[0][0]*a2[1][1]*a2[2][2] + a2[0][1]*a2[1][2]*a2[2][0] + a2[1][0]*a2[0][2]*a2[2][1] -
                a2[0][2]*a2[1][1]*a2[2][0] - a2[0][0]*a2[1][2]*a2[2][1] - a2[0][1]*a2[1][0]*a2[2][2];*/
        /*result = resh/delta;*/
        return resh;
    }

    public static double Delta(double[][] array){  //метод для розрахунку визначників 4*4
        int len = array.length;
        double resh_help = 0;

        for(int i = 0; i < len; i++){
            double[][] array_delta = new double[array.length - 1][array[0].length - 1];
            for(int j = 0; j < len; j++){
                for(int k = 1; k < len; k++){
                    if(j == i){
                        break;
                    }
                    else if(j < i){
                        array_delta[j][k-1] = array[j][k];
                    }
                    else{
                        array_delta[j-1][k-1] = array[j][k];
                    }
                }
            }
            if((double)i%2 == 0.0){
                resh_help += Kramer(array_delta) * array[i][0];
            }
            else{
                resh_help -= Kramer(array_delta) * array[i][0];
            }
        }
        return resh_help;
    }

}
