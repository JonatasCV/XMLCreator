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
import org.w3c.dom.NodeList;

/**
 *
 * @author dotah
 */
public class XMLHandler {
    Element eArtists, eArtist, eName, eGenre, eAlbuns, eAlbum, eYear, eTracks, eTrack, eDuration;
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
            eArtists = (Element) doc.getElementsByTagName("artists").item(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void startFile(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            eArtists = doc.createElement("artists");
            doc.appendChild(eArtists);
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
        NodeList nList = doc.getElementsByTagName("artist");
        
        for(int i = 0; i < nList.getLength(); i++){
            if(nList.item(i).getChildNodes().item(1).getTextContent().equals(name)){
                return false;
            }
        }
        
        eArtist = doc.createElement("artist");
        
        eName = doc.createElement("name");
        eName.appendChild(doc.createTextNode(name));     
        eArtist.appendChild(eName);
        
        eGenre = doc.createElement("genre");
        eGenre.appendChild(doc.createTextNode(genre));        
        eArtist.appendChild(eGenre);
        
        eArtists.appendChild(eArtist);       
        
        return true;
    }
    
    public boolean addAlbum(String artist, String name, String year){
        NodeList nLista = doc.getElementsByTagName("album");
        
        for(int i = 0; i < nLista.getLength(); i++){            
            if(nLista.item(i).getChildNodes().item(1).getTextContent().equals(name)){
                return false;
            }
        }
        
        NodeList nList = doc.getElementsByTagName("artist");
        
        for(int i=0; i < nList.getLength(); i++){
            if(nList.item(i).getChildNodes().item(1).getTextContent().equals(artist)){                
                
                eAlbum = doc.createElement("album");
        
                eName = doc.createElement("name");
                eName.appendChild(doc.createTextNode(name));
                eName.setAttribute("artist", artist);
                eAlbum.appendChild(eName);

                eYear = doc.createElement("year");
                eYear.appendChild(doc.createTextNode(year));  
                eAlbum.appendChild(eYear);

                System.out.println(nLista.getLength());
                
                if(nLista.getLength() <= 0){
                   eAlbuns = doc.createElement("albuns");
                   eAlbuns.appendChild(eAlbum);
                   nList.item(i).appendChild(eAlbuns);
                }else {
                    nList.item(i).getChildNodes().item(5).appendChild(eAlbum);
                }
                
                break;
            }     
        }
        
        return true;
    }
    
    public boolean addTrack(String artist, String album, String name, String duration){
        NodeList nLista = doc.getElementsByTagName("track");
        
        for(int i = 0; i < nLista.getLength(); i++){            
            if(nLista.item(i).getChildNodes().item(1).getTextContent().equals(name)){
                return false;
            }
        }
        
        NodeList nList = doc.getElementsByTagName("artist");
        
        for(int i=0; i < nList.getLength(); i++){            
            if(nList.item(i).getChildNodes().item(1).getTextContent().equals(artist)){
                nList = nList.item(i).getChildNodes();
                
                for(int j = 0; j < nList.getLength(); j++){
                    if(nList.item(5).getChildNodes().item(1).getChildNodes().item(1).getTextContent().equals(album)){
                                               
                        eTrack = doc.createElement("track");

                        eName = doc.createElement("name");
                        eName.appendChild(doc.createTextNode(name));
                        eName.setAttribute("album", album);
                        eTrack.appendChild(eName);

                        eDuration = doc.createElement("duration");
                        eDuration.appendChild(doc.createTextNode(duration));
                        eTrack.appendChild(eDuration);

                        if(nLista.getLength() <= 0){
                            eTracks = doc.createElement("tracks");
                            eTracks.appendChild(eTrack);
                            nList.item(5).getChildNodes().item(1).appendChild(eTracks);
                        }else{
                            nList.item(5).getChildNodes().item(1).getChildNodes().item(5).appendChild(eTrack);
                        }                       
                        
                        break;
                    }
                }
                break;
            }
        }
        
        return true;
    }
    
    public boolean removeArtist(String name){
        NodeList nList = doc.getElementsByTagName("artist");
        
        for(int i = 0; i < nList.getLength(); i++){
            if(nList.item(i).getChildNodes().item(1).getTextContent().equals(name)){
                eArtists.removeChild(nList.item(i));
                return true;
            }
        }
        
        return false;
    }
    
    public boolean removeAlbum(String artist, String name){
        NodeList nList = doc.getElementsByTagName("album");
        
        for(int i = 0; i < nList.getLength(); i++){
            if(nList.item(i).getChildNodes().item(1).getTextContent().equals(name)){             
                nList.item(i).getParentNode().removeChild(nList.item(i));
                return true;
            }
        }
        
        return false;
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
