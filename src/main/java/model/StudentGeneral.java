package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentGeneral extends Student {
    private String FIO;
    private String chair;
    private int semester;
    private Map<Integer, ArrayList<Discipline>> listDisciplines;

    public StudentGeneral(String FIO, String chair, int semester, ArrayList<Discipline> listDisciplines) {
        this.FIO = FIO;
        this.chair = chair;
        this.semester = semester;
        this.listDisciplines = sort(listDisciplines);
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

    public Map<Integer, ArrayList<Discipline>> getListDisciplines() {
        return new HashMap<>(listDisciplines);
    }

    public Map<Integer, ArrayList<Discipline>> sort(ArrayList<Discipline> list) {
        Map<Integer, ArrayList<Discipline>> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if (!map.containsKey(list.get(i).getSemester()))
                map.put(list.get(i).getSemester(), new ArrayList<>());
            map.get(list.get(i).getSemester()).add(list.get(i));
        }
        return map;
    }

    @Override
    public double getAveragePoint() {
        double average = 0;
        int count = 0;
        for (HashMap.Entry<Integer, ArrayList<Discipline>> entry : this.listDisciplines.entrySet()) {
            ArrayList<Discipline> list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                average += list.get(i).getPoint();
            }
            count += list.size();
        }
        return average / count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentGeneral)) return false;
        StudentGeneral that = (StudentGeneral) o;
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
        return "StudentGeneral{" +
                "FIO='" + FIO + '\'' +
                ", chair='" + chair + '\'' +
                ", semester=" + semester +
                ", listDisciplines=" + listDisciplines +
                '}';
    }
}
