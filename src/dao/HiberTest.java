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
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import javax.persistence.Query;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * Created by Donggu on 2016/11/29.
 */
public class HiberTest {
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
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    void listUser() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Record record = new Record();
            record.setDatetime(Timestamp.valueOf(LocalDateTime.now()));
            record.setUser("lengyan");
            record.setWord("angry");
            session.save(record);
            tx.commit();
        } catch (HibernateError e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    private static void testDAO() {
        Record record = new Record();
        record.setDatetime(Timestamp.valueOf(LocalDateTime.now()));
        record.setUser("lengyan");
        record.setWord("angry");

        RecordDAO.getInstance().save(record);
    }

    private void testSQL() {
        Query query = factory.openSession().createQuery("select :param1 from User where username=:param2");
        query.setParameter("param1", "*");
        query.setParameter("param2", "lengyan");
        for (Iterator it = query.getResultList().iterator(); it.hasNext(); ) {
            User user = (User) it.next();
            System.out.println(user.getUsername());
        }
    }

    private static void testJDBC() {
        try {
            //加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
            e.printStackTrace();
        }

        String url = "jdbc:mysql://10.60.42.203:8888/db_1452764";
        String username = "S_1452764";
        String password = "NFGdCnWC";
        Connection conn;
        try{
            conn =DriverManager.getConnection(url,username,password);
            PreparedStatement sql = conn.prepareStatement(
                    "INSERT INTO Flight(flight_number, origin_airport_code, destination_airport_code)\n" +
                    "VALUEs (?,?,?);");
            sql.setInt(1,123);
            sql.setInt(2,1);
            sql.setInt(3,2);
            sql.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        HiberTest test = new HiberTest();
//        test.init();
//        test.listUser();
//        testDAO();
        testJDBC();
    }
}
