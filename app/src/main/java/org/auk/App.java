package org.auk;
import org.auk.net.DetyraServer;
import org.auk.utils.DbUtil;

import java.io.IOException;


 // Detyra JAVA 2019


public class App {

    public static void main(String[] args) {

        DbUtil.getSessionFactory().getCurrentSession();
        try {
            new DetyraServer(DetyraServer.ServerType.SPARK);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
