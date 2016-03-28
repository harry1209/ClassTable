package com.yarin.mangoclasstable;

import java.io.File;
import java.io.FilenameFilter;

public class MusicFilter implements FilenameFilter {

	@Override
	public boolean accept(File arg0, String arg1) {
		// TODO Auto-generated method stub
		return (arg1.endsWith(".mp3"));
	}

}
