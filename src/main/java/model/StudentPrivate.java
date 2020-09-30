package model;

import java.util.ArrayList;
import java.util.Objects;

public class StudentPrivate extends Student {
    private String FIO;
    private String chair;
    private int semester;
    private ArrayList<Discipline> listDisciplines;

    public StudentPrivate(String FIO, String chair, int semester, ArrayList<Discipline> listDisciplines) {
        this.FIO = FIO;
        this.chair = chair;
        this.semester = semester;
        //забыл
        this.listDisciplines = new ArrayList<>(listDisciplines);
    }

    public String getFIO() {
        return FIO;
    }

    public String getChair() {
        return chair;
    }

    public int getSemester() {
        return semester;
    }

    public ArrayList<Discipline> getDisciplines() {
        return new ArrayList<>(listDisciplines);
    }

    @Override
    public double getAveragePoint() {
        double middle = 0;
        for (Discipline listDiscipline : listDisciplines) {
            middle += listDiscipline.getPoint();
        }
        return middle / listDisciplines.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentPrivate)) return false;
        StudentPrivate that = (StudentPrivate) o;
        return semester == that.semester &&
                Objects.equals(FIO, that.FIO) &&
                Objects.equals(chair, that.chair) &&
                Objects.equals(listDisciplines, that.listDisciplines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FIO, chair, semester, listDisciplines);
    }

    @Override
    public String toString() {
        return "StudentPrivate{" +
                "FIO='" + FIO + '\'' +
                ", chair='" + chair + '\'' +
                ", semester=" + semester +
                ", listDisciplines=" + listDisciplines +
                '}';
    }
}
