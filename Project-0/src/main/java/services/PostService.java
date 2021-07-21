package services;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import dao.FileIO;
import logging.Logging;
import models.Transactions;

public class PostService {
	
	private String file;
	private FileIO<Transactions> io;
	
	public PostService(String file) {
		this.file = file;
		this.io = new FileIO<Transactions>(file);
	}
	
	//Get all posts from the post file
	public List<Transactions> getAllPosts(){
		List<Transactions> pList;
		
		try {
			pList = io.readObject();
		} catch (FileNotFoundException e) {
			pList = new ArrayList<Transactions>();
		} catch (Exception e) {
			pList = null;
			e.printStackTrace();
		}
		
		return pList;
	}
	
	public void addPost(Transactions p) {
		ArrayList<Transactions> pList;
		
		try {
			pList = io.readObject();
		} catch(FileNotFoundException e) {
			pList = new ArrayList<Transactions>();
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		pList.add(p);
		io.writeObject(pList);
		Logging.logger.info("User: " + p.getUser() + " posted a new post");
	}
	
}
