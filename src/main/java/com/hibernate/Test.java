package com.hibernate;

import com.hibernate.entity.*;
import com.hibernate.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Test {

    public final static Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args) {
        try {
            createData();
//            queryUpdateData();
//            querySelect();
            queryCaching();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

//      session.eviect(entity) để xóa đối tượng khỏi cache
//      session.clear()

    public static void createData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            // create contact and customer
            Contact contact = new Contact("Dan Phuong", "0212154389", "quang_dp");
            Customer customer = new Customer("nguyen viet ngoc quang", 1, new Date(98, 10, 1), contact);
            session.save(contact);
            session.save(customer);

            // create category and product
            Category categoryCD = new Category("CD");
            Product product1 = new Product("Core Java", "Core Java", 100d, categoryCD);
            Product product2 = new Product("Spring for Beginners", "Spring for Beginners", 50d, categoryCD);
            Product product3 = new Product("Swift for Beginners", "Swift for Beginners", 120d, categoryCD);

            session.save(categoryCD);
            session.save(product1);
            session.save(product2);
            session.save(product3);

//        createByInverse();

            Category categoryDVD = new Category("DVD");
            Product product4 = new Product("Core Java", "Core Java", 100d, categoryDVD);
            Product product5 = new Product("Spring for Beginners", "Spring for Beginners", 50d, categoryDVD);
            Product product6 = new Product("Swift for Beginners", "Swift for Beginners", 120d, categoryDVD);
//            categoryDVD.setProducts(Arrays.asList(product4,product5,product6));
            session.save(categoryDVD);
            session.save(product4);
            session.save(product5);
            session.save(product6);

            Category categoryBook = new Category("Book");
            Product product7 = new Product("Core Java", "Core Java", 100d, categoryBook);
            Product product8 = new Product("Spring for Beginners", "Spring for Beginners", 50d, categoryBook);
            Product product9 = new Product("Swift for Beginners", "Swift for Beginners", 120d, categoryBook);
//            categoryDVD.setProducts(Arrays.asList(product4,product5,product6));
            session.save(categoryBook);
            session.save(product7);
            session.save(product8);
            session.save(product9);

            // create order and item
            Order order = new Order(customer, new Date(System.currentTimeMillis() - 86400 * 1000), new ArrayList<>());
            Item item1 = new Item(product1, 5, order, 0);
            Item item2 = new Item(product5, 3, order, 0);
            session.save(order);
            session.save(item1);
            session.save(item2);

            logger.info("successfully saved data");
            transaction.commit();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);


        } finally {
            session.close();
        }
    }

    public static void queryUpdateData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Order order = session.find(Order.class, new Integer(1));
            order.setOrderDate(new Date(System.currentTimeMillis()));
            session.update(order);

            Criteria criteria = session.createCriteria(Item.class);
            criteria.add(Restrictions.between("quantity", 2, 6));
            List<Item> items = (List<Item>) criteria.list();
            Item item1 = items.get(0);
//            item1.setOrder(null);
//            session.update(item1);
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
            logger.error(exception.getMessage(), exception);
        } finally {
            Order updatedOrder =session.find(Order.class, new Integer(1));
            System.err.println("orderDate: "+updatedOrder.getOrderDate());
            session.close();
        }
    }

    public static void querySelect() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.flush();
            session.clear();
            transaction.commit();
            Category category = session.find(Category.class, new Integer(1));
            System.err.println(category);
            Customer customer = session.find(Customer.class, new Integer(1));
            System.err.println(category);
            List<Category> list = session.createQuery("from Category ").list();
            for(Category category1 : list){
                List<Product> products = category1.getProducts();
                products =  products.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
                for (Iterator iter = products.iterator(); iter.hasNext(); ) {
                    Product product = (Product) iter.next();
                    System.out.println(product);
                }
            }
            session.close();
            for (Product product : category.getProducts()) {
                System.err.println(product);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }

    }

    public static void queryCaching(){
        System.err.println("Caching example");
        try (Session session1 = HibernateUtil.getSessionFactory().openSession();
             Session session2 = HibernateUtil.getSessionFactory().openSession();) {
            Category cat1_1st = session1.get(Category.class, new Integer(1));
            System.out.println("Session 1 at 1st time: " + cat1_1st.getName());
            Category cat1_2nd = session1.get(Category.class, new Integer(1));
            System.out.println("Session 1 at 2nd time: " + cat1_2nd.getName());
//            session1.evict(cat1_1st);
//            HibernateUtil.getSessionFactory().getCache().evictAll();

//            Category cat1_3nd = session1.get(Category.class, new Integer(1));
//            System.out.println("Session 1 cat 3: " + cat1_3nd.getName());

            Category cat2 = session2.get(Category.class, new Integer(1));
            System.out.println("Session 2: " + cat2.getName());
        }
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session1 = sessionFactory.openSession();) {

            String hql = " FROM Category cat WHERE cat.id = :id";
            Query<Category> query = session1.createQuery(hql, Category.class);
            query.setCacheable(true);
            query.setCacheRegion(Category.class.getCanonicalName());
            Category cat1 = query.setParameter("id", new Integer(1)).uniqueResult();
            System.out.println("cat1: " + cat1.getName());

            Category cat2 = query.setParameter("id", new Integer(1)).uniqueResult();
            System.out.println("cat2: " + cat2.getName());
        }
        System.err.println("Caching example");
    }
}
