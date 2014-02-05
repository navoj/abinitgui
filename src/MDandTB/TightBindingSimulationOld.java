/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions, and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions, and the disclaimer that follows
these conditions in the documentation and/or other materials
provided with the distribution.

3. The names of the author may not be used to endorse or promote
products derived from this software without specific prior written
permission.

In addition, we request (but do not require) that you include in the
end-user documentation provided with the redistribution and/or in the
software itself an acknowledgement equivalent to the following:
"This product includes software developed by the
Abinit Project (http://www.abinit.org/)."

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

For more information on the Abinit Project, please see
<http://www.abinit.org/>.
 */

package MDandTB;

import core.Atom;
import core.Exec;
import core.MainFrame;
import core.RetMSG;
import core.Utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import projects.LocalMachine;
import projects.Machine;
import projects.RemoteJob;
import projects.RemoteJob;
import projects.Simulation;
import projects.SubmissionScript;

public class TightBindingSimulationOld extends Simulation {

    public final static int TB_DOSKNEE1AND2 = 101, TB_DOSKNEE3 = 102, TB_BNDSTR = 103;
    
    private int nkpt;
    private int model;
    private String name = "default";
    private RemoteJob job;
    public static int RUNNING = 1;
    public static int FINISHED = 2;
    public static int READY = 0;
    private int status;
    private int L, M;

    public TightBindingSimulationOld() {
        status = READY;

        job = new RemoteJob();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
        //return "Simulation(name = " + name + "; fileName = " + tbenerFileName + ")";
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public RemoteJob getRemoteJob() {
        return job;
    }

    @Override
    public void setRemoteJob(RemoteJob job) {
        this.job = job;
    }

    void updateStatus() {
        job.updateStatus();
    }
    
    @Override
    public void createFileTree(Machine mach, MainFrame mf)
    {
        String path = mach.getSimulationPath();
        if (path.equals("")) {
            path = ".";
        }
        mach.createTree(path, mf);
        if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
        {
            mf.getLocalExec().createTree(path);
        }
        
    }
    
    @Override
    public boolean submit(MainFrame mf)
    {
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());

        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return false;
        }

        if(!mach.isConnected())
        {
            mach.connection(mf);
        }

        if (mach.getType() == Machine.LOCAL_MACHINE && Utils.osName().startsWith("Windows")) {
            mf.printERR("Please connect to a remote host before submitting a simulation on Windows platform !");
            return false;
        }

        this.createFileTree(mach, mf);

        String rootPath = mach.getSimulationPath();

        SubmissionScript script = getRemoteJob().getScript();

        String TBfolderL = "Tight-Binding";

        String cwd = "";

        String CMD = "pwd";

        RetMSG retmsg = mach.sendCommand(CMD, mf);
        if(retmsg.getRetCode() == RetMSG.SUCCES)
        {
            mf.printOUT("PWD: " + retmsg.getRetMSG());
            cwd = Utils.removeEndl(retmsg.getRetMSG());
        }
        else
        {
            mf.printERR("Not able to get working directory !");
            return false;
        }
        
        String simName = name.replace(" ", "_");
            
            
        String TBrootR = TBfolderL;
        String TBrootL = TBfolderL;

        // ***************************************************************
        // Creation du dossier Tight-Binding local
        mf.getLocalExec().mkdir(rootPath + "/" + TBfolderL);
        // ***************************************************************

        if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
        {
            mach.mkdir(rootPath + "/" + TBrootR, mf);
        }

        int option = 1;

        //**************

        String L_str = "3";
        String M_str = "3";
        try {
            L_str = "" + L;
            M_str = "" + M;
        } catch (Exception e) {
            mf.printDEB("L = 5, M = 5 (default taken!)");
            L_str = "5";
            M_str = "5";
        }
        script.setCDPart("cd "+rootPath + "/" + TBrootR);
        script.setAbinitPath("bash " + "run_TB.sh" + " " + L_str + " " + M_str + " " + getModel() + " " + getNkpt());
            
        script.setInputPath("/dev/null");
        script.setLogPath(simName + ".log");

        //script.setPreProcessPart("cd "+TBrootR);

        if (script.getSystem().equals("SGE")) {
            String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
            script.writeToFile(PBSfileName);
        } else if (script.getSystem().equals("SLURM")) {
            String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
            script.writeToFile(PBSfileName);
        } else if (script.getSystem().equals("Frontend")) {
            String SHfileName = rootPath + "/" + TBrootL + "/" + simName + ".sh";
            script.writeToFile(SHfileName);
        }

        if (script.getSystem().equals("SGE")) {
            String sgeSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
            String sgeSHFileR = rootPath + "/" + TBrootR + "/" + simName + ".SGE.sh";
            //String sgeSHFileR = TBrootR + "/" + simName + ".SGE.sh";
            // Envoie du fichier SGE
                
            if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {


                mach.putFile(sgeSHFile + " " + sgeSHFileR, mf);
                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + sgeSHFileR, mf);
                }
            }
            // lancement des commandes d'ex�cution de la simulation
            mach.sendCommand("qsub " + sgeSHFileR, mf);
        } else if (script.getSystem().equals("Frontend")) {
            String SHFile = rootPath + "/" + TBrootL + "/" + simName + ".sh";
            String SHFileR = rootPath + "/" + TBrootR + "/" + simName + ".sh";
            //String SHFileR = TBrootR + "/" + simName + ".sh";
            
            /*if (Utils.osName().startsWith("Windows")) {
             Utils.dos2unix(new File(SHFileR));
             }*/

            // Envoie du fichier BASH

                
            if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {
                
                mach.putFile(SHFile + " " + SHFileR, mf);
                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + SHFileR, mf);
                }
            }
            // lancement des commandes d'ex�cution de la simulation
            mach.sendCommand("bash "+SHFileR, mf);
        } else if (script.getSystem().equals("SLURM")) {
            String slurmSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
            String slurmSHFileR = rootPath + "/" + TBrootR + "/" + simName + ".SLURM.sh";
            //String slurmSHFileR = TBrootR + "/" + simName + ".SLURM.sh";
            
            // Envoie du fichier SGE
            
            if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {

                mach.putFile(slurmSHFile + " " + slurmSHFileR, mf);
                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + slurmSHFileR, mf);
                }
            }
            // lancement des commandes d'ex�cution de la simulation
            mach.sendCommand("sbatch " + slurmSHFileR, mf);
        }

        if (mach.getType() == Machine.LOCAL_MACHINE) {
            mf.printOUT("The simulation was submitted to the local Tight-Binding server.");
        } else {
            mf.printOUT("The simulation was submitted to the remote Tight-Binding server " + mach.getName());
        }
        mf.printDEB("The submission thread ended successfully! (Tight-Binding)");

        return true;
        
    }
    
    @Override
    public void downloadLog(MainFrame mf)
    {
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return;
        }
        
        if(!mach.isConnected())
        {
            mach.connection(mf);
        }
        
        String rootPath = mach.getSimulationPath();
        String TBrootL = rootPath + "/Tight-Binding";
        String TBrootR = TBrootL;
        
        String simName = name.replace(" ", "_");
        String logfileNameR = TBrootR + "/"+simName+".log";
        String logfileNameL = TBrootL + "/"+simName+".log";

        mach.getFile(logfileNameR + " " + logfileNameL, mf);
        if (Utils.osName().startsWith("Windows")) {
            Utils.unix2dos(new File(logfileNameL));
        }
        
        logfileNameR = TBrootR + "/log_"+L+"_"+M;
        logfileNameL = TBrootL + "/log_"+L+"_"+M;

        mach.getFile(logfileNameR + " " + logfileNameL, mf);
        if (Utils.osName().startsWith("Windows")) {
            Utils.unix2dos(new File(logfileNameL));
        }

        mf.editFile(logfileNameL.replace("/./", "/"), false);
    }
    
    @Override
    public void downloadOutput(MainFrame mf)
    {
        /**
        * Download result 1
        */
        
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return;
        }
        
        if(!mach.isConnected())
        {
            mach.connection(mf);
        }
        
        String TBrootR = "";
        String TBrootL = "";
        String rootPath = mach.getSimulationPath() + "/Tight-Binding";

            //**************

        String LMParams = "_" + L + "_" + M;
        
        //mach.sendCommand("cd "+rootPath,mf);
        //mach.sendCommand("pwd", mf);
        mach.sendCommand("cd "+rootPath+" ; pwd ; gnuplot gnuplot"+LMParams+".dat ; cd $HOME", mf);
        //mach.sendCommand("cd $HOME",mf);
        
        String fileName = rootPath + "/bands" + LMParams+".png"; 

        if (!Utils.exists(fileName)) {
            mach.getFile(fileName + " " + fileName, mf);
            if (Utils.osName().startsWith("Windows")) {
                Utils.unix2dos(new File(fileName));
            }
        } else {
            mf.printOUT("File " + fileName + " exists in your local filetree!\n"
                + "Please remove the local file before you download the new file version!");
        }
        
        fileName = rootPath + "/dos" + LMParams+".dat"; 

        if (!Utils.exists(fileName)) {
            mach.getFile(fileName + " " + fileName, mf);
            if (Utils.osName().startsWith("Windows")) {
                Utils.unix2dos(new File(fileName));
            }
        } else {
            mf.printOUT("File " + fileName + " exists in your local filetree!\n"
                + "Please remove the local file before you download the new file version!");
        }
        
        fileName = rootPath + "/nt" + LMParams+".aims"; 

        if (!Utils.exists(fileName)) {
            mach.getFile(fileName + " " + fileName, mf);
            if (Utils.osName().startsWith("Windows")) {
                Utils.unix2dos(new File(fileName));
            }
        } else {
            mf.printOUT("File " + fileName + " exists in your local filetree!\n"
                + "Please remove the local file before you download the new file version!");
        }
        
        // ****************************************************************************
        // Tester l'existance du fichier
        if (!Utils.exists(fileName)) {
            mf.printERR("Output file is not existing !");
        } else {
            mf.editFile(fileName.replace("/./", "/"), false);
            mf.getLocalExec().sendCommand("java -jar Jmol.jar "+fileName);
        }
        
    }

    /**
     * @return the L
     */
    public int getL() {
        return L;
    }

    /**
     * @param L the L to set
     */
    public void setL(int L) {
        this.L = L;
    }

    /**
     * @return the M
     */
    public int getM() {
        return M;
    }

    /**
     * @param M the M to set
     */
    public void setM(int M) {
        this.M = M;
    }

    /**
     * @return the nkpt
     */
    public int getNkpt() {
        return nkpt;
    }

    /**
     * @param nkpt the nkpt to set
     */
    public void setNkpt(int nkpt) {
        this.nkpt = nkpt;
    }

    /**
     * @return the model
     */
    public int getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(int model) {
        this.model = model;
    }

    
    
}