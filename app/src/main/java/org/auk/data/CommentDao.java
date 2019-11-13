package org.auk.data;

import org.auk.models.Comment;
import org.auk.utils.DbUtil;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CommentDao implements BaseDao {
    @Override
    public void save(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public List<Comment> getAll() {
        List<Comment> articleList = new ArrayList<>();
        Transaction transaction = null;

        try (var session = DbUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            articleList = session.createQuery("from Comment", Comment.class).getResultList();//artice class mapping
            transaction.commit();
        } catch (HibernateException e) {

            e.printStackTrace();
        }

        return articleList;
    }
}
