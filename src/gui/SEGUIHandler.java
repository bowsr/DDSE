package gui;

import bit.BitConverter;
import file.FileHandler;
import gui.input.SEButton;
import gui.input.SECheckBox;
import gui.input.SESpinner;
import gui.input.arena.ArenaEditPane;
import resource.Resources;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;

public class SEGUIHandler {

    public JFrame frame;
    public SEPanel left, right, squids, spiders, pedes, other, selections, entry, modify, main, back, exit, titleBar, radii, bright, radStart, radFinal, shrink, brightPanel;
    public SEList list;
    public SEListModel elm;
    public ArrayList<SECheckBox> enemySelections;
    public SEButton addButton, deleteButton, clearButton, exitButton, createButton, openButton, editDelayButton, editEnemyType;
    public JTextPane titleText, listTitles, arenaTitle;
    public JComboBox combo;
    public JSpinner delayspin, spinBrightness, radiusStart, radiusFinal, shrinkRate;
    public JFileChooser chooser;
    public ArenaEditPane aep;

    public static SEGUIHandler instance;

    private static final String PROGRAM_TITLE = "DDSE " + Resources.PROGRAM_VERSION;
    private static final double START_RADIUS = 50.0000038;
    private static final double FINAL_RADIUS = 20.0;
    private static final double SHRINK_RATE = 0.025;
    private static final double BRIGHTNESS = 60.0;

    public SEGUIHandler() {
        instance = this;

        frame = new SEFrame(PROGRAM_TITLE);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializePanels();

        aep = new ArenaEditPane();

        back = new SEPanel();
        back.add(titleBar, BorderLayout.PAGE_START);
        back.add(main, BorderLayout.CENTER);
        back.add(aep, BorderLayout.LINE_END);

        back.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        frame.add(back);

        frame.getContentPane().setBackground(new Color(43, 43, 43));

        frame.setUndecorated(true);
        frame.setResizable(false);

        frame.pack();
        frame.setLocation(50, 50);
    }

    public void setVisible() {
        frame.setVisible(true);
    }

