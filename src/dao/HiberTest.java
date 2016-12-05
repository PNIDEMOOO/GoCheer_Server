package dao;

import entity.Record;
import entity.User;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Donggu on 2016/11/29.
 */
public class HiberTest{
    SessionFactory factory;

    protected void init() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            this.factory = new MetadataSources(registry).
                    buildMetadata().
                    buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            e.printStackTrace();
        }
    }

    void listUser(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
//            List users = session.createQuery("from Record ").list();
//            for(Iterator it = users.iterator(); it.hasNext();){
//                Record record = (Record) it.next();
//                System.out.println(record.getWord());
//            }
            Record record = new Record();
            record.setDatetime(Timestamp.valueOf(LocalDateTime.now()));
            record.setUser("lengyan");
            record.setWord("angry");
            session.save(record);
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    private static void testDAO(){
        Record record = new Record();
        record.setDatetime(Timestamp.valueOf(LocalDateTime.now()));
        record.setUser("lengyan");
        record.setWord("angry");

        RecordDAO.getInstance().save(record);
    }

    public static void main(String[] args) throws Exception{
//        HiberTest test = new HiberTest();
//        test.init();
//        test.listUser();
//        testDAO();
        User user = UserDAO.getInstance()
                            .findById("lengyan");
    }
}
