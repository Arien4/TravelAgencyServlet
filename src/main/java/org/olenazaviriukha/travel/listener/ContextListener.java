package org.olenazaviriukha.travel.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // lazy initializing DBHelper
//        ServletContext sc = event.getServletContext();
//
//        String url = sc.getInitParameter("url");
//        String user_name = sc.getInitParameter("user_name");
//        String password = sc.getInitParameter("password");
//        String database = sc.getInitParameter("database");
//        //Database db = new Database(url + database, user_name, password);
//        //System.out.println("in the listener!!");
//        DBHelper db = DBHelper.getInstance();
//        sc.setAttribute("db", db);

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
//        DBHelper.getInstance().shutdown();
    }


}
