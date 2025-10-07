/*
Name: Noah Carmichael
Description: The main functions of the computer simulator. Includes multiple apps that can be opened and closed, uses .txt files
to simulate HDD memory. Apps: Search Browser, Notepad (a plain text file), Settings, Antivirus scanner and sofrware, user manual.
Power button can be used to shut down computer at any time
 */
package com.mycompany.fpt;

import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import static java.lang.System.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.Color;


public class Main extends javax.swing.JFrame {

    // these need to be longs to handle the millis numbers
    private long lastClickTime = 0;
    private long lastClickTime2 = 0;
    private long lastClickTime3 = 0;
    private long lastClickTime4 = 0;
    private long lastClickTime5 = 0;
    public boolean virus = false;
    public String saveText;
    public String wallpaperPath = "";
    public Color PURPLE = new Color(255, 102, 102);

    public Main() {

        initComponents();
        
        // get and display date
        date.setText(java.time.LocalDate.now().toString());

        // colour scheme remembering from memory (a text file)
        String myColour = "White";
        try {
            FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\colourScheme.txt");
            BufferedReader br = new BufferedReader(fr);
            myColour = br.readLine();
        } catch (IOException e) {
            out.println("file not found");
        }
        if (myColour.equals("Red")) {
            browser.setForeground(Color.RED);
            notepad.setForeground(Color.RED);
            antivirus.setForeground(Color.RED);
            settings.setForeground(Color.RED);
            manual.setForeground(Color.RED);
            date.setForeground(Color.RED);
        } else if (myColour.equals("White")) {
            browser.setForeground(Color.WHITE);
            notepad.setForeground(Color.WHITE);
            antivirus.setForeground(Color.WHITE);
            settings.setForeground(Color.WHITE);
            manual.setForeground(Color.WHITE);
            date.setForeground(Color.WHITE);
        } else if (myColour.equals("Black")) {
            browser.setForeground(Color.BLACK);
            notepad.setForeground(Color.BLACK);
            antivirus.setForeground(Color.BLACK);
            settings.setForeground(Color.BLACK);
            manual.setForeground(Color.BLACK);
            date.setForeground(Color.BLACK);
        } else if (myColour.equals("Orange")) {
            browser.setForeground(Color.ORANGE);
            notepad.setForeground(Color.ORANGE);
            antivirus.setForeground(Color.ORANGE);
            settings.setForeground(Color.ORANGE);
            manual.setForeground(Color.ORANGE);
            date.setForeground(Color.ORANGE);
        } else if (myColour.equals("Purple")) {
            browser.setForeground(PURPLE);
            notepad.setForeground(PURPLE);
            antivirus.setForeground(PURPLE);
            settings.setForeground(PURPLE);
            manual.setForeground(PURPLE);
            date.setForeground(PURPLE);
        } else if (myColour.equals("Green")) {
            browser.setForeground(Color.GREEN);
            notepad.setForeground(Color.GREEN);
            antivirus.setForeground(Color.GREEN);
            settings.setForeground(Color.GREEN);
            manual.setForeground(Color.GREEN);
            date.setForeground(Color.GREEN);
        } else if (myColour.equals("Blue")) {
            browser.setForeground(Color.BLUE);
            notepad.setForeground(Color.BLUE);
            antivirus.setForeground(Color.BLUE);
            settings.setForeground(Color.BLUE);
            manual.setForeground(Color.BLUE);
            date.setForeground(Color.BLUE);
        }

        // wallpaper remembering from memory
        try {
            FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\wallpaperPath.txt");
            BufferedReader br = new BufferedReader(fr);
            wallpaperPath = br.readLine();
        } catch (IOException e) {
            out.println("file not found");
        }

        if (!wallpaperPath.equals("")) {
            File imageFile = new File(wallpaperPath);
            try {
                // get the image and resize it to fit the background
                ImageIcon originalIcon = new ImageIcon(ImageIO.read(imageFile));
                Image originalImage = originalIcon.getImage();

                // check if image is valid
                if (originalImage == null) {
                    JOptionPane.showMessageDialog(Main.this, "The selected file is not a valid image", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    // resize the image to the dimensions of the desktop
                    int newWidth = 800;
                    int newHeight = 450;
                    Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    background.setIcon(resizedIcon);
                    // center wallpaper
                    background.setHorizontalAlignment(JLabel.CENTER);
                    background.setVerticalAlignment(JLabel.CENTER);
                }

            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }

        }

        // check if computer is infected with virus
        String virusStatus = "false";
        try {
            FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\virusStatus.txt");
            BufferedReader br = new BufferedReader(fr);
            virusStatus = br.readLine();
        } catch (IOException e) {
            out.println("file not found");
        }
        if (virusStatus.equals("true")) {
            virus = true;
        }

        // if virus is true, change the wallpaper to be infected
        if (virus == true) {
            File virusWall = new File("src\\main\\resources\\graphics\\infectedWallpaper1.png");
            try {
                ImageIcon infectedIcon = new ImageIcon(ImageIO.read(virusWall));
                background.setIcon(infectedIcon);
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        
        // remember previous app locations on desktop
        try {
            FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\appLocations.txt");
            BufferedReader br = new BufferedReader(fr);
            
            // each line corresponds to an app's coords
            String browserLoc = br.readLine();
            String notepadLoc = br.readLine();
            String settingsLoc = br.readLine();
            String antivirusLoc = br.readLine();
            String manualLoc = br.readLine();
            
            // store x and y values from file as string, then convert them to ints and set app location
            String[] broswerArray = browserLoc.split(" ");
            browser.setLocation(Integer.parseInt(broswerArray[0]), Integer.parseInt(broswerArray[1]));
            String[] notepadArray = notepadLoc.split(" ");
            notepad.setLocation(Integer.parseInt(notepadArray[0]), Integer.parseInt(notepadArray[1]));
            String[] settingsArray = settingsLoc.split(" ");
            settings.setLocation(Integer.parseInt(settingsArray[0]), Integer.parseInt(settingsArray[1]));
            String[] antivirusArray = antivirusLoc.split(" ");
            antivirus.setLocation(Integer.parseInt(antivirusArray[0]), Integer.parseInt(antivirusArray[1]));
            String[] manualArray = manualLoc.split(" ");
            manual.setLocation(Integer.parseInt(manualArray[0]), Integer.parseInt(manualArray[1]));
            
        } catch (IOException e) {
            out.println("file not found");
        }

        // change the position of app names
        browser.setVerticalTextPosition(SwingConstants.BOTTOM);
        browser.setHorizontalTextPosition(SwingConstants.CENTER);
        notepad.setVerticalTextPosition(SwingConstants.BOTTOM);
        notepad.setHorizontalTextPosition(SwingConstants.CENTER);
        settings.setVerticalTextPosition(SwingConstants.BOTTOM);
        settings.setHorizontalTextPosition(SwingConstants.CENTER);
        antivirus.setVerticalTextPosition(SwingConstants.BOTTOM);
        antivirus.setHorizontalTextPosition(SwingConstants.CENTER);
        manual.setVerticalTextPosition(SwingConstants.BOTTOM);
        manual.setHorizontalTextPosition(SwingConstants.CENTER);

        // dragable desktop icons + sword
        makeDraggable(browser, this);
        makeDraggable(notepad, this);
        makeDraggable(settings, this);
        makeDraggable(antivirus, this);
        makeDraggable(manual, this);
        makeDraggable(sword, this);

        // add listners to detect for double click
        browser.addMouseListener(new DoubleClickListener());
        notepad.addMouseListener(new FileDoubleClickListener());
        settings.addMouseListener(new SettingsDoubleClickListener());
        antivirus.addMouseListener(new AntivirusDoubleClickListener());
        manual.addMouseListener(new ManualDoubleClickListener());
        Waterfox.setVisible(false);
        myFile.setVisible(false);
        appSettings.setVisible(false);
        appAntivirus.setVisible(false);
        appManual.setVisible(false);
        setLocation(50, 50);

        // listen for when apps are closed
        Waterfox.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                // reset double click time
                lastClickTime = 0;
            }
        });
        myFile.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {

                if (myFile.isVisible() == false && virus == false) {
                    // don't save text
                    if (saveFileText().equals("no")) {
                        fileContent.setText(saveText);

                        // save text
                    } else {

                        // reset double click time
                        lastClickTime2 = 0;
                        try {
                            FileWriter fw = new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\myFile.txt");
                            PrintWriter pw = new PrintWriter(fw);
                            // this is a solution to get the text from the text area line by line
                            String lines[] = fileContent.getText().split("\\r?\\n");

                            // for every line in the box, copy it to the file (and prevent starting on second line in a blank file)
                            for (int i = 0; i < lines.length; i++) {
                                pw.print(lines[i]);
                                if (i < lines.length - 1) {
                                    pw.println();
                                }
                            }
                            pw.close();
                            fw.close();

                        } catch (IOException p) {
                            System.out.println("ERROR");
                        }
                    }
                }
            }
        });
        appSettings.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                // reset double click time
                lastClickTime3 = 0;
            }
        });
        appAntivirus.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                // reset double click time
                lastClickTime4 = 0;

            }
        });
        appManual.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                // reset double click time
                lastClickTime4 = 0;

            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainDesktopPane = new javax.swing.JDesktopPane();
        appManual = new javax.swing.JInternalFrame();
        customWallpaperLabel1 = new javax.swing.JLabel();
        manualScrollPane = new javax.swing.JScrollPane();
        manualTextPane = new javax.swing.JTextPane();
        appSettings = new javax.swing.JInternalFrame();
        usernameLabel = new javax.swing.JLabel();
        settingsVirus = new javax.swing.JLabel();
        wallpaper = new javax.swing.JButton();
        resetWallpaper = new javax.swing.JButton();
        colourScheme = new javax.swing.JComboBox<>();
        customWallpaperLabel = new javax.swing.JLabel();
        desktopThemesLabel = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        passwordText = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordReset = new javax.swing.JButton();
        usernameReset = new javax.swing.JButton();
        appAntivirus = new javax.swing.JInternalFrame();
        allGood = new javax.swing.JLabel();
        eradicated = new javax.swing.JLabel();
        antivirusOK = new javax.swing.JButton();
        virusScan = new javax.swing.JButton();
        loadingText = new javax.swing.JLabel();
        theVirus1 = new javax.swing.JLabel();
        theVirus2 = new javax.swing.JLabel();
        theVirus3 = new javax.swing.JLabel();
        theVirus4 = new javax.swing.JLabel();
        sword = new javax.swing.JLabel();
        detectedText = new javax.swing.JTextPane();
        confetti = new javax.swing.JLabel();
        myFile = new javax.swing.JInternalFrame();
        textField = new javax.swing.JScrollPane();
        fileContent = new javax.swing.JTextArea();
        infectedTextField = new javax.swing.JScrollPane();
        fileContent1 = new javax.swing.JTextArea();
        Waterfox = new javax.swing.JInternalFrame();
        browserEnter = new javax.swing.JButton();
        browserEnter2 = new javax.swing.JButton();
        searchBar = new javax.swing.JTextField();
        suggestions = new javax.swing.JLabel();
        browserTitle = new javax.swing.JLabel();
        searchBar2 = new javax.swing.JTextField();
        downloadVirus = new javax.swing.JButton();
        appleResult = new javax.swing.JLabel();
        freeRAM = new javax.swing.JLabel();
        blueResult = new javax.swing.JLabel();
        fishResult = new javax.swing.JLabel();
        noResults = new javax.swing.JLabel();
        home = new javax.swing.JButton();
        virusResult = new javax.swing.JLabel();
        whiteBroswer = new javax.swing.JPanel();
        virusFrame = new javax.swing.JInternalFrame();
        greenBack = new javax.swing.JPanel();
        virusMessage = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        power = new javax.swing.JButton();
        taskbarManual = new javax.swing.JButton();
        taskbarNotepad = new javax.swing.JButton();
        taskbarBrowser = new javax.swing.JButton();
        taskbarSettings = new javax.swing.JButton();
        taskbarAntivirus = new javax.swing.JButton();
        browser = new javax.swing.JButton();
        notepad = new javax.swing.JButton();
        manual = new javax.swing.JButton();
        settings = new javax.swing.JButton();
        antivirus = new javax.swing.JButton();
        taskbarPanel = new javax.swing.JPanel();
        date = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 450));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        appManual.setBackground(new java.awt.Color(255, 255, 255));
        appManual.setClosable(true);
        appManual.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        appManual.setMaximizable(true);
        appManual.setResizable(true);
        appManual.setTitle("User Manual");
        appManual.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/bindowsSmall1.png"))); // NOI18N
        appManual.setVisible(false);
        appManual.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        customWallpaperLabel1.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        customWallpaperLabel1.setText("Welcome to Bindows 10.5, the ultimate computer system");
        appManual.getContentPane().add(customWallpaperLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 450, 20));

        manualTextPane.setEditable(false);
        manualTextPane.setFont(new java.awt.Font("Gill Sans MT", 0, 14)); // NOI18N
        manualTextPane.setText("This is a normal computer, so many features may be similar to those of a Windows 10/11 computer:\nDouble click on apps on the desktop to open them\nClick and drag apps on the desktop to move them around\nSingle click on apps in the taskbar to open them\nYou can drag, resize, and close app windows\nCertain things will be stored in the computer's memory so that it will stay when the computer is shut down and restarted, such as any .txt files, the locations of app shortcuts on the desktop, or a custom wallpaper\n\nWhatever you do, do NOT look up www.downloadram.com in the browser and do NOT click on any DOWNLOAD button that may appear\n\nCopyright Macrohard Bindows 10.5© OS 2024 all rights reserved.");
        manualScrollPane.setViewportView(manualTextPane);

        appManual.getContentPane().add(manualScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 750, 270));

        MainDesktopPane.add(appManual);
        appManual.setBounds(0, 0, 800, 390);

        appSettings.setBackground(new java.awt.Color(255, 255, 255));
        appSettings.setClosable(true);
        appSettings.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        appSettings.setMaximizable(true);
        appSettings.setResizable(true);
        appSettings.setTitle("Settings");
        appSettings.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallSettings.png"))); // NOI18N
        appSettings.setVisible(false);
        appSettings.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameLabel.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        usernameLabel.setText("Reset Username");
        appSettings.getContentPane().add(usernameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 150, 20));

        settingsVirus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/virusElongated1.png"))); // NOI18N
        appSettings.getContentPane().add(settingsVirus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 430, 200));

        wallpaper.setFont(new java.awt.Font("Gill Sans MT", 0, 14)); // NOI18N
        wallpaper.setText("Select Wallpaper");
        wallpaper.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        wallpaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallpaperActionPerformed(evt);
            }
        });
        appSettings.getContentPane().add(wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        resetWallpaper.setFont(new java.awt.Font("Gill Sans MT", 0, 14)); // NOI18N
        resetWallpaper.setText("Reset Wallpaper");
        resetWallpaper.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetWallpaper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetWallpaperActionPerformed(evt);
            }
        });
        appSettings.getContentPane().add(resetWallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        colourScheme.setFont(new java.awt.Font("Gill Sans MT", 0, 14)); // NOI18N
        colourScheme.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "White", "Black", "Blue", "Orange", "Red", "Purple", "Green" }));
        colourScheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colourSchemeActionPerformed(evt);
            }
        });
        appSettings.getContentPane().add(colourScheme, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 80, 30));

        customWallpaperLabel.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        customWallpaperLabel.setText("Custom Wallpaper");
        appSettings.getContentPane().add(customWallpaperLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 150, 20));

        desktopThemesLabel.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        desktopThemesLabel.setText("Change Desktop Theme");
        appSettings.getContentPane().add(desktopThemesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 200, 20));

        usernameText.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        usernameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextActionPerformed(evt);
            }
        });
        appSettings.getContentPane().add(usernameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 170, -1));

        passwordText.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        passwordText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextActionPerformed(evt);
            }
        });
        appSettings.getContentPane().add(passwordText, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 170, -1));

        passwordLabel.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        passwordLabel.setText("Reset Password");
        appSettings.getContentPane().add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, 150, 20));

        passwordReset.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        passwordReset.setText("Change");
        passwordReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordResetActionPerformed(evt);
            }
        });
        appSettings.getContentPane().add(passwordReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, -1, -1));

        usernameReset.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        usernameReset.setText("Change");
        usernameReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameResetActionPerformed(evt);
            }
        });
        appSettings.getContentPane().add(usernameReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 120, -1, -1));

        MainDesktopPane.add(appSettings);
        appSettings.setBounds(0, 0, 800, 390);

        appAntivirus.setBackground(new java.awt.Color(255, 255, 255));
        appAntivirus.setClosable(true);
        appAntivirus.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        appAntivirus.setMaximizable(true);
        appAntivirus.setResizable(true);
        appAntivirus.setTitle("Antivirus Software");
        appAntivirus.setVisible(false);
        appAntivirus.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        allGood.setFont(new java.awt.Font("Futura Md BT", 0, 36)); // NOI18N
        allGood.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        allGood.setText("All good! No viruses detected");
        appAntivirus.getContentPane().add(allGood, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 790, 80));

        eradicated.setFont(new java.awt.Font("Futura Md BT", 0, 48)); // NOI18N
        eradicated.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eradicated.setText("Virus successfully eradicated!");
        appAntivirus.getContentPane().add(eradicated, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 790, -1));

        antivirusOK.setFont(new java.awt.Font("Futura Md BT", 0, 12)); // NOI18N
        antivirusOK.setText("OK");
        antivirusOK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        antivirusOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antivirusOKActionPerformed(evt);
            }
        });
        appAntivirus.getContentPane().add(antivirusOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, 210, -1));

        virusScan.setFont(new java.awt.Font("Futura Md BT", 0, 24)); // NOI18N
        virusScan.setText("Run Virus Scanner");
        virusScan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        virusScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                virusScanActionPerformed(evt);
            }
        });
        appAntivirus.getContentPane().add(virusScan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 320, 170));

        loadingText.setFont(new java.awt.Font("FoundryMonoline-ExBdIta", 0, 12)); // NOI18N
        loadingText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loadingText.setText("Scanning");
        appAntivirus.getContentPane().add(loadingText, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 360));

        theVirus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/virus_tiny.png"))); // NOI18N
        appAntivirus.getContentPane().add(theVirus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 200, 100));

        theVirus2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/virus_tiny.png"))); // NOI18N
        appAntivirus.getContentPane().add(theVirus2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 240, 190, 100));

        theVirus3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/virus_tiny.png"))); // NOI18N
        appAntivirus.getContentPane().add(theVirus3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 200, 100));

        theVirus4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/virus_tiny.png"))); // NOI18N
        appAntivirus.getContentPane().add(theVirus4, new org.netbeans.lib.awtextra.AbsoluteConstraints(581, 200, 200, 100));

        sword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/sword_tiny.png"))); // NOI18N
        appAntivirus.getContentPane().add(sword, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 90, 100));

        detectedText.setEditable(false);
        detectedText.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        detectedText.setFont(new java.awt.Font("Candara Light", 1, 14)); // NOI18N
        detectedText.setText("Oh no, a virus has been detected!\nTo save your computer, take this sword and slay the virus!\n(use your mouse)");
        appAntivirus.getContentPane().add(detectedText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 200, 100));

        confetti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/confett11i.gif"))); // NOI18N
        appAntivirus.getContentPane().add(confetti, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 370));

        MainDesktopPane.add(appAntivirus);
        appAntivirus.setBounds(0, 0, 800, 390);

        myFile.setBackground(new java.awt.Color(255, 255, 255));
        myFile.setClosable(true);
        myFile.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        myFile.setMaximizable(true);
        myFile.setResizable(true);
        myFile.setTitle("my epic text file.txt");
        myFile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        myFile.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallmyFile.png"))); // NOI18N
        myFile.setVisible(false);
        myFile.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        fileContent.setColumns(20);
        fileContent.setRows(5);
        textField.setViewportView(fileContent);

        myFile.getContentPane().add(textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 370));

        infectedTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        fileContent1.setEditable(false);
        fileContent1.setColumns(20);
        fileContent1.setLineWrap(true);
        fileContent1.setRows(5);
        fileContent1.setText("iamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthevirusiamthe");
        infectedTextField.setViewportView(fileContent1);

        myFile.getContentPane().add(infectedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 370));

        MainDesktopPane.add(myFile);
        myFile.setBounds(0, 0, 800, 390);
        myFile.getAccessibleContext().setAccessibleDescription("");

        Waterfox.setBackground(new java.awt.Color(255, 255, 255));
        Waterfox.setClosable(true);
        Waterfox.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        Waterfox.setMaximizable(true);
        Waterfox.setResizable(true);
        Waterfox.setTitle("Mimosa Waterfox");
        Waterfox.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallWaterfox.png"))); // NOI18N
        Waterfox.setVisible(false);
        Waterfox.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        browserEnter.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        browserEnter.setText("Search");
        browserEnter.setBorder(new javax.swing.border.MatteBorder(null));
        browserEnter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browserEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browserEnterActionPerformed(evt);
            }
        });
        Waterfox.getContentPane().add(browserEnter, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, 90, 40));

        browserEnter2.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        browserEnter2.setText("Search");
        browserEnter2.setBorder(new javax.swing.border.MatteBorder(null));
        browserEnter2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browserEnter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browserEnter2ActionPerformed(evt);
            }
        });
        Waterfox.getContentPane().add(browserEnter2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 90, 40));

        searchBar.setFont(new java.awt.Font("Dubai Medium", 0, 12)); // NOI18N
        Waterfox.getContentPane().add(searchBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 280, -1));

        suggestions.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        suggestions.setText("<html>Search Suggestions:<br/>apple<br/>blue<br/>www.downloadram.com<br/>fish</html>");
        Waterfox.getContentPane().add(suggestions, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 160, 190));

        browserTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/WaterfoxTitle.png"))); // NOI18N
        Waterfox.getContentPane().add(browserTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 600, 110));

        searchBar2.setFont(new java.awt.Font("Dubai Medium", 0, 12)); // NOI18N
        Waterfox.getContentPane().add(searchBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 340, -1));

        downloadVirus.setText("DOWNLOAD");
        downloadVirus.setToolTipText("(100% safe)");
        downloadVirus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        downloadVirus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadVirusActionPerformed(evt);
            }
        });
        Waterfox.getContentPane().add(downloadVirus, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 140, 30));

        appleResult.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/appleResult1.jpg"))); // NOI18N
        appleResult.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Waterfox.getContentPane().add(appleResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 790, 280));

        freeRAM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/freeRAM.jpg"))); // NOI18N
        freeRAM.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Waterfox.getContentPane().add(freeRAM, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 790, 280));

        blueResult.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/blueResult.jpg"))); // NOI18N
        blueResult.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Waterfox.getContentPane().add(blueResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 790, 280));

        fishResult.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/fishResult.jpg"))); // NOI18N
        fishResult.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Waterfox.getContentPane().add(fishResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 790, 280));

        noResults.setText("Your search has no results");
        Waterfox.getContentPane().add(noResults, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 660, 50));

        home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/home.jpg"))); // NOI18N
        home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });
        Waterfox.getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 60, -1));

        virusResult.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/blueVirus.jpg"))); // NOI18N
        virusResult.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Waterfox.getContentPane().add(virusResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 790, 280));

        whiteBroswer.setBackground(new java.awt.Color(255, 255, 255));
        whiteBroswer.setForeground(new java.awt.Color(255, 255, 255));
        Waterfox.getContentPane().add(whiteBroswer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 460));

        MainDesktopPane.add(Waterfox);
        Waterfox.setBounds(0, 0, 800, 400);

        virusFrame.setClosable(true);
        virusFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        virusFrame.setTitle("you're computer is HACKED with VIRUS");
        virusFrame.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        virusFrame.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        virusFrame.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/virus1.png"))); // NOI18N
        virusFrame.setVisible(false);
        virusFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        greenBack.setBackground(new java.awt.Color(0, 204, 51));
        greenBack.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        virusMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));

        jTextPane1.setEditable(false);
        virusMessage.setViewportView(jTextPane1);
        jTextPane1.setText("hi i am virus.\ni am now hack ur computer.\nokie\ni lvoe the computer;\nit is makes me happy\ni am virus\nmy name is virus\npleas do not not not kill me maintenant\ni am only small virus,");

        greenBack.add(virusMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 180, 90));

        virusFrame.getContentPane().add(greenBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 380, 180));

        MainDesktopPane.add(virusFrame);
        virusFrame.setBounds(230, 110, 370, 210);

        power.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/power.png"))); // NOI18N
        power.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        power.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                powerActionPerformed(evt);
            }
        });
        MainDesktopPane.add(power);
        power.setBounds(0, 400, 50, 50);

        taskbarManual.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        taskbarManual.setForeground(new java.awt.Color(255, 255, 255));
        taskbarManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/bindowsSmall1.png"))); // NOI18N
        taskbarManual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        taskbarManual.setFocusPainted(false);
        taskbarManual.setRolloverEnabled(false);
        taskbarManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskbarManualActionPerformed(evt);
            }
        });
        MainDesktopPane.add(taskbarManual);
        taskbarManual.setBounds(290, 400, 60, 50);

        taskbarNotepad.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        taskbarNotepad.setForeground(new java.awt.Color(255, 255, 255));
        taskbarNotepad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallmyFile.png"))); // NOI18N
        taskbarNotepad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        taskbarNotepad.setRolloverEnabled(false);
        taskbarNotepad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskbarNotepadActionPerformed(evt);
            }
        });
        MainDesktopPane.add(taskbarNotepad);
        taskbarNotepad.setBounds(110, 400, 60, 50);

        taskbarBrowser.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        taskbarBrowser.setForeground(new java.awt.Color(255, 255, 255));
        taskbarBrowser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallWaterfox.png"))); // NOI18N
        taskbarBrowser.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        taskbarBrowser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        taskbarBrowser.setRolloverEnabled(false);
        taskbarBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskbarBrowserActionPerformed(evt);
            }
        });
        MainDesktopPane.add(taskbarBrowser);
        taskbarBrowser.setBounds(50, 400, 60, 50);

        taskbarSettings.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        taskbarSettings.setForeground(new java.awt.Color(255, 255, 255));
        taskbarSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallSettings.png"))); // NOI18N
        taskbarSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        taskbarSettings.setRolloverEnabled(false);
        taskbarSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskbarSettingsActionPerformed(evt);
            }
        });
        MainDesktopPane.add(taskbarSettings);
        taskbarSettings.setBounds(170, 400, 60, 50);

        taskbarAntivirus.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        taskbarAntivirus.setForeground(new java.awt.Color(255, 255, 255));
        taskbarAntivirus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallAnitvirus.png"))); // NOI18N
        taskbarAntivirus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        taskbarAntivirus.setFocusPainted(false);
        taskbarAntivirus.setRolloverEnabled(false);
        taskbarAntivirus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskbarAntivirusActionPerformed(evt);
            }
        });
        MainDesktopPane.add(taskbarAntivirus);
        taskbarAntivirus.setBounds(230, 400, 60, 50);

        browser.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        browser.setForeground(new java.awt.Color(255, 255, 255));
        browser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallWaterfox.png"))); // NOI18N
        browser.setText("Mimosa Waterfox");
        browser.setBorder(null);
        browser.setContentAreaFilled(false);
        browser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browser.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallWaterfox.png"))); // NOI18N
        browser.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallWaterfoxDark.png"))); // NOI18N
        browser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browserActionPerformed(evt);
            }
        });
        MainDesktopPane.add(browser);
        browser.setBounds(50, 50, 140, 80);

        notepad.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        notepad.setForeground(new java.awt.Color(255, 255, 255));
        notepad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallmyFile.png"))); // NOI18N
        notepad.setText("my epic text file.txt");
        notepad.setContentAreaFilled(false);
        notepad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notepad.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallmyFile.png"))); // NOI18N
        notepad.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallmyFileDark.png"))); // NOI18N
        notepad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notepadActionPerformed(evt);
            }
        });
        MainDesktopPane.add(notepad);
        notepad.setBounds(240, 50, 130, 80);

        manual.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        manual.setForeground(new java.awt.Color(255, 255, 255));
        manual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/bindowsSmall1.png"))); // NOI18N
        manual.setText("User Manual");
        manual.setContentAreaFilled(false);
        manual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manual.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/bindowsSmall1.png"))); // NOI18N
        manual.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/bindowsSmallDark.png"))); // NOI18N
        manual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualActionPerformed(evt);
            }
        });
        MainDesktopPane.add(manual);
        manual.setBounds(50, 170, 100, 70);

        settings.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        settings.setForeground(new java.awt.Color(255, 255, 255));
        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallSettings.png"))); // NOI18N
        settings.setText("Settings");
        settings.setContentAreaFilled(false);
        settings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settings.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallSettings.png"))); // NOI18N
        settings.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallSettingsDark.png"))); // NOI18N
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });
        MainDesktopPane.add(settings);
        settings.setBounds(440, 50, 90, 70);

        antivirus.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        antivirus.setForeground(new java.awt.Color(255, 255, 255));
        antivirus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallAnitvirus.png"))); // NOI18N
        antivirus.setText("Antivirus Software");
        antivirus.setContentAreaFilled(false);
        antivirus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        antivirus.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallAnitvirus.png"))); // NOI18N
        antivirus.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/SmallAnitvirusDark.png"))); // NOI18N
        antivirus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antivirusActionPerformed(evt);
            }
        });
        MainDesktopPane.add(antivirus);
        antivirus.setBounds(610, 50, 130, 70);

        taskbarPanel.setBackground(new Color(30, 46, 79, 110));
        taskbarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date.setFont(new java.awt.Font("Gill Sans MT", 0, 14)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        date.setText("Bindows 10.5");
        taskbarPanel.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 5, 130, 40));

        MainDesktopPane.add(taskbarPanel);
        taskbarPanel.setBounds(0, 400, 800, 50);
        MainDesktopPane.add(background);
        background.setBounds(0, 0, 800, 450);

        getContentPane().add(MainDesktopPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // for when user wants to turn off computer
    private void powerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_powerActionPerformed
        int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to shut down the computer?", getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            // save app locations on desktop in memory using multidimensional array
            int[][] apps = new int[5][2];
            apps[0][0] = (int) browser.getLocation().getX();
            apps[0][1] = (int) browser.getLocation().getY();
            apps[1][0] = (int) notepad.getLocation().getX();
            apps[1][1] = (int) notepad.getLocation().getY();
            apps[2][0] = (int) settings.getLocation().getX();
            apps[2][1] = (int) settings.getLocation().getY();
            apps[3][0] = (int) antivirus.getLocation().getX();
            apps[3][1] = (int) antivirus.getLocation().getY();
            apps[4][0] = (int) manual.getLocation().getX();
            apps[4][1] = (int) manual.getLocation().getY();
            
            try {
                    FileWriter fw = new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\appLocations.txt");
                    PrintWriter pw = new PrintWriter(fw);
                    
                    // run through the array, placing numbers in txt file with spacebar as seperator and lines seperating each app coords
                    for (int i=0; i < 5; i++) {
                        for (int j=0; j<2; j++) {
                                pw.print(apps[i][j] + " ");
                        }
                        pw.println();
                    }
                    pw.close();
                    fw.close();

                } catch (IOException p) {
                    System.out.println("ERROR");
                }

            System.exit(0);
        }
    }//GEN-LAST:event_powerActionPerformed

    // determines if user wants to save my epic text file inputted text
    public String saveFileText() {
        int reply = JOptionPane.showConfirmDialog(this, "Save changes to \"my epic text file.txt\"?", getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            return "yes";
        } else {
            return "no";
        }
    }

    private void browserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browserActionPerformed

    }//GEN-LAST:event_browserActionPerformed

    private void browserEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browserEnterActionPerformed
        searchBar2.setText(searchBar.getText());
        home.setVisible(true);
        // check for virus infested browser
        if (virus == true) {
            searchBar.setVisible(false);
            suggestions.setVisible(false);
            browserEnter.setVisible(false);
            browserTitle.setVisible(false);
            freeRAM.setVisible(false);
            fishResult.setVisible(false);
            noResults.setVisible(false);
            downloadVirus.setVisible(false);

            virusResult.setVisible(true);
            searchBar2.setVisible(true);
            browserEnter2.setVisible(true);
        } else { // normal browser function, depending on search display a result
            if ((searchBar.getText().toLowerCase().strip()).equals("apple")) {
                searchBar.setVisible(false);
                suggestions.setVisible(false);
                browserEnter.setVisible(false);
                browserTitle.setVisible(false);
                freeRAM.setVisible(false);
                fishResult.setVisible(false);
                noResults.setVisible(false);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);

                appleResult.setVisible(true);
                searchBar2.setVisible(true);
                browserEnter2.setVisible(true);
            } else if ((searchBar.getText().toLowerCase().strip()).equals("blue")) {
                searchBar.setVisible(false);
                suggestions.setVisible(false);
                browserEnter.setVisible(false);
                browserTitle.setVisible(false);
                freeRAM.setVisible(false);
                fishResult.setVisible(false);
                noResults.setVisible(false);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);

                blueResult.setVisible(true);
                searchBar2.setVisible(true);
                browserEnter2.setVisible(true);
            } else if ((searchBar.getText().toLowerCase().strip()).equals("www.downloadram.com")) {
                searchBar.setVisible(false);
                suggestions.setVisible(false);
                browserEnter.setVisible(false);
                browserTitle.setVisible(false);
                blueResult.setVisible(false);
                fishResult.setVisible(false);
                noResults.setVisible(false);
                virusResult.setVisible(false);

                downloadVirus.setVisible(true);
                freeRAM.setVisible(true);
                searchBar2.setVisible(true);
                browserEnter2.setVisible(true);
            } else if ((searchBar.getText().toLowerCase().strip()).equals("fish")) {
                searchBar.setVisible(false);
                suggestions.setVisible(false);
                browserEnter.setVisible(false);
                browserTitle.setVisible(false);
                blueResult.setVisible(false);
                freeRAM.setVisible(false);
                noResults.setVisible(false);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);

                fishResult.setVisible(true);
                searchBar2.setVisible(true);
                browserEnter2.setVisible(true);
            } else { // for when user search does not match any search suggestion
                searchBar.setVisible(false);
                suggestions.setVisible(false);
                browserEnter.setVisible(false);
                browserTitle.setVisible(false);
                blueResult.setVisible(false);
                freeRAM.setVisible(false);
                fishResult.setVisible(false);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);

                noResults.setVisible(true);
                searchBar2.setVisible(true);
                browserEnter2.setVisible(true);
                noResults.setText("Your search - " + searchBar.getText() + " - does not have any results");
            }
        }
    }//GEN-LAST:event_browserEnterActionPerformed

    private void browserEnter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browserEnter2ActionPerformed
        // secondary enter button for when user is not searching from home
        if (virus == true) {
            virusResult.setVisible(true);
        } else { // normal browser function
            if ((searchBar2.getText().toLowerCase()).equals("apple")) {
                // all other results setVisible(false);    
                fishResult.setVisible(false);
                blueResult.setVisible(false);
                freeRAM.setVisible(false);
                appleResult.setVisible(true);
                noResults.setVisible(false);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);
            } else if ((searchBar2.getText().toLowerCase()).equals("blue")) {
                fishResult.setVisible(false);
                appleResult.setVisible(false);
                freeRAM.setVisible(false);
                blueResult.setVisible(true);
                noResults.setVisible(false);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);
            } else if ((searchBar2.getText().toLowerCase()).equals("www.downloadram.com")) {
                fishResult.setVisible(false);
                appleResult.setVisible(false);
                blueResult.setVisible(false);
                freeRAM.setVisible(true);
                noResults.setVisible(false);
                downloadVirus.setVisible(true);
                virusResult.setVisible(false);
            } else if ((searchBar2.getText().toLowerCase()).equals("fish")) {
                appleResult.setVisible(false);
                blueResult.setVisible(false);
                freeRAM.setVisible(false);
                fishResult.setVisible(true);
                noResults.setVisible(false);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);
            } else {
                appleResult.setVisible(false);
                blueResult.setVisible(false);
                freeRAM.setVisible(false);
                fishResult.setVisible(false);
                noResults.setText("Your search - " + searchBar2.getText() + " - does not have any results");
                noResults.setVisible(true);
                downloadVirus.setVisible(false);
                virusResult.setVisible(false);
            }
        }
    }//GEN-LAST:event_browserEnter2ActionPerformed

    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
        // return to Waterfox landing page
        home.setVisible(false);
        searchBar.setText("");
        searchBar.setVisible(false);
        appleResult.setVisible(false);
        blueResult.setVisible(false);
        freeRAM.setVisible(false);
        fishResult.setVisible(false);
        noResults.setVisible(false);
        searchBar2.setVisible(false);
        browserEnter2.setVisible(false);
        downloadVirus.setVisible(false);
        virusResult.setVisible(false);

        searchBar.setVisible(true);
        browserEnter.setVisible(true);
        browserTitle.setVisible(true);
        suggestions.setVisible(true);
    }//GEN-LAST:event_homeActionPerformed

    private void notepadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notepadActionPerformed

    }//GEN-LAST:event_notepadActionPerformed

    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingsActionPerformed

    private void wallpaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallpaperActionPerformed
        // make a new instance of the filechooser dialog to get the path to an image the user chooses
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();
            File imageFile = new File(imagePath);

            try {
                // get the image and resize it to fit the background
                ImageIcon originalIcon = new ImageIcon(ImageIO.read(imageFile));
                Image originalImage = originalIcon.getImage();

                // check if image is valid
                if (originalImage == null) {
                    JOptionPane.showMessageDialog(Main.this, "The selected file is not a valid image", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    // resize the image
                    int newWidth = 800;
                    int newHeight = 450;
                    // scale the image to fit the computer simulator's desktop
                    Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    background.setIcon(resizedIcon);
                    // center wallpaper
                    background.setHorizontalAlignment(JLabel.CENTER);
                    background.setVerticalAlignment(JLabel.CENTER);

                    // save wallpaper path for later (easier than saving image itself)
                    try {
                        FileWriter fw = new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\wallpaperPath.txt");
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(imagePath);
                        pw.close();
                        fw.close();

                    } catch (IOException p) {
                        System.out.println("ERROR");
                    }

                }

            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_wallpaperActionPerformed

    private void resetWallpaperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetWallpaperActionPerformed
        // reset the wallpaper to the original
        ImageIcon defaultWallpaperIcon = new ImageIcon("");
        background.setIcon(defaultWallpaperIcon);

        try {
            // basically just clear wallpaperPath.txt
            FileWriter fw = new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\wallpaperPath.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.println("");
            pw.close();
            fw.close();

        } catch (IOException p) {
            System.out.println("ERROR");
        }

    }//GEN-LAST:event_resetWallpaperActionPerformed

    private void antivirusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antivirusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_antivirusActionPerformed

    private void virusScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_virusScanActionPerformed
        // perform loading screen for virus scan
        virusScan.setVisible(false);
        loadingText.setVisible(true);
        loadingText.setText("Scanning...");
        // timers are for scanning progress
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadingText.setText("Scanning - 25%");
            }
        });
        timer.setRepeats(false);
        timer.start();
        Timer timer2 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadingText.setText("Scanning - 50%");
            }
        });
        timer2.setRepeats(false);
        timer2.start();
        Timer timer3 = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadingText.setText("Scanning - 75%");
            }
        });
        timer3.setRepeats(false);
        timer3.start();
        Timer timer4 = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadingText.setText("Scanning - 100%");
            }
        });
        timer4.setRepeats(false);
        timer4.start();
        Timer timer5 = new Timer(6100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if a virus has been detected, deploy virus eradication game
                if (virus == true) {
                    loadingText.setVisible(false);
                    sword.setVisible(true);
                    theVirus1.setVisible(true);
                    theVirus2.setVisible(true);
                    theVirus3.setVisible(true);
                    theVirus4.setVisible(true);
                    detectedText.setVisible(true);

                    // Monitor for intersection continuously (every 100 millisenods will update)
                    Timer intersectionTimer = new Timer(100, new ActionListener() {
                        int virusTotal = 0;
                        boolean virus1 = false;
                        boolean virus2 = false;
                        boolean virus3 = false;
                        boolean virus4 = false;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Check for collision
                            if (sword.getBounds().intersects(theVirus1.getBounds()) && virus1 == false) {
                                theVirus1.setVisible(false);
                                virus1 = true;
                                virusTotal++;
                            }
                            if (sword.getBounds().intersects(theVirus2.getBounds()) && virus2 == false) {
                                theVirus2.setVisible(false);
                                virusTotal++;
                                virus2 = true;
                            }
                            if (sword.getBounds().intersects(theVirus3.getBounds()) && virus3 == false) {
                                theVirus3.setVisible(false);
                                virusTotal++;
                                virus3 = true;
                            }
                            if (sword.getBounds().intersects(theVirus4.getBounds()) && virus4 == false) {
                                theVirus4.setVisible(false);
                                virusTotal++;
                                virus4 = true;
                            }
                            // all virus slayed
                            if (virusTotal > 3) {
                                sword.setVisible(false);
                                virus = false;
                                
                                // celebration
                                confetti.setVisible(true);
                                antivirusOK.setVisible(true);
                                eradicated.setVisible(true);
                                detectedText.setVisible(false);
                                
                                // get rid of virus in memory
                                try {
                                    FileWriter fw = new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\virusStatus.txt");
                                    PrintWriter pw = new PrintWriter(fw);
                                    pw.println("false");
                                    pw.close();
                                    fw.close();

                                } catch (IOException p) {
                                    System.out.println("ERROR");
                                }
                                
                                // wallpaper remembering from memory
                                
                                // just in case no wallpaper, reset to default
                                ImageIcon defaultWallpaperIcon = new ImageIcon("");
                                background.setIcon(defaultWallpaperIcon);
                        try {
                            FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\wallpaperPath.txt");
                            BufferedReader br = new BufferedReader(fr);
                            wallpaperPath = br.readLine();
                        } catch (IOException q) {
                            out.println("file not found");
                        }

                        if (!wallpaperPath.equals("")) {
                            File imageFile = new File(wallpaperPath);
                            try {
                                // get the image and resize it to fit the background
                                ImageIcon originalIcon = new ImageIcon(ImageIO.read(imageFile));
                                Image originalImage = originalIcon.getImage();

                                // check if image is valid
                                if (originalImage == null) {
                                    JOptionPane.showMessageDialog(Main.this, "The selected file is not a valid image", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {

                                    // resize the image to the dimensions of the desktop
                                    int newWidth = 800;
                                    int newHeight = 450;
                                    Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                                    background.setIcon(resizedIcon);
                                    // center wallpaper
                                    background.setHorizontalAlignment(JLabel.CENTER);
                                    background.setVerticalAlignment(JLabel.CENTER);
                                }

                            } catch (IOException | IllegalArgumentException d) {
                                d.printStackTrace();
                            }

                        }
                                
                                
                                // now that the collision is acheived, end the detection loop
                                ((Timer) e.getSource()).stop();
                            }
                        }
                    });
                    intersectionTimer.start();
                    
                // on no virus detected
                } else {
                    loadingText.setVisible(false);
                    allGood.setVisible(true);
                    antivirusOK.setVisible(true);
                }
            }
        });
        timer5.setRepeats(false);
        timer5.start();

    }//GEN-LAST:event_virusScanActionPerformed

    private void downloadVirusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadVirusActionPerformed
        // when user downloads the virus
        virus = true;
        try {
            FileWriter fw = new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\virusStatus.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.println("true");
            pw.close();
            fw.close();

        } catch (IOException p) {
            System.out.println("ERROR");
        }
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // close all apps, open virus dialog
                Waterfox.setVisible(false);
                myFile.setVisible(false);
                appSettings.setVisible(false);
                appAntivirus.setVisible(false);

                virusFrame.setVisible(true);
                File virusWall = new File("src\\main\\resources\\graphics\\infectedWallpaper1.png");
                try {
                    ImageIcon infectedIcon = new ImageIcon(ImageIO.read(virusWall));
                    background.setIcon(infectedIcon);
                } catch (IOException | IllegalArgumentException j) {
                    j.printStackTrace();
                }

            }
        });
        timer.setRepeats(false);
        timer.start();
    }//GEN-LAST:event_downloadVirusActionPerformed

    private void colourSchemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colourSchemeActionPerformed
        // change colours of desktop app names
        String colour = colourScheme.getSelectedItem().toString();
        if (colour.equals("Red")) {
            browser.setForeground(Color.RED);
            notepad.setForeground(Color.RED);
            antivirus.setForeground(Color.RED);
            settings.setForeground(Color.RED);
            manual.setForeground(Color.RED);
            date.setForeground(Color.RED);
        } else if (colour.equals("White")) {
            browser.setForeground(Color.WHITE);
            notepad.setForeground(Color.WHITE);
            antivirus.setForeground(Color.WHITE);
            settings.setForeground(Color.WHITE);
            manual.setForeground(Color.WHITE);
            date.setForeground(Color.WHITE);
        } else if (colour.equals("Black")) {
            browser.setForeground(Color.BLACK);
            notepad.setForeground(Color.BLACK);
            antivirus.setForeground(Color.BLACK);
            settings.setForeground(Color.BLACK);
            manual.setForeground(Color.BLACK);
            date.setForeground(Color.BLACK);
        } else if (colour.equals("Orange")) {
            browser.setForeground(Color.ORANGE);
            notepad.setForeground(Color.ORANGE);
            antivirus.setForeground(Color.ORANGE);
            settings.setForeground(Color.ORANGE);
            manual.setForeground(Color.ORANGE);
            date.setForeground(Color.ORANGE);
        } else if (colour.equals("Purple")) {
            browser.setForeground(PURPLE);
            notepad.setForeground(PURPLE);
            antivirus.setForeground(PURPLE);
            settings.setForeground(PURPLE);
            manual.setForeground(PURPLE);
            date.setForeground(PURPLE);
        } else if (colour.equals("Green")) {
            browser.setForeground(Color.GREEN);
            notepad.setForeground(Color.GREEN);
            antivirus.setForeground(Color.GREEN);
            settings.setForeground(Color.GREEN);
            manual.setForeground(Color.GREEN);
            date.setForeground(Color.GREEN);
        } else if (colour.equals("Blue")) {
            browser.setForeground(Color.BLUE);
            notepad.setForeground(Color.BLUE);
            antivirus.setForeground(Color.BLUE);
            settings.setForeground(Color.BLUE);
            manual.setForeground(Color.BLUE);
            date.setForeground(Color.BLUE);
        }
        try {
            FileWriter fw = new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\colourScheme.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.println(colour);
            pw.close();
            fw.close();

        } catch (IOException p) {
            System.out.println("ERROR");
        }

    }//GEN-LAST:event_colourSchemeActionPerformed

    // taskbar versions of opening app sequences
    private void taskbarBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskbarBrowserActionPerformed
        if (!Waterfox.isVisible()) {
            Waterfox.setVisible(true);
            searchBar.setVisible(true);
            browserEnter.setVisible(true);
            browserTitle.setVisible(true);
            suggestions.setVisible(true);
            Waterfox.setSize(800, 400);
            Waterfox.setLocation(0, 0);
            searchBar.setText("");

            downloadVirus.setVisible(false);
            home.setVisible(false);
            noResults.setVisible(false);
            appleResult.setVisible(false);
            blueResult.setVisible(false);
            searchBar2.setVisible(false);
            browserEnter2.setVisible(false);
            freeRAM.setVisible(false);
            fishResult.setVisible(false);
            virusResult.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(Main.this, "'Mimosa Waterfox' is already open", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_taskbarBrowserActionPerformed

    private void taskbarNotepadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskbarNotepadActionPerformed
        if (!myFile.isVisible()) {
            if (virus == true) {
                myFile.setVisible(true);
                textField.setVisible(false);
                infectedTextField.setVisible(true);
                myFile.setSize(800, 400);
                myFile.setLocation(0, 0);
            } else {
                try {
                    FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\myFile.txt");
                    BufferedReader br = new BufferedReader(fr);
                    fileContent.setText(""); // Clear existing content in case you are reloading the file
                    String line;
                    // Read each line from the file and append it to JTextArea
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                        fileContent.append(line + "\n");
                    }

                } catch (IOException e) {
                    out.println("file not found");
                }

                myFile.setVisible(true);
                textField.setVisible(true);
                infectedTextField.setVisible(false);
                myFile.setSize(800, 400);
                myFile.setLocation(0, 0);

                saveText = fileContent.getText();
            }
        } else {
            JOptionPane.showMessageDialog(Main.this, "'my epic text file.txt' is already open", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_taskbarNotepadActionPerformed

    private void taskbarSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskbarSettingsActionPerformed
        if (!appSettings.isVisible()) {
            appSettings.setVisible(true);
            
            try {
                FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\userInfo.txt");
                BufferedReader br = new BufferedReader(fr);
                usernameText.setText(br.readLine());
                passwordText.setText(br.readLine());
            } catch (IOException e) {
                out.println("file not found");
            }
            
            if (virus == true) {
                settingsVirus.setVisible(true);
                resetWallpaper.setVisible(false);
                wallpaper.setVisible(false);
            } else {
                settingsVirus.setVisible(false);
                resetWallpaper.setVisible(true);
                wallpaper.setVisible(true);
                appSettings.setSize(800, 400);
                appSettings.setLocation(0, 0);
            }
        } else {
            JOptionPane.showMessageDialog(Main.this, "'Settings' is already open", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_taskbarSettingsActionPerformed

    private void taskbarAntivirusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskbarAntivirusActionPerformed
        if (!appAntivirus.isVisible()) {
            // must begin by reseting frame components
            sword.setVisible(false);
            theVirus4.setVisible(false);
            theVirus3.setVisible(false);
            theVirus2.setVisible(false);
            theVirus1.setVisible(false);
            detectedText.setVisible(false);
            appAntivirus.setVisible(true);
            virusScan.setVisible(true);
            appAntivirus.setSize(800, 400);
            appAntivirus.setLocation(0, 0);
            allGood.setVisible(false);
            antivirusOK.setVisible(false);
            loadingText.setVisible(false);
            confetti.setVisible(false);
            eradicated.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(Main.this, "'Antivirus Software' is already open", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_taskbarAntivirusActionPerformed

    // return to main antivirus page after virus eradicated
    private void antivirusOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antivirusOKActionPerformed
        allGood.setVisible(false);
        confetti.setVisible(false);
        antivirusOK.setVisible(false);
        eradicated.setVisible(false);
        virusScan.setVisible(true);
    }//GEN-LAST:event_antivirusOKActionPerformed

    private void manualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_manualActionPerformed

    private void taskbarManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskbarManualActionPerformed
        if (!appManual.isVisible()) {
            appManual.setVisible(true);
            appManual.setSize(800, 400);
            appManual.setLocation(0, 0);
        } else {
            JOptionPane.showMessageDialog(Main.this, "'User Manual' is already open", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_taskbarManualActionPerformed

    private void usernameResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameResetActionPerformed
        if (usernameText.getText() == null || usernameText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Main.this, "You cannot have an empty username", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            
        String newContent = usernameText.getText();
        try {
            BufferedReader fr = new BufferedReader(new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\userInfo.txt"));
            StringBuilder content = new StringBuilder();
            String line;
            int lineNumber = 1;

            while ((line = fr.readLine()) != null) {
                if (lineNumber == 1) {
                    content.append(newContent).append(System.lineSeparator());
                } else {
                    content.append(line).append(System.lineSeparator());
                }
                lineNumber++;
            }
            fr.close();

            // Write the modified content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\userInfo.txt"));
            writer.write(content.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }//GEN-LAST:event_usernameResetActionPerformed

    private void passwordTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextActionPerformed
        
    }//GEN-LAST:event_passwordTextActionPerformed

    private void usernameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextActionPerformed

    private void passwordResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordResetActionPerformed
        if (passwordText.getText() == null || passwordText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Main.this, "You cannot have an empty password", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            
        }
        String newContent = passwordText.getText();
        try {
            BufferedReader fr = new BufferedReader(new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\userInfo.txt"));
            StringBuilder content = new StringBuilder();
            String line;
            int lineNumber = 1;

            while ((line = fr.readLine()) != null) {
                if (lineNumber == 2) {
                    content.append(newContent).append(System.lineSeparator());
                } else {
                    content.append(line).append(System.lineSeparator());
                }
                lineNumber++;
            }
            fr.close();

            // Write the modified content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\com\\mycompany\\fpt\\HDD\\userInfo.txt"));
            writer.write(content.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_passwordResetActionPerformed

    // instead of using normal button, listen for double click. this part will constantly run in the background (desktop icons only)
    private class DoubleClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            long clickTime = System.currentTimeMillis();
            System.out.println("clicktime:" + clickTime);
            System.out.println("lastClickTime:" + lastClickTime);
            // if new click was close enough in time to previous click, open app
            if (clickTime - lastClickTime <= 300) {
                // but first make sure app isn't already open
                if (!Waterfox.isVisible()) {
                    Waterfox.setVisible(true);
                    searchBar.setVisible(true);
                    browserEnter.setVisible(true);
                    browserTitle.setVisible(true);
                    suggestions.setVisible(true);
                    Waterfox.setSize(800, 400);
                    Waterfox.setLocation(0, 0);
                    searchBar.setText("");

                    downloadVirus.setVisible(false);
                    home.setVisible(false);
                    noResults.setVisible(false);
                    appleResult.setVisible(false);
                    blueResult.setVisible(false);
                    searchBar2.setVisible(false);
                    browserEnter2.setVisible(false);
                    freeRAM.setVisible(false);
                    fishResult.setVisible(false);
                    virusResult.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "'Mimosa Waterfox' is already open", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            lastClickTime = clickTime;
        }
    }

    // same thing, but for each other desktop icon now
    private class FileDoubleClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            long clickTime2 = System.currentTimeMillis();
            if (clickTime2 - lastClickTime2 <= 300) {
                if (!myFile.isVisible()) {
                    // functionallity will change upon virus infection
                    if (virus == false) {
                        try {
                            FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\myFile.txt");
                            BufferedReader br = new BufferedReader(fr);
                            fileContent.setText("");
                            String line;
                            // Read each line from the file and append it to the simulated text file
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                                fileContent.append(line + "\n");
                            }

                        } catch (IOException b) {
                            out.println("file not found");
                        }

                        myFile.setVisible(true);
                        textField.setVisible(true);
                        infectedTextField.setVisible(false);
                        myFile.setSize(800, 400);
                        myFile.setLocation(0, 0);
                        // the saveText variable stores the text in case the user doesn't want to save, it'll revert to original
                        saveText = fileContent.getText();
                    } else {
                        myFile.setVisible(true);
                        textField.setVisible(false);
                        infectedTextField.setVisible(true);
                        myFile.setSize(800, 400);
                        myFile.setLocation(0, 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.this, "The app 'my epic text file.txt' is already open", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            lastClickTime2 = clickTime2;
        }
    }

    private class SettingsDoubleClickListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            long clickTime3 = System.currentTimeMillis();
            if (clickTime3 - lastClickTime3 <= 300) {
                if (!appSettings.isVisible()) {
                    appSettings.setVisible(true);
                    
                    try {
                        FileReader fr = new FileReader("src\\main\\java\\com\\mycompany\\fpt\\HDD\\userInfo.txt");
                        BufferedReader br = new BufferedReader(fr);
                        usernameText.setText(br.readLine());
                        passwordText.setText(br.readLine());
                    } catch (IOException l) {
                        out.println("file not found");
                    }
                    
                    if (virus == true) {
                        settingsVirus.setVisible(true);
                        resetWallpaper.setVisible(false);
                        wallpaper.setVisible(false);
                    } else {
                        settingsVirus.setVisible(false);
                        resetWallpaper.setVisible(true);
                        wallpaper.setVisible(true);
                        appSettings.setSize(800, 400);
                        appSettings.setLocation(0, 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(Main.this, "'Settings' is already open", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            lastClickTime3 = clickTime3;
        }
    }

    private class AntivirusDoubleClickListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            long clickTime4 = System.currentTimeMillis();
            if (clickTime4 - lastClickTime4 <= 300) {
                if (!appAntivirus.isVisible()) {
                    confetti.setVisible(false);
                    antivirusOK.setVisible(false);
                    eradicated.setVisible(false);
                    sword.setVisible(false);
                    theVirus4.setVisible(false);
                    theVirus3.setVisible(false);
                    theVirus2.setVisible(false);
                    theVirus1.setVisible(false);
                    detectedText.setVisible(false);
                    appAntivirus.setVisible(true);
                    virusScan.setVisible(true);
                    appAntivirus.setSize(800, 400);
                    appAntivirus.setLocation(0, 0);
                    allGood.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "'Antivirus Software' is already open", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            lastClickTime4 = clickTime4;
        }
    }

    private class ManualDoubleClickListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            long clickTime5 = System.currentTimeMillis();
            if (clickTime5 - lastClickTime5 <= 300) {
                if (!appManual.isVisible()) {

                    appManual.setVisible(true);

                    appManual.setSize(800, 400);
                    appManual.setLocation(0, 0);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "'User Manual' is already open", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            lastClickTime5 = clickTime5;
        }
    }

    // makes desktop icons draggable, so if a icon is held and the mouse moves (while it's still held), the icon will move until it is released
    private static void makeDraggable(final Component component, final JFrame frame) {
        // make a new point (basically store x y coords)
        final Point offset = new Point();
        
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                offset.setLocation(e.getPoint());
            }
        });
        component.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point newLocation = component.getLocation();
                // app location - new mouse location = the amouunt that the icon should translate across the screen
                newLocation.translate(e.getX() - offset.x, e.getY() - offset.y);
                component.setLocation(newLocation);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main main = new Main();
                main.setSize(800, 450);
                main.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane MainDesktopPane;
    private javax.swing.JInternalFrame Waterfox;
    private javax.swing.JLabel allGood;
    private javax.swing.JButton antivirus;
    private javax.swing.JButton antivirusOK;
    private javax.swing.JInternalFrame appAntivirus;
    private javax.swing.JInternalFrame appManual;
    private javax.swing.JInternalFrame appSettings;
    private javax.swing.JLabel appleResult;
    private javax.swing.JLabel background;
    private javax.swing.JLabel blueResult;
    private javax.swing.JButton browser;
    private javax.swing.JButton browserEnter;
    private javax.swing.JButton browserEnter2;
    private javax.swing.JLabel browserTitle;
    private javax.swing.JComboBox<String> colourScheme;
    private javax.swing.JLabel confetti;
    private javax.swing.JLabel customWallpaperLabel;
    private javax.swing.JLabel customWallpaperLabel1;
    private javax.swing.JLabel date;
    private javax.swing.JLabel desktopThemesLabel;
    private javax.swing.JTextPane detectedText;
    private javax.swing.JButton downloadVirus;
    private javax.swing.JLabel eradicated;
    private javax.swing.JTextArea fileContent;
    private javax.swing.JTextArea fileContent1;
    private javax.swing.JLabel fishResult;
    private javax.swing.JLabel freeRAM;
    private javax.swing.JPanel greenBack;
    private javax.swing.JButton home;
    private javax.swing.JScrollPane infectedTextField;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel loadingText;
    private javax.swing.JButton manual;
    private javax.swing.JScrollPane manualScrollPane;
    private javax.swing.JTextPane manualTextPane;
    private javax.swing.JInternalFrame myFile;
    private javax.swing.JLabel noResults;
    private javax.swing.JButton notepad;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton passwordReset;
    private javax.swing.JTextField passwordText;
    private javax.swing.JButton power;
    private javax.swing.JButton resetWallpaper;
    private javax.swing.JTextField searchBar;
    private javax.swing.JTextField searchBar2;
    private javax.swing.JButton settings;
    private javax.swing.JLabel settingsVirus;
    private javax.swing.JLabel suggestions;
    private javax.swing.JLabel sword;
    private javax.swing.JButton taskbarAntivirus;
    private javax.swing.JButton taskbarBrowser;
    private javax.swing.JButton taskbarManual;
    private javax.swing.JButton taskbarNotepad;
    private javax.swing.JPanel taskbarPanel;
    private javax.swing.JButton taskbarSettings;
    private javax.swing.JScrollPane textField;
    private javax.swing.JLabel theVirus1;
    private javax.swing.JLabel theVirus2;
    private javax.swing.JLabel theVirus3;
    private javax.swing.JLabel theVirus4;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JButton usernameReset;
    private javax.swing.JTextField usernameText;
    private javax.swing.JInternalFrame virusFrame;
    private javax.swing.JScrollPane virusMessage;
    private javax.swing.JLabel virusResult;
    private javax.swing.JButton virusScan;
    private javax.swing.JButton wallpaper;
    private javax.swing.JPanel whiteBroswer;
    // End of variables declaration//GEN-END:variables
}
