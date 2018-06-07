/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLHandler;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author dotah
 */
public class XMLHandler {
    Element artists, artist, albuns, album, tracks, track;
    File file = new File("artists.xml");
    Document doc = null;
    List<Artist> artistList;
    List<Album> albumList;
    List<Track> trackList;
    
    public boolean searchFile(){
        return file.exists();
    }
    
    public void openFile(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse("artists.xml");
            artists = (Element) doc.getElementsByTagName("artists").item(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void startFile(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            artists = doc.createElement("artists");
            doc.appendChild(artists);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean saveFile(){        
        try {
            XMLSerializer serializer;
            serializer = new XMLSerializer(
                    new FileOutputStream("artists.xml"), new OutputFormat(doc, "iso-8859-1", true));
            serializer.serialize(doc);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public boolean addArtist(String name, String genre){
        
        return true;
    }
    
    public boolean addAlbum(String artist, String name, String year){
        
        return true;
    }
    
    public boolean addTrack(String artist, String album, String name, String duration){
        
        return true;
    }
    
    public boolean removeArtist(String name){
        
        return true;
    }
    
    public boolean removeAlbum(String artist, String name){
        
        return true;
    }
    
    public boolean removeTrack(String artist, String album, String name){
        
        return true;
    }
    
    public List searchByArtist(String name){
        
        return artistList;
    }
    
    public List searchByAlbum(String name){
        
        return albumList;
    }
    
    public List searchByTrack(String name){
        
        return trackList;
    }
}
