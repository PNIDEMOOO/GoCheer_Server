package dao;

import entity.Record;
import entity.RecordPK;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Donggu on 2016/12/5.
 */
public class RecordDAO extends BaseDAO<Record> {
    private static RecordDAO instance = new RecordDAO();
    private RecordDAO(){}

    public static RecordDAO getInstance(){
        return instance;
    }

    public void delete(RecordPK pk) {
        super.delete(Record.class, pk);
    }

    public Record findById(RecordPK pk) {
        return super.findById(Record.class, pk);
    }

    /**
     * Get searching history of user.
     * @param username username
     * @return List of records in descend order.
     */
    public List getUserHistory(String username){
        Session session = getSession();
        Query query = session.createQuery("from Record where user = :username order by datetime desc");
        query.setParameter("username", username);
        List result = query.getResultList();
        session.close();
        return result;
    }

    public List getUserHistory(String username,String word)
    {
        Session session=getSession();
        Query query=session.createQuery("from Record  where user=:username AND word=:word order by datetime desc");
        query.setParameter("username",username);
        List result=query.getResultList();
        session.close();
        return result;
    }

}
