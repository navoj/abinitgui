/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MDandTB;

import core.MainFrame;
import core.RetMSG;
import core.Utils;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import projects.LocalMachine;
import projects.Machine;
import projects.Simulation;
import projects.SimulationPanel;

/**
 *
 * @author yannick
 */
public class ClustepPanel extends SimulationPanel {

    private MainFrame mf;
    
    /**
     * Creates new form ClustepPanel
     */
    public ClustepPanel() {
        initComponents();
    }
    
    public void setMainFrame(MainFrame mf)
    {
        this.mf = mf;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openClustepInputFileLabel = new javax.swing.JLabel();
        displayClustepInputFileButton = new javax.swing.JButton();
        geditClustepInputButton = new javax.swing.JButton();
        openClustepInputFileDialogButton = new javax.swing.JButton();
        openClustepInputFileTextField = new javax.swing.JTextField();
        openClustepPositionFileLabel = new javax.swing.JLabel();
        displayClustepPositionFileButton = new javax.swing.JButton();
        geditClustepPositionButton = new javax.swing.JButton();
        openClustepPositionFileDialogButton = new javax.swing.JButton();
        openClustepPositionFileTextField = new javax.swing.JTextField();
        sendClustepCheckBox = new javax.swing.JCheckBox();
        getEvolutionFileButton = new javax.swing.JButton();
        getFilmFileButton = new javax.swing.JButton();
        getLogFileButton = new javax.swing.JButton();

        openClustepInputFileLabel.setText("Open the Clustep input file");

        displayClustepInputFileButton.setText("Display");
        displayClustepInputFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayClustepInputFileButtonActionPerformed(evt);
            }
        });

        geditClustepInputButton.setText("Edit");
        geditClustepInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geditClustepInputButtonActionPerformed(evt);
            }
        });

        openClustepInputFileDialogButton.setText("...");
        openClustepInputFileDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openClustepInputFileDialogButtonActionPerformed(evt);
            }
        });

        openClustepPositionFileLabel.setText("Open the Clustep position file");

        displayClustepPositionFileButton.setText("Display");
        displayClustepPositionFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayClustepPositionFileButtonActionPerformed(evt);
            }
        });

        geditClustepPositionButton.setText("Edit");
        geditClustepPositionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geditClustepPositionButtonActionPerformed(evt);
            }
        });

        openClustepPositionFileDialogButton.setText("...");
        openClustepPositionFileDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openClustepPositionFileDialogButtonActionPerformed(evt);
            }
        });

        sendClustepCheckBox.setText("Send CLUSTEP source code and compile");

        getEvolutionFileButton.setText("Download Evolution File");
        getEvolutionFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getEvolutionFileButtonActionPerformed(evt);
            }
        });

        getFilmFileButton.setText("Download Film File");
        getFilmFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFilmFileButtonActionPerformed(evt);
            }
        });

        getLogFileButton.setText("Download log File");
        getLogFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getLogFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepInputFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(displayClustepInputFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geditClustepInputButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepPositionFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(displayClustepPositionFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geditClustepPositionButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepInputFileDialogButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openClustepInputFileTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepPositionFileDialogButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openClustepPositionFileTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sendClustepCheckBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(getEvolutionFileButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(getFilmFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(getLogFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 52, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepInputFileLabel)
                    .addComponent(geditClustepInputButton)
                    .addComponent(displayClustepInputFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepInputFileDialogButton)
                    .addComponent(openClustepInputFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepPositionFileLabel)
                    .addComponent(geditClustepPositionButton)
                    .addComponent(displayClustepPositionFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepPositionFileDialogButton)
                    .addComponent(openClustepPositionFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99)
                .addComponent(sendClustepCheckBox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getEvolutionFileButton)
                    .addComponent(getFilmFileButton)
                    .addComponent(getLogFileButton))
                .addGap(143, 143, 143))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void displayClustepInputFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayClustepInputFileButtonActionPerformed
        mf.clustepInputFileDisplayer.setVisible(true);
        // TODO : pour quand ce sera éditable
        //clustepInputFileDisplayer.setEditable(true);

        String fileContent = "";

        try {
            File file = new File(openClustepInputFileTextField.getText());

            FileInputStream fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            while (dis.available() != 0) {
                fileContent += dis.readLine() + "\n";
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            mf.printERR(e.getMessage());
        } catch (IOException e) {
            mf.printERR(e.getMessage());
        }

        mf.clustepInputFileDisplayer.setText(fileContent);
    }//GEN-LAST:event_displayClustepInputFileButtonActionPerformed

    @Override
    public void fillFromSimu(Simulation currentSimu)
    {
        if(currentSimu instanceof ClustepSimulation)
        {
            ClustepSimulation simu = (ClustepSimulation)currentSimu;
            openClustepInputFileTextField.setText(simu.getInputFileName());
            openClustepPositionFileTextField.setText(simu.getPositionFileName());
            sendClustepCheckBox.setSelected(simu.isSendClustepSources());
        }
        else
        {
            throw new UnsupportedOperationException("Unable to tackle simulation");
        }
    }
    
    @Override
    public void fillSimu(Simulation currentSimu)
    {
        if(currentSimu instanceof ClustepSimulation)
        {
            ClustepSimulation simu = (ClustepSimulation)currentSimu;
            simu.setInputFileName(openClustepInputFileTextField.getText());
            simu.setPositionFileName(openClustepPositionFileTextField.getText());
            simu.setSendClustepSources(sendClustepCheckBox.isSelected());
        }
        else
        {
            throw new UnsupportedOperationException("Unable to tackle simulation");
        }
    }
    
    private void geditClustepInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geditClustepInputButtonActionPerformed

        String fileName = openClustepInputFileTextField.getText();

        mf.editFile(fileName, true);
    }//GEN-LAST:event_geditClustepInputButtonActionPerformed

    private void openClustepInputFileDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openClustepInputFileDialogButtonActionPerformed
        JFileChooser fc = new JFileChooser(".");
        File currDir = new File(".");
        String currPath = currDir.getAbsolutePath();
        String basePath = basePath = currPath.replace("\\", "/").replace(".", "");
            mf.printDEB(basePath);
            fc.setMultiSelectionEnabled(false);

            int retValue = fc.showOpenDialog(this);
            if (retValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                    openClustepInputFileTextField.setText(relPath);
                }
    }//GEN-LAST:event_openClustepInputFileDialogButtonActionPerformed

    private void displayClustepPositionFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayClustepPositionFileButtonActionPerformed
        mf.clustepPositionFileDisplayer.setVisible(true);
        // TODO : pour quand ce sera éditable
        //clustepPositionFileDisplayer.setEditable(true);

        String fileContent = "";

        try {
            File file = new File(openClustepPositionFileTextField.getText());

            FileInputStream fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            while (dis.available() != 0) {
                fileContent += dis.readLine() + "\n";
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            mf.printERR(e.getMessage());
        } catch (IOException e) {
            mf.printERR(e.getMessage());
        }

        mf.clustepPositionFileDisplayer.setText(fileContent);
    }//GEN-LAST:event_displayClustepPositionFileButtonActionPerformed

    private void geditClustepPositionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geditClustepPositionButtonActionPerformed

        String fileName = openClustepPositionFileTextField.getText();

        mf.editFile(fileName, true);
    }//GEN-LAST:event_geditClustepPositionButtonActionPerformed

    private void openClustepPositionFileDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openClustepPositionFileDialogButtonActionPerformed
        JFileChooser fc = new JFileChooser(".");
        File currDir = new File(".");
        String currPath = currDir.getAbsolutePath();
        String basePath = basePath = currPath.replace("\\", "/").replace(".", "");
            mf.printDEB(basePath);
            fc.setMultiSelectionEnabled(false);

            int retValue = fc.showOpenDialog(this);
            if (retValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                    openClustepPositionFileTextField.setText(relPath);
                }
    }//GEN-LAST:event_openClustepPositionFileDialogButtonActionPerformed

    private void getLogFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getLogFileButtonActionPerformed

    }//GEN-LAST:event_getLogFileButtonActionPerformed

    private void getFilmFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getFilmFileButtonActionPerformed
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // TODO get machine from combobox
                Machine mach = new LocalMachine();

                if(!mach.isConnected())
                {
                    mach.connection(mf);
                }

                String rootPath = mach.getSimulationPath();
                if(rootPath == null || rootPath.isEmpty())
                {
                    rootPath = ".";
                }
                String clustepFolder = "clustep";

                String inputFile = "";
                String inputFileName = "";

                inputFile = openClustepInputFileTextField.getText();
                inputFileName = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

                // Test de l'existance de inputfile
                if (!Utils.exists(inputFile)) {
                    mf.printERR("The file " + inputFile + " doesn't exist !");
                    getEvolutionFileButton.setEnabled(true);
                    return;
                }

                String simName = null;
                if (inputFileName != null) {
                    if (!inputFileName.equals("")) {
                        int idx = inputFileName.indexOf('-');
                        if (idx > 0 && idx < inputFileName.length()) {
                            simName = inputFileName.substring(0, idx);
                        } else {
                            simName = inputFileName;
                        }
                    } else {
                        mf.printERR("inputFileName == \"\"");
                        return;
                    }
                } else {
                    mf.printERR("inputFileName == null");
                    return;
                }

                if (!inputFile.equals("")) {

                    String fileName = rootPath + "/" + clustepFolder + "/"
                    + simName + "/" + simName + "-film.xyz";

                    if (!Utils.exists(fileName)) {
                        // Réception (copie) du fichier d'output si celui-ci est distant
                        if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                            //                            if (Utils.osName().startsWith("Windows")) {
                                //                                sendCommand("unix2dos " + fileName);
                                //                            }
                            mach.getFile(fileName + " " + fileName, mf);
                            //                            if (Utils.osName().startsWith("Windows")) {
                                //                                sendCommand("dos2unix " + fileName);
                                //                            }
                            if (Utils.osName().startsWith("Windows")) {
                                Utils.unix2dos(new File(fileName));
                            }
                        }
                    } else {
                        mf.printOUT("File " + fileName + " exists in your local filetree!\n"
                            + "Please remove the local file before you download the new file version!");
                    }

                    // ****************************************************************************
                    // Tester l'existance du fichier
                    mf.editFile(fileName.replace("/./", "/"), false);

                    if (!Utils.exists(fileName)) {
                        mf.printERR("File " + fileName + " doesn't exist !");
                        return;
                    } else {
                        if (Utils.osName().equals("Linux")) {
                            mf.localCommand("java -jar Jmol.jar " + fileName.replace("/./", "/"));
                        } else if (Utils.osName().equals("Mac OS X")) {
                            mf.localCommand("java -jar Jmol.jar " + fileName.replace("/./", "/"));
                        } else {
                            mf.localCommand("java -jar Jmol.jar " + fileName.replace("/./", "/"));
                        }
                    }
                    // ****************************************************************************
                } else {
                    mf.printERR("Please setup the inputfile textfield !");
                    return;
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
    }//GEN-LAST:event_getFilmFileButtonActionPerformed

    private void getEvolutionFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getEvolutionFileButtonActionPerformed
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // TODO use combobox !
                Machine mach = new LocalMachine();
                if(!mach.isConnected())
                {
                    mach.connection(mf);
                }

                String rootPath = mach.getSimulationPath();
                if(rootPath == null || rootPath.isEmpty())
                {
                    rootPath = ".";
                }

                String clustepFolder = "clustep";

                String inputFile = "";
                String inputFileName = "";

                inputFile = openClustepInputFileTextField.getText();
                inputFileName = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

                // Test de l'existance de inputfile
                if (!Utils.exists(inputFile)) {
                    mf.printERR("The file " + inputFile + " doesn't exist !");
                    getEvolutionFileButton.setEnabled(true);
                    return;
                }

                String simName = null;
                if (inputFileName != null) {
                    if (!inputFileName.equals("")) {
                        int idx = inputFileName.indexOf('-');
                        if (idx > 0 && idx < inputFileName.length()) {
                            simName = inputFileName.substring(0, idx);
                        } else {
                            simName = inputFileName;
                        }
                    } else {
                        mf.printERR("inputFileName == \"\"");
                        return;
                    }
                } else {
                    mf.printERR("inputFileName == null");
                    return;
                }

                if (!inputFile.equals("")) {

                    String fileName = rootPath + "/" + clustepFolder + "/"
                    + simName + "/" + simName + "-evol.dat";

                    if (!Utils.exists(fileName)) {
                        // Réception (copie) du fichier d'output si celui-ci est distant
                        if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                            //                            if (Utils.osName().startsWith("Windows")) {
                                //                                sendCommand("unix2dos " + fileName);
                                //                            }
                            mach.getFile(fileName + " " + fileName, mf);
                            //                            if (Utils.osName().startsWith("Windows")) {
                                //                                sendCommand("dos2unix " + fileName);
                                //                            }
                            if (Utils.osName().startsWith("Windows")) {
                                Utils.unix2dos(new File(fileName));
                            }
                        }
                    } else {
                        mf.printOUT("File " + fileName + " exists in your local filetree!\n"
                            + "Please remove the local file before you download the new file version!");
                    }

                    // ****************************************************************************
                    // Tester l'existance du fichier
                    mf.editFile(fileName, false);
                    // ****************************************************************************
                } else {
                    mf.printERR("Please setup the inputfile textfield !");
                    return;
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
    }//GEN-LAST:event_getEvolutionFileButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displayClustepInputFileButton;
    private javax.swing.JButton displayClustepPositionFileButton;
    private javax.swing.JButton geditClustepInputButton;
    private javax.swing.JButton geditClustepPositionButton;
    private javax.swing.JButton getEvolutionFileButton;
    private javax.swing.JButton getFilmFileButton;
    private javax.swing.JButton getLogFileButton;
    private javax.swing.JButton openClustepInputFileDialogButton;
    private javax.swing.JLabel openClustepInputFileLabel;
    public javax.swing.JTextField openClustepInputFileTextField;
    private javax.swing.JButton openClustepPositionFileDialogButton;
    private javax.swing.JLabel openClustepPositionFileLabel;
    public javax.swing.JTextField openClustepPositionFileTextField;
    private javax.swing.JCheckBox sendClustepCheckBox;
    // End of variables declaration//GEN-END:variables
}
