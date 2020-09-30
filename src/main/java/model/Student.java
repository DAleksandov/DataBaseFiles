package model;

import java.util.ArrayList;

/**
 * Реализовать виртуальный класс, содержащий чистые виртуальные функции, функционал которых различается для наследников.
 */

public abstract class Student {
    public abstract double getAveragePoint();

    public abstract String getFIO();

    public abstract String getChair() ;

    public abstract int getSemester() ;
}
