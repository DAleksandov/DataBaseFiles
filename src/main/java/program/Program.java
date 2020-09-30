package program;

import model.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class Program {

    public static void main(String[] args) {
        //generateTD();
        //Блок подготовки ТД без гемора
        //Сканнер чудит или я?
        /*Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter FIO");
        String name = scanner1.nextLine();*/
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        while (i != -1) {
            System.out.println("To Create DB enter 1");
            System.out.println("To Show DB enter 2");
            System.out.println("To show list DB enter 3");
            System.out.println("To exit enter -1");
            try {
                i = scanner.nextInt();
                switch (i) {
                    case 1:
                        createDB(scanner, i);
                        break;
                    case 2:
                        try {
                            showDB(scanner, i);
                        } catch (IOException e) {
                            System.out.println("DB is not exist");
                        }
                        break;
                    case 3:
                        showListDB(scanner, i);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Bad input type");
                scanner = new Scanner(System.in);
            }
        }
    }

    public static void generateTD() {
        String[] fNames = {"Dima", "Vasya", "Serioga", "Kirill", "Debil", "Vanil", "Jon", "Kane", "Grom", "Pogrom"};
        String[] sNames = {"Dimov", "Vasyov", "Seriogov", "Kirillov", "Debilov", "Vanilov", "Jonov", "Kanov", "Gromov", "Programmov"};
        String[] dNames = {"Matan", "Patan", "Vatan", "Fisika", "Huisika", "Durian", "Proectirovka", "Truba", "Neft", "Fisra"};
        ArrayList<Student> students = new ArrayList<>();
        for (int k = 0; k < 5; k++) {
            ArrayList<Discipline> disciplines = new ArrayList<>();
            for (int j = 0; j < (int) (Math.random() * 5 + 1); j++) {
                String a = RandomStringUtils.randomAlphabetic(10);
                Discipline discipline = new Discipline((int) (Math.random() * 100 + 1), (int) (Math.random() * 10 + 1), dNames[(int) (Math.random() * 9 + 1)]);
                disciplines.add(discipline);
            }
            Student student = new StudentPrivate(fNames[(int) (Math.random() * 9 + 1)] + sNames[(int) (Math.random() * 9 + 1)], RandomStringUtils.randomAlphabetic(3), (int) (Math.random() * 11 + 1), disciplines);
            students.add(student);
        }
        for (int k = 0; k < 5; k++) {
            ArrayList<Discipline> disciplines = new ArrayList<>();
            for (int j = 0; j < (int) (Math.random() * 5 + 1); j++) {
                String a = RandomStringUtils.randomAlphabetic(10);
                Discipline discipline = new Discipline((int) (Math.random() * 100 + 1), (int) (Math.random() * 9 + 1), dNames[(int) (Math.random() * 9 + 1)]);
                disciplines.add(discipline);
            }
            Student student = new StudentGeneral(fNames[(int) (Math.random() * 9 + 1)] + sNames[(int) (Math.random() * 9 + 1)], RandomStringUtils.randomAlphabetic(3), (int) (Math.random() * 11 + 1), disciplines);
            students.add(student);
        }
        System.out.println(students);
        try {
            new DB(Math.random() + "", students);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createDB(Scanner scanner, int i) {
        System.out.println("Select name");
        String name = scanner.next();
        try {
            DB db = new DB(name, true);
            System.out.println("DB created");
        } catch (FileAlreadyExistsException a) {
            System.out.println(a.getMessage());
        } catch (IOException e) {
        }
    }

    //проверка на существование такого файла
    public static void showDB(Scanner scanner, int i) throws IOException {
        System.out.println("Enter name DB");
        DB db = new DB(scanner.next(), false);
        ArrayList<Student> list = db.getListStudents();
        for (int l = 0; l < list.size(); l++) {
            System.out.println(list.get(l));
        }
        while (i != -1) {
            System.out.println("Add Sudents - 1");
            System.out.println("Delete Sudents - 2");
            System.out.println("Sort Sudents - 3");
            System.out.println("Get middle Score of students - 4");
            System.out.println("Search Student - 5");
            System.out.println("Get Sample - 6");
            System.out.println("Show Students - 7");
            System.out.println("Update DB - 8");
            System.out.println("Exit - -1");
            i = scanner.nextInt();
            if (i == 1) {
                int j = 0;
                while (j != 2) {
                    System.out.println();
                    db.addStudent(createStudent(scanner));
                    System.out.println("Do you want add new Student?");
                    System.out.println("1 - Yes, 2 - No");
                    j = scanner.nextInt();
                }
            }
            if (i == 2) {
                int q = 0;
                while (q != 1) {
                    System.out.println("Enter name");
                    scanner.nextLine();
                    String mn = scanner.nextLine();
                    System.out.println(db.deleteStudent(mn));
                    System.out.println("Continue delete? 2 - Yes, 1 - No");
                    q = scanner.nextInt();
                }
            }
            if (i == 3) {
                System.out.println("Alphabet - 1");
                System.out.println("Semester - 2");
                i = scanner.nextInt();
                SortTypeDB sortTypeDB = i == 1 ? SortTypeDB.ALPHABET : i == 2 ? SortTypeDB.SEMESTER : null;
                if (sortTypeDB == null) {
                    System.out.println("error");
                    return;
                }
                db.sort(sortTypeDB);
                list = db.getListStudents();
                System.out.println("Sorted DB:");
                for (int l = 0; l < list.size(); l++) {
                    System.out.println(list.get(l));
                }
                System.out.println();
            }
            if (i == 4) {
                LinkedHashMap<Student, Double> map = db.middleScore();
                for (Map.Entry<Student, Double> entry : map.entrySet()) {
                    System.out.printf(entry.getKey().getFIO() + " " + "%.2f", entry.getValue());
                    System.out.println();
                }
            }
            if (i == 5) {
                System.out.println("Enter FIO student");
                Student student = db.search(scanner.next());
                if (student != null) {
                    System.out.println(student);
                } else {
                    System.out.println("Student was not fount in this db");
                }
            }
            if (i == 6) {
                ArrayList<Student> sample = new ArrayList<>();
                System.out.println("Enter type of sample: Semester = 1, Name - 2");
                int type = scanner.nextInt();
                if (type == 1) {
                    System.out.println("Enter semester");
                    sample = db.getSample(scanner.nextInt());
                }
                if (type == 2) {
                    System.out.println("Enter name");
                    sample = db.getSample(scanner.next());
                }
                for (int j = 0; j < sample.size(); j++) {
                    System.out.println(sample.get(j));
                }
                System.out.println("Do you want save sample in new DB?\n 1 = Yes, 2 = No");
                type = scanner.nextInt();
                if (type == 1) {
                    System.out.println("Enter name of new DB");
                    String nameNewDB = scanner.next();
                    try {
                        new DB(nameNewDB, sample);
                        System.out.println("DB " + nameNewDB + " Created");
                    } catch (IOException e) {
                        System.out.println("Error");
                    }
                }
            }
            if (i == 7) {
                list = db.getListStudents();
                for (int l = 0; l < list.size(); l++) {
                    System.out.println(list.get(l));
                }
            }
            if (i == 8) {
                System.out.println("Do you want update DB? 1 - Yes, 2 - no");
                int end = scanner.nextInt();
                if (end == 1)
                    db.updateDB();
            }
        }
    }

    public static void showListDB(Scanner scanner, int i) {
        File file = new File("DB");
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("You need create DB");
            return;
        }
        for (int j = 0; j < files.length; j++) {
            System.out.println(files[j].getName());
        }
    }

    public static Student createStudent(Scanner scanner) {
        System.out.println("add last name");
        String ln = scanner.next();
        System.out.println("add first name");
        String fn = scanner.next();
        System.out.println("add mid name");
        String mn = scanner.next();
        String fio = ln + " " + fn + " " + mn;
        System.out.println("add chair");
        String chair = scanner.next();
        System.out.println("add sem");
        int sem = scanner.nextInt();
        ArrayList<Discipline> list = new ArrayList<>();
        System.out.println("Create disciplines");
        int j = 0;
        while (j != -1) {
            System.out.println("add point");
            int point = scanner.nextInt();
            System.out.println("add sem");
            int semester = scanner.nextInt();
            System.out.println("add name Discipline");
            String nameD = scanner.next();
            Discipline discipline = new Discipline(point, semester, nameD);
            list.add(discipline);
            System.out.println("-1 for end, 1 - continue add discipline");
            j = scanner.nextInt();
        }
        System.out.println("Type of Study: General - 1, Private - 2");
        int a = scanner.nextInt();
        Student student;
        if (a == 1) {
            student = new StudentGeneral(fio, chair, sem, list);
        } else {
            student = new StudentPrivate(fio, chair, sem, list);
        }
        return student;
    }
}
