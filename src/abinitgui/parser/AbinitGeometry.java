/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */
package abinitgui.parser;

import abinitgui.pseudos.Atom;
import abinitgui.core.MainFrame;
import abinitgui.core.Utils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.sourceforge.jeval.EvaluationException;

public class AbinitGeometry {

    // Fixed sized tables
    public Double acell[] = null;
    public Double scalecart[] = null;
    public Double angdeg[] = null;
    public Double rprim[][] = null;
    public Double rprimd[][] = null;
    // Varying size table
    public int natom = 0;
    public Double znucl[] = null; // znucl(ntypat)
    public int ntypat = 0;
    public Integer typat[] = null; // typat(natom)
    public Double xangst[][] = null; // xangst(natom,3)
    public Double xred[][] = null; // xred(natom,3)
    public Double xcart[][] = null; // xcart(natom,3)
    public Double allpositions[][] = null; // allpositions(allatoms,3)
    public int allatoms;
    public Double allznucl[] = null; // allznucl(allatoms)
    public final List<String> allNames = Arrays.asList("acell", "angdeg", "natom",
            "ntypat", "rprim", "rprimd", "scalecart", "typat", "xangst", "xred",
            "xcart", "znucl");
    protected static final double ANGSTROMPERBOHR = 0.529177249f;

    @Override
    public String toString() {
        String s;
        s = "acell = " + Utils.printArray(acell) + "\n"
                + "angdeg = " + Utils.printArray(angdeg) + "\n"
                + "natom = " + Utils.printArray(natom) + "\n"
                + "ntypat = " + Utils.printArray(ntypat) + "\n"
                + "rprim = " + Utils.printArray(rprim) + "\n"
                + "rprimd = " + Utils.printArray(rprimd) + "\n"
                + "scalecart = " + Utils.printArray(scalecart) + "\n"
                + "typat = " + Utils.printArray(typat) + "\n"
                + "xangst = " + Utils.printArray(xangst) + "\n"
                + "xred = " + Utils.printArray(xred) + "\n"
                + "xcart = " + Utils.printArray(xcart) + "\n"
                + "znucl = " + Utils.printArray(znucl) + "\n";

        return s;
    }

    /**
     * Sets the in-memory state from an AbinitInputMapping
     * @param map The mapping representing the input file
     * @param idtset The current dataset
     * @throws EvaluationException If something goes wrong
     */
    public void loadData(AbinitInputMapping map, int idtset) throws EvaluationException {

        Object o2 = map.getVariableValue("natom",idtset);
        if (o2 != null) {
            this.natom = (int) (o2);
        }

        o2 = map.getVariableValue("ntypat",idtset);
        if (o2 != null) {
            this.ntypat = (int) (o2);
        }

        o2 = map.getVariableValue("typat",idtset);
        if (o2 != null) {
            this.typat = (Integer[]) (o2);
        }

        o2 = map.getVariableValue("znucl",idtset);
        if (o2 != null) {
            this.znucl = (Double[]) (o2);
        }

        o2 = map.getVariableValue("xred",idtset);
        if (o2 != null) {
            this.xred = (Double[][]) (o2);
        }

        o2 = map.getVariableValue("xcart",idtset);
        if (o2 != null) {
            this.xcart = (Double[][]) (o2);
        }

        o2 = map.getVariableValue("rprim",idtset);
        if (o2 != null) {
            this.rprim = (Double[][]) (o2);
        }

        o2 = map.getVariableValue("rprimd",idtset);
        if (o2 != null) {
            this.rprimd = (Double[][]) (o2);
        }

        o2 = map.getVariableValue("scalecart",idtset);
        if (o2 != null) {
            this.scalecart = (Double[]) (o2);
        }

        o2 = map.getVariableValue("xangst",idtset);
        if (o2 != null) {
            this.xangst = (Double[][]) (o2);
        }

        o2 = map.getVariableValue("acell",idtset);
        if (o2 != null) {
            this.acell = (Double[]) (o2);
        }

        o2 = map.getVariableValue("angdeg",idtset);
        if (o2 != null) {
            this.angdeg = (Double[]) (o2);
        }

    }

