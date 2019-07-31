package forgefuck.team.xenobyte.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;

public class FileProvider extends File {

    private static int openerIndent = 350;
    private static File lastOpenedFile;
    private OutputStreamWriter linear;
    private long timeStamp;
    
    public FileProvider(File file) {
        super(file.getPath());
    }
    
    public FileProvider(File file, boolean lines) {
        this(file);
        try {
            linear = new OutputStreamWriter(new FileOutputStream(this), StandardCharsets.UTF_8);
        } catch (IOException e) {}
    }
    
    public String readFile() {
        try {
            return FileUtils.readFileToString(this, StandardCharsets.UTF_8).replaceAll("\\s+", " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        timeStamp = lastModified();
        return "";
    }
    
    public void writeFile(String in) {
        try {
            FileUtils.writeStringToFile(this, in, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeLine(String line) {
        try {
            linear.write(line);
            linear.flush();
        } catch (Exception e) {}
    }
    
    public boolean checkTimeStamp() {
        long newTimeStamp = new File(getPath()).lastModified();
        if (timeStamp != newTimeStamp) {
            readFile();
            return true;
        }
        return false;
    }
    
    public static File openFileDialog(String message, String filter) {
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        JFileChooser fileopen = new JFileChooser(lastOpenedFile);
        fileopen.setFileFilter(new FileFilter() {
            @Override public boolean accept(File file) {
                if(file != null) {
                    if (file.isDirectory()) {
                        return true;
                    }
                    return file.getName().endsWith(filter);
                }
                return false;
            }
            @Override public String getDescription() {
                return "*." + filter;
            }
            
        });
        fileopen.setAcceptAllFileFilterUsed(false);
        fileopen.setMultiSelectionEnabled(false);
        fileopen.setPreferredSize(new Dimension(scr.width - openerIndent, scr.height - openerIndent));
        if (fileopen.showDialog(null, message + " *" + filter) == JFileChooser.APPROVE_OPTION) {
            File openedFile = fileopen.getSelectedFile();
            lastOpenedFile = openedFile;
            return openedFile;
        }
        return null;
    }

}