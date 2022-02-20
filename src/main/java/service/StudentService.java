package service;

import entity.Student;
import repository.StudentDao;

import java.util.List;

public class StudentService {

    StudentDao studentDao = new StudentDao();

    public List<Student> getListStudent() {
        return studentDao.getAll();
    }

    public Student findID(int id) {
        return studentDao.findById(id);
    }

    public List<Student> search(String name, String hometown, String gender, String classname, String major,List<Float> mark, List<Integer> birthday){
        return studentDao.search(name, hometown, gender, classname, major, mark, birthday);
    }

    public boolean insert(Student student) {
        List<Student> students = studentDao.getAll();
        students.sort((o1, o2) -> o1.getId() < o2.getId() ? 1 : -1);
        int id = students.get(0).getId() + 1;

        student.setId(id);
        if (student.getFullName() == null) {
            return false;
        }
        return studentDao.insert(student);
    }

    public boolean update(Student student){
        return studentDao.update(student);
    }

    public boolean removeStudent(int id) {
        return studentDao.removeStudent(id);
    }

    public List<Student> birthday(){
        return studentDao.birthday();
    }

}
