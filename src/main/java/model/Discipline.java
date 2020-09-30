package model;

import java.util.Objects;

public class Discipline {
    private int point;
    private int semester;
    private String name;

    public Discipline(int point, int semester, String name) {
        this.point = point;
        this.semester = semester;
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public int getSemester() {
        return semester;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discipline)) return false;
        Discipline that = (Discipline) o;
        return point == that.point &&
                semester == that.semester &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, semester, name);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "point=" + point +
                ", semester=" + semester +
                ", name='" + name + '\'' +
                '}';
    }
}
