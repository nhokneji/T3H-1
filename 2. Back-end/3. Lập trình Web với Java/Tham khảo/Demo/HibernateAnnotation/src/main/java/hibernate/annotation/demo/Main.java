package hibernate.annotation.demo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate.annotation.demo.entities.User;
import hibernate.annotation.demo.utility.HibernateUtil;
public class Main {

	public static void main(String[] args) {
		Main main = new Main();

		Integer user1 = main.addUser("tuan", "Ali");
		Integer user2 = main.addUser("ngoc", "Das");

		/* List down all the Users */
		main.listUsers();

		/* Update User's records */
//		main.updateUser(1, "t3h");

		/* Delete an User from the database */
		main.deleteUser(user2);

		/* List down new list of the Users */
		main.listUsers();
	}

	/* Method to CREATE an User in the database */
	public Integer addUser(String username, String pass) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		Integer userId = null;

		try {
			tx = session.beginTransaction();
			User user = new User(username, pass);
			userId = (Integer) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userId;
	}

	public void listUsers() {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List users = session.createQuery("FROM User").list();
			for (Object object : users) {
				User userElement = (User) object;
				System.out.print("Username: " + userElement.getUsername());
				System.out.print("  Pass: " + userElement.getPass());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to UPDATE salary for an User */
	public void updateUser(Integer id, String pass) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User userUpdate = (User) session.get(User.class, id);
			userUpdate.setPass(pass);
			session.update(userUpdate);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to DELETE an User from the records */
	public void deleteUser(Integer UserID) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, UserID);
			session.delete(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
