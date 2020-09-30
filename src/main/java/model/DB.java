package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class DB {
    private String name;
    private ArrayList<Student> listStudents;

    public DB(String name, ArrayList<Student> listStudents) throws IOException {
        this.name = "" + name;
        File file = new File("DB\\" + name);
        file.mkdirs();
        this.listStudents = listStudents;
        updateDB();
    }

    public DB(String name, boolean isNew) throws IOException {
        this.name = name;
        File file = new File("DB\\" + name);
        file.mkdirs();
        this.listStudents = new ArrayList<>();
        if (!isNew)
            load();
        else {
            File file1 = new File("DB\\" + name + "\\StudentsGen.json");
            File file2 = new File("DB\\" + name + "\\StudentsPr.json");
            if (file1.exists() || file2.exists()) {
                throw new FileAlreadyExistsException("DB already exist");
            }
            file1.createNewFile();
            file2.createNewFile();
        }
    }

    //Можно вынести в метод
    //Закрыть потоки
    private void load() throws IOException {
        readerLoad(true);
        readerLoad(false);
    }

    private void readerLoad(boolean isPrivate) throws IOException {
        Gson gson = new Gson();
        ArrayList<StudentPrivate> listP = new ArrayList<>();
        ArrayList<StudentGeneral> listG = new ArrayList<>();

        String nameFile = isPrivate ? "StudentsPr.json" : "StudentsGen.json";
        TypeToken typeToken = isPrivate ? new TypeToken<ArrayList<StudentPrivate>>() {
        } : new TypeToken<ArrayList<StudentGeneral>>() {
        };
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("DB\\" + name + "\\" + nameFile)))) {
            if (isPrivate)
                listP = gson.fromJson(bufferedReader, typeToken.getType());
            else
                listG = gson.fromJson(bufferedReader, typeToken.getType());
        }
        if (listG != null)
            this.listStudents.addAll(listG);
        if (listP != null)
            this.listStudents.addAll(listP);
    }

    //Второй метод сделать приватный
    public void updateDB() throws IOException {
        ArrayList<Student> list = new ArrayList<>();
        ArrayList<Student> list1 = new ArrayList<>();
        for (int i = 0; i < this.listStudents.size(); i++) {
            Student student = this.listStudents.get(i);
            if (student.getClass() == StudentGeneral.class)
                list.add(student);
            if (student.getClass() == StudentPrivate.class)
                list1.add(student);
        }
        writerUpdate(false, list);
        writerUpdate(true, list1);
    }

    private void writerUpdate(boolean isPrivate, ArrayList<Student> list) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String nameFile = isPrivate ? "StudentsPr.json" : "StudentsGen.json";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("DB\\" + name + "\\" + nameFile)))) {
            gson.toJson(list, bufferedWriter);
        }
    }

    public LinkedHashMap<Student, Double> middleScore() {
        LinkedHashMap<Student, Double> map = new LinkedHashMap<>();
        for (int i = 0; i < this.listStudents.size(); i++) {
            if (!map.containsKey(listStudents.get(i)))
                map.put(listStudents.get(i), listStudents.get(i).getAveragePoint());
        }
        return map;
    }

    public boolean addStudent(Student student) {
        return this.listStudents.add(student);
    }

    public boolean deleteStudent(Student student) {
        return this.listStudents.remove(student);
    }

    public boolean deleteStudent(String fio) {
        Student search = search(fio);
        return search != null && this.listStudents.remove(search);
    }

    public ArrayList<Student> getListStudents() {
        return new ArrayList<>(listStudents);
    }

    public Student search(String fio) {
        for (int i = 0; i < this.listStudents.size(); i++) {
            if (this.listStudents.get(i).getFIO().equals(fio)) {
                return this.listStudents.get(i);
            }
        }
        return null;
    }

    public void sort(SortTypeDB type) {
        if (type == SortTypeDB.ALPHABET) {
            this.listStudents.sort(Comparator.comparing(Student::getFIO));
        }
        if (type == SortTypeDB.SEMESTER) {
            this.listStudents.sort(Comparator.comparingInt(Student::getSemester));
        }
    }

    public ArrayList<Student> getSample(int semester) {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < this.listStudents.size(); i++) {
            if (this.listStudents.get(i).getSemester() == semester)
                list.add(this.listStudents.get(i));
        }
        return list;
    }

    //Можно вынести в отдельные методы поиск по лекциям студентов для оптимизации+
    public ArrayList<Student> getSample(String nameDiscipline) {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < this.listStudents.size(); i++) {
            Student student = this.listStudents.get(i);
            if (student.getClass() == StudentPrivate.class) {
                if (searchStudentPr(student, nameDiscipline))
                    list.add(student);
            }
            if (student.getClass() == StudentGeneral.class) {
                if (searchStudentGen(student, nameDiscipline))
                    list.add(student);
            }
        }
        return list;
    }

    private boolean searchStudentGen(Student student, String nameDiscipline) {
        StudentGeneral st = (StudentGeneral) student;
        Map<Integer, ArrayList<Discipline>> map = st.getListDisciplines();
        for (Map.Entry<Integer, ArrayList<Discipline>> entry : map.entrySet()) {
            for (int j = 0; j < entry.getValue().size(); j++) {
                if (entry.getValue().get(j).getName().equals(nameDiscipline)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean searchStudentPr(Student student, String nameDiscipline) {
        StudentPrivate st = (StudentPrivate) student;
        for (int j = 0; j < st.getDisciplines().size(); j++) {
            if (st.getDisciplines().get(j).getName().equals(nameDiscipline)) {
                return true;
            }
        }
        return false;
    }

    public boolean delete() {
        File studentsGen = new File("DB\\" + this.name + "\\StudentsGen.json");
        File studentsPr = new File("DB\\" + this.name + "\\StudentsPr.json");
        return studentsGen.delete() && studentsPr.delete();
    }
}