    private void initializeButtonsAndInputs() {
        addButton = new SEButton("Add Spawns", 100, 25, "add_selected");
        deleteButton = new SEButton("Delete", 80, 25, "delete_entry");
        deleteButton.setEnabled(false);
        clearButton = new SEButton("Clear", 80, 25, "clear_list");
        exitButton = new SEButton("Exit", 80, 25, "exit");
        exitButton.setBackground(new Color(167, 84, 75));
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        createButton = new SEButton("Create File", 120, 25, "create_file");
        createButton.setBackground(new Color(62, 134, 159));
        createButton.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30)));
        openButton = new SEButton("Open File", 120, 25, "open_file");
        openButton.setBackground(new Color(62, 134, 159));
        openButton.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30)));
        editDelayButton = new SEButton("Edit Delay", 120, 25, "edit_delay");
        editDelayButton.setEnabled(false);
        editEnemyType = new SEButton("Edit Enemy", 120, 25, "edit_enemy");
        editEnemyType.setEnabled(false);

        chooser = new JFileChooser(System.getProperty("user.dir"));
    }

    private void initializeList() {
        elm = new SEListModel();
        list = new SEList(elm);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.addListSelectionListener(this::listSelectionListener);
    }

    private void initializePanels() {
        initializeList();
        initializeSelections();
        initializeButtonsAndInputs();

        Font cb_font = enemySelections.get(0).getFont();

        modify = new SEPanel(new FlowLayout());
        modify.add(deleteButton);
        modify.add(editEnemyType);
        modify.add(editDelayButton);
        modify.add(clearButton);

        left = new SEPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
        JScrollPane jsp = new SEScrollPane(list);
        jsp.setPreferredSize(new Dimension(350, 500));
        jsp.setBorder(BorderFactory.createLineBorder(new Color(35, 35, 35)));

        titleText = new JTextPane();
        titleText.setFont(new Font(enemySelections.get(0).getFont().getName(), Font.BOLD, 24));
        titleText.setText(PROGRAM_TITLE);
        titleText.setForeground(Color.WHITE);
        titleText.setBackground(new Color(43, 43, 43));
        titleText.setBorder(BorderFactory.createEmptyBorder());

        arenaTitle = new JTextPane();
        arenaTitle.setFont(new Font(enemySelections.get(0).getFont().getName(), Font.BOLD, 20));
        arenaTitle.setText("Arena Editor");
        arenaTitle.setForeground(Color.WHITE);
        arenaTitle.setBackground(new Color(43, 43, 43));
        arenaTitle.setBorder(BorderFactory.createEmptyBorder());

        titleBar = new SEPanel(new FlowLayout());
        titleBar.add(titleText);
        titleBar.add(createFiller(815, 24));
        titleBar.add(arenaTitle);
        titleBar.add(createFiller(200, 24));


        listTitles = new JTextPane();
        listTitles.setFont(new Font(Font.MONOSPACED, Font.PLAIN, list.getFont().getSize()));
        listTitles.setForeground(Color.WHITE);
        listTitles.setBackground(new Color(43, 43, 43));
        listTitles.setBorder(BorderFactory.createEmptyBorder());
        listTitles.setText("Enemy Type     Delay   Spawn Time   Repeated (Last 17)");

        left.add(listTitles);
        left.add(jsp);
        left.add(createFiller(100, 10));
        left.add(modify);

        selections = new SEPanel();
        selections.setLayout(new BoxLayout(selections, BoxLayout.LINE_AXIS));
        selections.add(squids);
        selections.add(spiders);
        selections.add(pedes);
        selections.add(other);

        SETextPane spawnDelay = new SETextPane();
        spawnDelay.setFont(enemySelections.get(0).getFont());
        spawnDelay.setText("Spawn Delay: ");

        SETextPane lastSpawns = new SETextPane();
        lastSpawns.setFont(enemySelections.get(0).getFont());
        lastSpawns.setText("Last 17 Repeating Spawns: ");

        SETextPane directory = new SETextPane();
        directory.setFont(enemySelections.get(0).getFont());
        directory.setText("Output directory name:");
        directory.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        entry = new SEPanel(new BorderLayout());
        delayspin = new SESpinner(0, 0, 2000, 1);
        delayspin.setPreferredSize(new Dimension(100, 12));
        entry.add(delayspin, BorderLayout.LINE_START);
        entry.add(addButton, BorderLayout.LINE_END);

        exit = new SEPanel(new FlowLayout());
        exit.add(createButton);
        exit.add(openButton);
        exit.add(exitButton);

        radiusStart = new SESpinner(START_RADIUS, 12.0, 200.0, 0.25);
        radiusStart.setPreferredSize(new Dimension(30, 25));
        radiusFinal = new SESpinner(FINAL_RADIUS, 12.0, 200.0, 0.25);
        radiusFinal.setPreferredSize(new Dimension(30, 25));
        spinBrightness = new SESpinner(BRIGHTNESS, 0.0, 300.0, 1.0);
        spinBrightness.setPreferredSize(new Dimension(30, 25));
        shrinkRate = new SESpinner(SHRINK_RATE, 0.0, 1.0, 0.005);
        shrinkRate.setPreferredSize(new Dimension(30, 25));

        SETextPane rStartText = new SETextPane();
        rStartText.setFont(cb_font);
        rStartText.setText("Initial Radius:");
        SETextPane rFinalText = new SETextPane();
        rFinalText.setFont(cb_font);
        rFinalText.setText("Final Radius:");
        SETextPane shrinkText = new SETextPane();
        shrinkText.setFont(cb_font);
        shrinkText.setText("Shrink Rate:  ");
        SETextPane brightText = new SETextPane();
        brightText.setFont(cb_font);
        brightText.setText("  Brightness:");


        radii = new SEPanel();
        radStart = new SEPanel();
        radStart.setLayout(new BoxLayout(radStart, BoxLayout.PAGE_AXIS));
        radFinal = new SEPanel();
        radFinal.setLayout(new BoxLayout(radFinal, BoxLayout.PAGE_AXIS));
        radStart.add(rStartText);
        radStart.add(radiusStart);
        radFinal.add(rFinalText);
        radFinal.add(radiusFinal);
        radii.add(radStart, BorderLayout.LINE_START);
        radii.add(radFinal, BorderLayout.LINE_END);
        radii.setPreferredSize(new Dimension(80, 40));

        bright = new SEPanel();
        shrink = new SEPanel();
        shrink.setLayout(new BoxLayout(shrink, BoxLayout.PAGE_AXIS));
        brightPanel = new SEPanel();
        brightPanel.setLayout(new BoxLayout(brightPanel, BoxLayout.PAGE_AXIS));
        shrink.add(shrinkText);
        shrink.add(shrinkRate);
        brightPanel.add(brightText);
        brightPanel.add(spinBrightness);
        bright.add(shrink, BorderLayout.LINE_START);
        bright.add(brightPanel, BorderLayout.LINE_END);
        bright.setPreferredSize(new Dimension(80, 40));

        right = new SEPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
        right.add(createFiller(0, 12));
        right.add(selections);
        right.add(createFiller(0, 13));
        right.add(spawnDelay, Component.LEFT_ALIGNMENT);
        right.add(entry);
        right.add(createFiller(0, 20));
        right.add(lastSpawns, Component.LEFT_ALIGNMENT);
        right.add(combo);
        right.add(createFiller(0, 20));
        right.add(radii);
        right.add(bright);
        right.add(createFiller(0, 226));
        right.add(exit);

        main = new SEPanel(new FlowLayout());
        main.add(left);
        main.add(right);
    }

    private void initializeSelections() {
        enemySelections = new ArrayList<>();

        squids = new SEPanel();
        squids.setLayout(new BoxLayout(squids, BoxLayout.PAGE_AXIS));
        squids.setAlignmentY(Component.TOP_ALIGNMENT);
        spiders = new SEPanel();
        spiders.setLayout(new BoxLayout(spiders, BoxLayout.PAGE_AXIS));
        spiders.setAlignmentY(Component.TOP_ALIGNMENT);
        pedes = new SEPanel();
        pedes.setLayout(new BoxLayout(pedes, BoxLayout.PAGE_AXIS));
        pedes.setAlignmentY(Component.TOP_ALIGNMENT);
        other = new SEPanel();
        other.setLayout(new BoxLayout(other, BoxLayout.PAGE_AXIS));
        other.setAlignmentY(Component.TOP_ALIGNMENT);

        new SECheckBox("SQUID I", 0, squids, enemySelections);
        new SECheckBox("SQUID II", 1, squids, enemySelections);
        new SECheckBox("CENTIPEDE", 2, pedes, enemySelections);
        new SECheckBox("SPIDER I", 3, spiders, enemySelections);
        new SECheckBox("LEVIATHAN", 4, other, enemySelections);
        new SECheckBox("GIGAPEDE", 5, pedes, enemySelections);
        new SECheckBox("SQUID III", 6, squids, enemySelections);
        new SECheckBox("THORN", 7, other, enemySelections);
        new SECheckBox("SPIDER II", 8, spiders, enemySelections);
        new SECheckBox("GHOSTPEDE", 9, pedes, enemySelections);
        new SECheckBox("EMPTY", -1, other, enemySelections);

        String[] options = {"Default", "Empty", "Last 17 - Fill with EMPTY as needed"};
        combo = new JComboBox<>(options);
        combo.setEditable(false);
        combo.setSelectedIndex(0);
        combo.setBackground(new Color(62, 62, 62));
        combo.setForeground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(62, 62, 62)));
    }

    public void loadDataFromFile() {
        elm.loadEnemyListFromFile();
        list.clearSelection();

        aep.loadMapFromFile();

        byte[] buffer = FileHandler.getTraitBuffer(), tmp = new byte[4];
        System.arraycopy(buffer, 8, tmp, 0, 4);
        radiusFinal.setValue((double) BitConverter.toSingle(tmp));

        System.arraycopy(buffer, 12, tmp, 0, 4);
        radiusStart.setValue((double) BitConverter.toSingle(tmp));

        System.arraycopy(buffer, 16, tmp, 0, 4);
        shrinkRate.setValue((double) BitConverter.toSingle(tmp));

        System.arraycopy(buffer, 20, tmp, 0, 4);
        spinBrightness.setValue((double) BitConverter.toSingle(tmp));
    }

    private Box.Filler createFiller(int width, int height) {
        return new Box.Filler(new Dimension(0, 0), new Dimension(width, height), new Dimension(2000, 2000));
    }

    private void listSelectionListener(ListSelectionEvent lse) {
        if(!lse.getValueIsAdjusting()) {
            if(list.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
                editDelayButton.setEnabled(false);
                editEnemyType.setEnabled(false);
            }else {
                deleteButton.setEnabled(true);
                editDelayButton.setEnabled(true);
                if(SECheckBox.singleSelection) editEnemyType.setEnabled(true);
            }
        }
    }
}