    /**
     * Tries to complete information from the already known states
     * Should be called after @loadData
     * @return true if everything went fine
     */
    public boolean fillData() {
        DecimalFormat df_rprim = new DecimalFormat("#0.0000000000000");

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df_rprim.setDecimalFormatSymbols(dfs);

        if (rprim != null && rprimd != null) {
            MainFrame.printERR("Rprim & Rprimd are not null.");
            return false;
        }

        if ((xred != null && !Utils.areZero(xred)))
        if (((xred != null && !Utils.areZero(xred)) && (xcart != null && !Utils.areZero(xcart)))
                || ((xred != null && !Utils.areZero(xred)) && (xangst != null && !Utils.areZero(xangst)))
                || ((xcart != null && !Utils.areZero(xcart)) && (xangst != null && !Utils.areZero(xangst)))) {
            MainFrame.printERR("Different positions assigned at the same time.");
            return false;
        }

        if (rprimd == null) {
            rprimd = new Double[3][3];
            if (acell == null) {
                acell = new Double[3];
                acell[0] = 1 * ANGSTROMPERBOHR;    //in angstrom    
                acell[1] = 1 * ANGSTROMPERBOHR;
                acell[2] = 1 * ANGSTROMPERBOHR;
            }
            if (angdeg == null && rprim == null) {
                angdeg = new Double[3];
                angdeg[0] = 90D;
                angdeg[1] = 90D;
                angdeg[2] = 90D;
            } else if (angdeg == null) {
                angdeg = new Double[3];

                double dot23 = 0;
                double dot13 = 0;
                double dot12 = 0;

                for (int j = 0; j < 3; j++) {
                    dot23 += rprim[1][j] * rprim[2][j];
                    dot13 += rprim[0][j] * rprim[2][j];
                    dot12 += rprim[0][j] * rprim[1][j];
                }

                angdeg[0] = 180 / Math.PI * Math.acos(dot23);
                angdeg[1] = 180 / Math.PI * Math.acos(dot13);
                angdeg[2] = 180 / Math.PI * Math.acos(dot12);
            }

            if (rprim == null || (Utils.isUnity(rprim) && angdeg != null)) {
                rprim = new Double[3][3];
                Double angdeg1 = angdeg[0]; // angdeg(1)
                Double angdeg2 = angdeg[1]; // angdeg(2)
                Double angdeg3 = angdeg[2]; // angdeg(3)

                Double rprim11;
                Double rprim12;
                Double rprim13;
                Double rprim21;
                Double rprim22;
                Double rprim23;
                Double rprim31;
                Double rprim32;
                Double rprim33;

                if (angdeg1.equals(angdeg2) && angdeg1.equals(angdeg3) && !angdeg1.equals(90.0)) {
                    Double cosang = Math.cos(Math.PI * angdeg1 / 180.0);
                    Double a2 = 2.0 / 3.0 * (1.0 - cosang);
                    Double aa = Math.sqrt(a2);
                    Double cc = Math.sqrt(1.0 - a2);
                    rprim11 = aa;
                    rprim21 = 0.0;
                    rprim31 = cc;
                    rprim12 = -0.5 * aa;
                    rprim22 = Math.sqrt(3.0) * 0.5 * aa;
                    rprim32 = cc;
                    rprim13 = -0.5 * aa;
                    rprim23 = -Math.sqrt(3.0) * 0.5 * aa;
                    rprim33 = cc;
                } else {
                    rprim11 = new Double(1.0); // rprim(1,1) OK
                    rprim12 = new Double(Math.cos(Math.PI * angdeg3 / 180.0)); // rprim(1,2) OK
                    rprim13 = new Double(Math.cos(Math.PI * angdeg2 / 180.0)); // rprim(1,3) OK

                    rprim21 = new Double(0.0); // rprim(2,1)
                    rprim22 = new Double(Math.sin(Math.PI * angdeg3 / 180.0)); // rprim(2,2) OK
                    rprim23 = new Double(((Math.cos(Math.PI * angdeg1 / 180.0)
                            - rprim12 * rprim13) / rprim22)); // rprim(2,3) OK

                    rprim31 = new Double(0.0); // rprim(3,1)
                    rprim32 = new Double(0.0); // rprim(3,2)
                    rprim33 = new Double(Math.sqrt(1.0 - Math.pow(rprim13, 2)
                            - Math.pow(rprim23, 2))); // rprim(3,3) OK
                }

                rprim[0][0] = new Double(df_rprim.format(rprim11));
                rprim[0][1] = new Double(df_rprim.format(rprim12));
                rprim[0][2] = new Double(df_rprim.format(rprim13));

                rprim[1][0] = new Double(df_rprim.format(rprim21));
                rprim[1][1] = new Double(df_rprim.format(rprim22));
                rprim[1][2] = new Double(df_rprim.format(rprim23));

                rprim[2][0] = new Double(df_rprim.format(rprim31));
                rprim[2][1] = new Double(df_rprim.format(rprim32));
                rprim[2][2] = new Double(df_rprim.format(rprim33));
            }

            if (scalecart == null) {
                scalecart = new Double[3];
                scalecart[0] = 1D;
                scalecart[1] = 1D;
                scalecart[2] = 1D;
            }

            // Compute rprimd !
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check order of indices
                    rprimd[i][j] = scalecart[i] * rprim[i][j] * acell[j] * ANGSTROMPERBOHR;
                }
            }
        }

        if (xangst == null) {
            xangst = new Double[3][natom];
            if (xred != null) {
                for (int i = 0; i < natom; i++) {
                    for (int j = 0; j < 3; j++) {

                        xangst[j][i] = 0.0D;
                        for (int k = 0; k < 3; k++) {
                            // Should check order of indices
                            xangst[j][i] = xangst[j][i] + rprimd[j][k] * xred[k][i];
                        }
                    }
                }
            }
            if (xcart != null) {
                for (int i = 0; i < natom; i++) {
                    for (int j = 0; j < 3; j++) {
                        xangst[j][i] = xcart[j][i] * ANGSTROMPERBOHR;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Compute the replica's, the positions and the structure
     * @param nbX Number of replica along the first axis
     * @param nbY Number of replica along the second axis
     * @param nbZ Number of replica along the third axis
     */
    public void computeReplicas(int nbX, int nbY, int nbZ) {
        allatoms = natom * nbX * nbY * nbZ;

        allpositions = new Double[allatoms][3];
        allznucl = new Double[allatoms];

        System.out.println(this);
        for (int i = 0; i < natom; i++) {
            for (int x = 0; x < nbX; x++) {
                for (int y = 0; y < nbY; y++) {
                    for (int z = 0; z < nbZ; z++) {
                        int atomindex = (((x * nbY) + y) * nbZ + z) * natom + i;
                        for (int j = 0; j < 3; j++) {
                            allznucl[atomindex] = znucl[typat[i] - 1];
                            allpositions[atomindex][j] =
                                    x * rprimd[j][0] + y * rprimd[j][1] + z * rprimd[j][2] + xangst[j][i];
                        }
                    }
                }
            }
        }
    }

    /**
     * Writes the structure to a XYZ file
     * @param fileName Name of the file where to write
     * @throws IOException If something goes wrong when writing the file
     */
    public void writeIntoXYZ(String fileName) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        pw.println("" + allatoms);
        String unitcell = "jmolscript: load \"\" packed unitcell {" + acell[0] + " "
                + acell[1] + " " + acell[2] + " " + angdeg[0] + " " + angdeg[1] + " " + angdeg[2] + "}";

        pw.println(unitcell);
        for (int i = 0; i < allatoms; i++) {
            String s = "" + Atom.getSymbolByZnucl((int) (Math.floor(allznucl[i])));

            for (int j = 0; j < 3; j++) {
                s = s + " " + allpositions[i][j];
            }

            pw.println(s);
        }

        pw.close();
    }

    /**
     * Writes the structure to a AIMS file (may contain the axis)
     * @param fileName Name of the file where to write
     * @throws IOException If something goes wrong when writing the file
     */
    public void writeIntoAIMS(String fileName) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

        for (int i = 0; i < 3; i++) {
            String latticevect = "lattice_vector\t";
            for (int j = 0; j < 3; j++) {
                latticevect += rprimd[j][i] + "\t";
            }
            pw.println(latticevect);
            MainFrame.printOUT("Writing inside " + fileName + " latticevect = "
                    + latticevect + ".");
        }

        for (int i = 0; i < allatoms; i++) {
            String s = "atom\t";

            for (int j = 0; j < 3; j++) {
                s = s + " " + allpositions[i][j] + "\t";
            }

            s = s + Atom.getSymbolByZnucl((int) (Math.floor(allznucl[i])));

            pw.println(s);
        }

        pw.close();
    }
}
