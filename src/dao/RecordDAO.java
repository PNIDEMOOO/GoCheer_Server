package dao;

import entity.Record;
import entity.RecordPK;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    // TODO: 添加根据用户名获取用户查词历史的函数。按时间先后排列
}
