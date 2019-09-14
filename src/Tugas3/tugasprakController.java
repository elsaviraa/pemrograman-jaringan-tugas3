/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Elsa
 */
public class tugasprakController {
    private TugasPraktikum view;
    private List<Integer> list = new ArrayList<>();
 
     public tugasprakController(TugasPraktikum view) {
         this.view = view;
         
         this.view.getBtBaca().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                proses();
             }
         });
         
         this.view.getBtSimpan().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 save();
             }
         });
         
     }
     
     private void proses(){
         JFileChooser loadFile = view.getLoadFile();
         StyledDocument doc = view.getTxtPane().getStyledDocument();
         if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
             BufferedReader reader = null;
             try {
                 reader = new BufferedReader(new FileReader(loadFile.getSelectedFile()));
                 String data = null;
                 doc.insertString(0, "", null);
                 while ((data = reader.readLine()) != null) {
                     doc.insertString(doc.getLength(), data, null);
                     doc.insertString(doc.getLength(), "\n", null);
             }
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(tugasprakController.class.getName()).log(Level.SEVERE,null,ex);
             }catch (IOException | BadLocationException ex){
                 Logger.getLogger(tugasprakController.class.getName()).log(Level.SEVERE,null,ex);
             }finally{
                 if (reader != null) {
                     try {
                         reader.close();
                     } catch (IOException ex) {
                         Logger.getLogger(tugasprakController.class.getName()).log(Level.SEVERE,null,ex);
                     }
                 }
             }
         }
     }
     
     private void save(){
        JFileChooser loadFile = view.getLoadFile();
        if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
            //OutputStream os = null;
            BufferedWriter writer = null;
            try {
                String contents = view.getTxtPane().getText();
                if (contents != null && !contents.isEmpty()) {
                    writer = new BufferedWriter(new FileWriter(loadFile.getSelectedFile()));
                    writer.write(contents);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(tugasprakController.class.getName()).log(Level.SEVERE,null,ex);
            } catch (IOException ex){
                Logger.getLogger(tugasprakController.class.getName()).log(Level.SEVERE,null,ex);
            }finally{
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        view.getTxtPane().setText("");
                        JOptionPane.showMessageDialog(null,"Berhasil Disimpan","Success",JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        Logger.getLogger(tugasprakController.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }else{
                     JOptionPane.showMessageDialog(null,"Data Tidak Boleh Kosong!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
