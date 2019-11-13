package org.auk.data;

import org.auk.models.Article;
import org.auk.utils.DbUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao implements BaseDao<Article> {

    public void save(Article article) {
        Transaction transaction = null;

        try (var sessionFactory = DbUtil.getSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(article);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.getStackTrace();
        }
    }

    @Override
    public void update(Article article) {

    }

    @Override
    public void delete(Article article) {

    }

    public Article findBySlug(String slug) {
        Article articleSlug = null;
        Transaction transaction = null;

        try (var session = DbUtil.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
            Query query = session.createQuery("from Article where slug = (:slug)");
            query.setParameter("slug", slug);
            articleSlug = (Article) query.getSingleResult();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return articleSlug;
    }
    public List<Article> getAll() {
        List<Article> articleList = new ArrayList<>();
        Transaction transaction = null;

        try (var session = DbUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            articleList = session.createQuery("from Article", Article.class).getResultList();//artice class mapping
            transaction.commit();
        } catch (HibernateException e) {

            e.printStackTrace();
        }

        return articleList;
    }

}
