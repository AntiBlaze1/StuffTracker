package com.antiblaze;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import static com.antiblaze.Constants.dbPath;

public class AccessDatabase {
    private static HashSet<String> repoNames=new HashSet<String>();

    public static void getReposFromDB() {
        File reposListFile=new File(dbPath+"repos");
        try {
            BufferedReader br=new BufferedReader(new FileReader(reposListFile));
            String repo;
            while ((repo=br.readLine())!=null) {
                repoNames.add(repo.trim());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        for (String thing:repoNames) {
//            System.out.println(thing);
//        }
    }

    public static Repo getRepo(String path) {
        String name=path.substring(1).toLowerCase().trim();
        if (repoNames.contains(name)) {
            return new Repo(name);
        }
        return null;
    }
}
