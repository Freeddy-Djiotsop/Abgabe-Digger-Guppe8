/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digger.logik;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author ghait
 */
public class ResourceManager {
    public  void save(Serializable data, String fileName) throws Exception{
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
            System.out.println(Paths.get(fileName));
            oos.writeObject(data);
        }
    
    }
    
    public  Object load(String fileName)throws Exception{
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
    
            return ois.readObject();
    }
    
    }
    
}
