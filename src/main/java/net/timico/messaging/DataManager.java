
package net.timico.messaging;

import java.util.List; 
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DataManager {
    private static SessionFactory factory; 
    
   @SuppressWarnings("deprecation")
   public void startHibernate() {
      try{
          
//         factory = new AnnotationConfiguration().
//                   configure().
//                   addAnnotatedClass(TurbineData.class).
//                   buildSessionFactory();
          
          Configuration config = new Configuration();
            config.configure();
            config.addAnnotatedClass(TurbineData.class);
            
            ServiceRegistry registry = new StandardServiceRegistryBuilder()
            .applySettings(config.getProperties())
            .build();
            
         factory = config.buildSessionFactory(registry);
         
      }catch (Throwable ex) { 
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex); 
      }

   }
   /* Method to CREATE an employee in the database */
   public void addTurbineData(TurbineData tbd){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         session.save(tbd); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   /* Method to  READ all the employees */
   public void listTurbineData( ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         List employees = session.createQuery("FROM Turbinedata").list(); 
         for (Iterator iterator = 
                           employees.iterator(); iterator.hasNext();){
            TurbineData employee = (TurbineData) iterator.next(); 
            System.out.print("Turbine ID: " + employee.getId()); 
            System.out.print("  Turbine String: " + employee.getStringData()); 
            System.out.println("  Datum: " + employee.getDatum()); 
         }
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   /* Method to UPDATE salary for an employee */
   public void updateTurbineData(Integer turbineID, String stringdata ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         TurbineData employee = 
                    (TurbineData)session.get(TurbineData.class, turbineID); 
         employee.setStringData( stringdata);
		 session.update(employee); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   /* Method to DELETE an employee from the records */
   public void deleteTurbineData(Integer TurbineID){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         TurbineData tbd = 
                   (TurbineData)session.get(TurbineData.class, TurbineID); 
         session.delete(tbd); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
    
    
    
    
    
    
    
}
