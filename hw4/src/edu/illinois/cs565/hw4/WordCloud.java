package edu.illinois.cs565.hw4;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

import com.epicdevs.wordcloud.image.CloudImageGenerator;
import com.epicdevs.wordcloud.words.StringProcessor;

public class WordCloud {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int PADDING = 30;

    public static final String TEXT = "english_test.txt";
    public static final String FILTER = "english_filtering.txt";

    public static void main(String[] args) throws IOException {
    	String s = null;
    	
    	for (String string : args) {
			s += string + " ";
		}
    	
        makeWordCloud(s, "saved.png");
    }

	public static void makeWordCloud(String s, String path) throws IOException {
		StringProcessor strProcessor = new StringProcessor(s, new HashSet<String>());
        CloudImageGenerator generator = new CloudImageGenerator(WIDTH, HEIGHT, PADDING);
        BufferedImage image = generator.generateImage(strProcessor, System.currentTimeMillis());
        
		File outputfile = new File(path);
        ImageIO.write(image, "png", outputfile);
	}

}
