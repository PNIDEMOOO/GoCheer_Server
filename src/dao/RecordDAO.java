package dao;

import entity.Record;
import entity.RecordPK;

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
}