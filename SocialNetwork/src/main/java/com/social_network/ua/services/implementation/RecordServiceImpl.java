package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.RecordDao;
import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.Record;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 26.11.2016.
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordDao recordDao;

    @Override
    public void add(Record record) {
        recordDao.add(record);
    }

    @Override
    public void add(String text, String urlOfImage, Date dateOfRecord) {
        Record record = new Record(text,urlOfImage,dateOfRecord);
        recordDao.add(record);
    }

    @Override
    public void edit(long id, String text, String urlOfImage, Date dateOfRecord) {
        Record record = recordDao.findOne(id);
        record.setText(text); record.setUrl(urlOfImage); record.setDateOfRecord(dateOfRecord);
        recordDao.edit(record);
    }

    @Override
    public void edit(Record record) {
        recordDao.edit(record);
    }

    @Override
    public void delete(long id) {
        recordDao.delete(recordDao.findOne(id));
    }

    @Override
    public Record findOne(long id) {
        return recordDao.findOne(id);
    }

    @Override
    public List<Record> findAll() {
        return recordDao.findAll();
    }

    @Override
    public void updateUserImageSrcOfRecords(User user) {
        recordDao.updateUserImageSrcOfRecords(user);
    }

    @Override
    public List<Record> findAllByCommunity(Community community) {
        return recordDao.findAllByCommunity(community);
    }

    @Override
    public List<Record> findAllInTheWallOf(long id) {
        return recordDao.findAllInTheWallOf(id);
    }
}


