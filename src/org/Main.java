package org;

import java.awt.*;
import javax.swing.*;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Main extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private TextArea area;

    private JMenuBar mb;

    private JMenu mFile;
    private JMenu mEdit;
    private JMenu mView;
    private JMenu mHelp;

    private JMenuItem novii;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem exit;

    private JMenuItem cut;
    private JMenuItem copy;
    private JMenuItem paste;
    private JMenuItem delete;
    private JMenuItem select_all;

    /*private JRadioButtonMenuItem cpUTF8;
    private JRadioButtonMenuItem cp1251;
    private JRadioButtonMenuItem cp866;
    private JRadioButtonMenuItem cpkoi8r;*/

    private JMenuItem about;

    private String encoding = "UTF-8";
    private String buf = "";

    public Main() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        area = new TextArea();
        add(area);

        mb = new JMenuBar();

        mFile = new JMenu("Файл");

        novii = new JMenuItem("Создать");
        open = new JMenuItem("Открыть");
        save = new JMenuItem("Сохранить");
        exit = new JMenuItem("Выход");

        novii.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);

        mFile.add(novii);
        mFile.add(open);
        mFile.add(save);
        mFile.addSeparator();
        mFile.add(exit);

        mb.add(mFile);

        mEdit = new JMenu("Редактирование");

        cut = new JMenuItem("Вырезать");
        copy = new JMenuItem("Копировать");
        paste = new JMenuItem("Вставить");
        delete = new JMenuItem("Удалить");
        select_all = new JMenuItem("Выделить все");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        delete.addActionListener(this);
        select_all.addActionListener(this);

        mEdit.add(cut);
        mEdit.add(copy);
        mEdit.add(paste);
        mEdit.add(delete);
        mEdit.add(select_all);
        mb.add(mEdit);


        setJMenuBar(mb);
        pack();
    }



    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == novii) {
            novii();
        } else if (source == open) {
            openFile();
        } else if (source == save) {
            saveFile();
        } else if (source == exit) {
            exit();
        } else if (source == cut) {
            cut();
        } else if (source == copy) {
            copy();
        }else if (source == paste) {
            paste();
        }else if (source == delete) {
            delete();
        }else if (source == select_all) {
            select_all();
        }
    }


    private void novii() {
        area.setText("");
    }
    private void exit() {
        System.exit(0);
    }

    private void cut() {
        buf = area.getSelectedText();
        area.replaceRange ("", area.getSelectionStart(), area.getSelectionEnd());
    }
    private void copy() {
        buf = area.getSelectedText();
    }
    private void paste() {
        area.replaceRange (buf, area.getSelectionStart(), area.getSelectionEnd());
    }
    private void delete() {
        area.replaceRange ("", area.getSelectionStart(), area.getSelectionEnd());
    }
    private void select_all() {
        area.selectAll();
    }


    private void saveFile() {
        FileDialog fd = new FileDialog(this, " Сохранить как", FileDialog.SAVE);
        fd.setVisible(true);
        String path = fd.getDirectory();
        if (path == null) {
            return;
        }
        path += fd.getFile();
        byte[] buf;
        OutputStream os = null;
        try {
            buf = area.getText().getBytes(encoding);
            os = new FileOutputStream(path);
            os.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openFile() {
        FileDialog fd = new FileDialog(this, " Открыть как", FileDialog.LOAD);
        fd.setVisible(true);
        String path = fd.getDirectory();
        if (path == null) {
            return;
        }
        path += fd.getFile();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
            for (int ch = br.read(); ch != -1; ch = br.read()) {
                sb.append((char) ch);
            }
            area.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e){}
                Frame frame = new Main();
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
