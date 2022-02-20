package repository;

import entity.Student;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class StudentDao {

    Logger logger = Logger.getLogger(StudentDao.class);

    public List<Student> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student").list();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public Student findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("select s from Student s where s.id = :p_student_id");
            query.setParameter("p_student_id", id);
            return query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public boolean insert(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean update(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean removeStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Student student = session.load(Student.class, id);
            session.delete(student);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error(e);
        } finally {
            session.close();
        }
        return false;
    }

    public List<Student> search(String name, String hometown, String gender, String classname, String major, List<Float> mark, List<Integer> birthday){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "select s from Student s where";

            if (name != null){
                sql = sql + " s.fullName like to_char(concat(concat('%', :p_student_name), '%'))";
            }

            if(hometown != null){
                sql = sql + " s.hometown like (:p_student_hometown)";
            }

            if(gender != null){
                sql = sql + " s.gender like (:p_student_gender)";
            }

            if(classname != null){
                sql = sql + " s.className like (:p_student_className)";
            }

            if (major != null){
                sql = sql + " s.major like (:p_student_major)";
            }

            if(mark.size() == 2){
                sql = sql + " s.averageMark between (:p_student_mark_min) and (:p_student_mark_max)";
            }

            if(birthday.size() == 2){
                sql = sql + " to_number(to_char(s.birthday, 'dd')) between (:p_student_birthday1) and (:p_student_birthday2)";
            }

            Query<Student> query = session.createQuery(sql);

            if (name != null){
                query.setParameter("p_student_name",name);
            }

            if(hometown != null){
                query.setParameter("p_student_hometown", hometown);
            }

            if(gender != null){
                query.setParameter("p_student_gender", gender);
            }

            if(classname != null){
                query.setParameter("p_student_className", classname);
            }

            if (major != null){
                query.setParameter("p_student_major", major);
            }

            if(mark.size() == 2){
                query.setParameter("p_student_mark_min", mark.get(0));
                query.setParameter("p_student_mark_max", mark.get(1));
            }

            if(birthday.size() == 2){
                query.setParameter("p_student_birthday1", birthday.get(0));
                query.setParameter("p_student_birthday2", birthday.get(1));
            }
            List<Student> students = query.list();
            session.getTransaction().commit();
            return  students;

        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public List<Student> birthday(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LocalDate today = LocalDate.now();
            int month = today.getMonthValue();
            int day = today.getDayOfMonth();
            Query<Student> query = session.createQuery("from Student where to_number(to_char(birthday, 'MM')) = :p_month and to_number(to_char(birthday, 'dd')) = :p_day");
            query.setParameter("p_month", month);
            query.setParameter("p_day", day);
            List<Student> students = query.getResultList();
            session.getTransaction().commit();
            return students;
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

}
