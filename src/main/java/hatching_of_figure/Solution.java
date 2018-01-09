package hatching_of_figure;

/**
 * Created by leha on 2017-03-08.
 */
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {


    public static void main(String[] args) throws IOException
    {
        ArrayList<Point> list = new ArrayList<Point>();

        //читаем все точки по порядку. x1, y1, x2, y2, итд. пока не скажем end
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line1 = reader.readLine();
        String line2 = reader.readLine();
        while (!line1.equals("end"))
        {
            list.add(new Point(Integer.parseInt(line1), Integer.parseInt(line2)));
            line1 = reader.readLine();
            line2 = reader.readLine();
        }
        reader.close();

        // создаём массив отрезков из последующих двух точек.
        ArrayList<Point[]> otrezok = new ArrayList<Point[]>();
        Point[] otr;
        for (int i = 0; i <= list.size() - 2; i++)
        {
            otr = new Point[]{list.get(i), list.get(i + 1)};
            otrezok.add(otr);
            if (i == list.size() - 2)
            {
                otr = new Point[]{list.get(i + 1), list.get(0)};
                otrezok.add(otr);
            }
        }
        for (int i = 0; i < list.size(); i++)
            System.out.println(otrezok.get(i)[0] + " " + otrezok.get(i)[1]);
        // инициализируем верхнюю и нижнюю границу фигуры.
        double ymax = -2000000000;
        double ymin = 2000000000;
        for (Point p : list)
        {
            if (p.getY() > ymax) ymax = p.getY();
            if (p.getY() < ymin) ymin = p.getY();
        }

        // для каждой высоты "y" проверяем точки пересечения с отрезком или с точкой. Точки(координаты Х) заносим в мессив.
        // "1" значит пересечение с отрезком, "0" значит пересечение с точкой.
        // начинаем !
        for(double y = ymin; y <= ymax; y=y+Math.min(1,ymax-y))
        {
            ArrayList<Double[]> to4ki_peresecheniya = new ArrayList<Double[]>();
            double x1;
            double x2;
            double y1;
            double y2;
            for (int i = 0; i < otrezok.size(); i++)
            {
                x1 = otrezok.get(i)[0].getX();
                x2 = otrezok.get(i)[1].getX();
                y1 = otrezok.get(i)[0].getY();
                y2 = otrezok.get(i)[1].getY();
                if ((y1 > y && y2 < y) || (y1 < y && y2 > y))
                {
                    double x = x2 - (y2 - y) * (x2 - x1) / (y2 - y1);
                    to4ki_peresecheniya.add(new Double[]{x, (double) 1});
                }
                if (y1 == y)
                {
                    to4ki_peresecheniya.add(new Double[]{x1, (double) 0});
                }
                if (y2 == y)
                {
                    to4ki_peresecheniya.add(new Double[]{x2, (double) 0});
                }
            }
            // удаляем повторяющиеся пункты пересечения и сортируем их по возрастанию.
            HashMap<Double,Double > hs = new HashMap<>();
            for (int j = 0; j < to4ki_peresecheniya.size() - 1; j++)
            {
                hs.put(to4ki_peresecheniya.get(j)[0], to4ki_peresecheniya.get(j)[1]);
            }
            to4ki_peresecheniya.clear();
            Iterator it = hs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                to4ki_peresecheniya.add(new Double[]{(Double)pair.getKey(), (Double)pair.getValue()});
            }

            for (int i = 0; i < to4ki_peresecheniya.size(); i++)
            {
                for (int j = i; j < to4ki_peresecheniya.size() - 1; j++)
                {
                    if (to4ki_peresecheniya.get(j)[0] > to4ki_peresecheniya.get(j + 1)[0])
                    {
                        Double[] temp = to4ki_peresecheniya.get(j);
                        to4ki_peresecheniya.remove(j);
                        to4ki_peresecheniya.add(j + 1, temp);
                    }
                }
            }
            // проверка: пункт пересечения + код (пересечение точки или отрезка)
            for (int i = 0; i < to4ki_peresecheniya.size(); i++)
                System.out.println(to4ki_peresecheniya.get(i)[0] + " " + to4ki_peresecheniya.get(i)[1]);

            // создание массива для реальных границ фигуры. "0" значит вход, "1" значит выход из фигуры, "2" значит одиночное касание пункта.
            ArrayList<Double[]> boundaries = new ArrayList<>();
            Double[] d = new Double[2];

            // создание массива для 2х пунктов 2х отрезков, через которые не проходит сечение.
            Point[] p = new Point[2];
            // создание массива для отрезка, через который проходит сечение.
            Point[] p2 = new Point[2];
            boolean in_figure = false;  // проверяем вошли ли мы в фигуру.


            // проверяем случай с пересечением точки, добавляем соответствующие правдивые границы пересечения.
            for (int i = 0; i < to4ki_peresecheniya.size(); i++)
            {
                // пересечение пункта
                if (to4ki_peresecheniya.get(i)[1] == 0)
                {
                    int counter = 0;
                    for (Point[] points : otrezok)   // инициализируем массив двумя остальными пунктами пересекающихся отрезков.
                    {
                        if (points[0].getX() == to4ki_peresecheniya.get(i)[0]&& points[0].getY() == y){
                            p[counter] = points[1];
                            counter++;
                        }
                        if (points[1].getX() == to4ki_peresecheniya.get(i)[0]&& points[1].getY() == y){
                            p[counter] = points[0];
                            counter++;
                        }

                    }
                    if ((p[0].getY() > y && p[1].getY() < y) || (p[0].getY() < y && p[1].getY() > y))
                    {
                        d[0] = to4ki_peresecheniya.get(i)[0];
                        if (!in_figure)
                        {
                            d[1] = 0.0;
                            boundaries.add(d);
                            in_figure = !in_figure;
                        }
                        if (in_figure)
                        {
                            d[1] = 1.0;
                            boundaries.add(d);
                            in_figure = !in_figure;
                        }
                    }
                    if ((p[0].getY() > y && p[1].getY() > y) || (p[0].getY() < y && p[1].getY() < y))
                    {
                        if (!in_figure)
                        {
                            d[0] = to4ki_peresecheniya.get(i)[0];
                            d[1] = 2.0;
                            boundaries.add(d);
                        }
                    }
                    // касание отрезка
                    if (p[1].getY() == y)
                    {
                        for (Point po : list)  // проверяем следующий пункт пересечения. Важно знать вверх или вних повернёт след. отрезок.
                        {
                            int count = 0;
                            if (po.getX() == to4ki_peresecheniya.get(i + 1)[0])
                            {
                                p2[count] = po;
                                count++;
                            }
                        }
                        // случай 1) касаемся границы но не пересекаем её.

                        if (!in_figure && ((p[0].getY() > y && p2[1].getY() > y) || (p[0].getY() < y && p2[1].getY() < y)))
                        {
                            d[0] = to4ki_peresecheniya.get(i)[0];
                            d[1] = 0.;
                            boundaries.add(d);
                            d[0] = to4ki_peresecheniya.get(i + 1)[0];
                            d[1] = 1.;
                            boundaries.add(d);
                        }
                        // случай 2) касаемся границы и входим в фигуру.
                        if (!in_figure && ((p[0].getY() > y && p2[1].getY() < y) || (p[0].getY() < y && p2[1].getY() > y)))
                        {
                            d[0] = to4ki_peresecheniya.get(i)[0];
                            d[1] = 1.0;
                            boundaries.add(d);
                            in_figure = !in_figure;
                        }
                        // случай 3) касаемся границы и выходим из фигуры.
                        if (in_figure && ((p[0].getY() > y && p2[1].getY() < y) || (p[0].getY() < y && p2[1].getY() > y)))
                        {
                            d[0] = to4ki_peresecheniya.get(i + 1)[0];
                            d[1] = 0.0;
                            boundaries.add(d);
                            in_figure = !in_figure;
                        }
                        i++;
                    }
                }
                // пересечение отрезка
                if (to4ki_peresecheniya.get(i)[1] == 1)
                {
                    d[0] = to4ki_peresecheniya.get(i)[0];
                    if (in_figure)
                    {
                        d[1] = 1.0;
                        boundaries.add(d);
                        in_figure = !in_figure;
                    }
                    if (!in_figure)
                    {
                        d[1] = 0.0;
                        boundaries.add(d);
                        in_figure = !in_figure;
                    }
                }
            }
            // ТУТ ДОЛЖЕН БЫТЬ КОД ПО ПЕЧАТАНИЮ/ ДОБАВЛЕНИЮ ПУНКТОВ ВНУТРИ ГРАНИЦ

        }

    }

}
