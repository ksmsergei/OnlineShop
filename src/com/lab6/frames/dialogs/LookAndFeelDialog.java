package com.lab6.frames.dialogs;

import com.lab6.Main;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;

public class LookAndFeelDialog  {

    public static class LookAndFeelDialogResult {
        public final DialogResult result;
        public final String className;

        public LookAndFeelDialogResult(DialogResult result, String className) {
            this.result = result;
            this.className = className;
        }
    }

    //Все доступные Look And Feel
    private static final LookAndFeelInfo[] LOOK_AND_FEELS = {
            //Core Themes
            new LookAndFeelInfo("FlatLaf Light", "com.formdev.flatlaf.FlatLightLaf"),
            new LookAndFeelInfo("FlatLaf Dark", "com.formdev.flatlaf.FlatDarkLaf"),
            new LookAndFeelInfo("FlatLaf IntelliJ (based on FlatLaf Light)", "com.formdev.flatlaf.FlatIntelliJLaf"),
            new LookAndFeelInfo("FlatLaf Darcula (based on FlatLaf Dark)", "com.formdev.flatlaf.FlatDarculaLaf"),
            new LookAndFeelInfo("FlatLaf macOS Light v3", "com.formdev.flatlaf.themes.FlatMacLightLaf"),
            new LookAndFeelInfo("FlatLaf macOS Dark v3", "com.formdev.flatlaf.themes.FlatMacDarkLaf"),

            //FlatLaf IntelliJ Themes Pack
            new LookAndFeelInfo("Arc", "com.formdev.flatlaf.intellijthemes.FlatArcIJTheme"),
            new LookAndFeelInfo("Arc - Orange", "com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme"),
            new LookAndFeelInfo("Arc Dark", "com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme"),
            new LookAndFeelInfo("Arc Dark - Orange", "com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme"),
            new LookAndFeelInfo("Carbon", "com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme"),
            new LookAndFeelInfo("Cobalt 2", "com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme"),
            new LookAndFeelInfo("Cyan light", "com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme"),
            new LookAndFeelInfo("Dark Flat", "com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme"),
            new LookAndFeelInfo("Dark purple", "com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme"),
            new LookAndFeelInfo("Dracula", "com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme"),
            new LookAndFeelInfo("Gradianto Dark Fuchsia", "com.formdev.flatlaf.intellijthemes.FlatGradiantoDarkFuchsiaIJTheme"),
            new LookAndFeelInfo("Gradianto Deep Ocean", "com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme"),
            new LookAndFeelInfo("Gradianto Midnight Blue", "com.formdev.flatlaf.intellijthemes.FlatGradiantoMidnightBlueIJTheme"),
            new LookAndFeelInfo("Gradianto Nature Green", "com.formdev.flatlaf.intellijthemes.FlatGradiantoNatureGreenIJTheme"),
            new LookAndFeelInfo("Gray", "com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme"),
            new LookAndFeelInfo("Gruvbox Dark Hard", "com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme"),
            new LookAndFeelInfo("Gruvbox Dark Medium", "com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkMediumIJTheme"),
            new LookAndFeelInfo("Gruvbox Dark Soft", "com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkSoftIJTheme"),
            new LookAndFeelInfo("Hiberbee Dark", "com.formdev.flatlaf.intellijthemes.FlatHiberbeeDarkIJTheme"),
            new LookAndFeelInfo("High contrast", "com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme"),
            new LookAndFeelInfo("Light Flat", "com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme"),
            new LookAndFeelInfo("Material Design Dark", "com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme"),
            new LookAndFeelInfo("Monocai", "com.formdev.flatlaf.intellijthemes.FlatMonocaiIJTheme"),
            new LookAndFeelInfo("Monokai Pro", "com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme"),
            new LookAndFeelInfo("Nord", "com.formdev.flatlaf.intellijthemes.FlatNordIJTheme"),
            new LookAndFeelInfo("One Dark", "com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme"),
            new LookAndFeelInfo("Solarized Dark", "com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme"),
            new LookAndFeelInfo("Solarized Light", "com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme"),
            new LookAndFeelInfo("Spacegray", "com.formdev.flatlaf.intellijthemes.FlatSpacegrayIJTheme"),
            new LookAndFeelInfo("Vuesion", "com.formdev.flatlaf.intellijthemes.FlatVuesionIJTheme"),
            new LookAndFeelInfo("Xcode-Dark", "com.formdev.flatlaf.intellijthemes.FlatXcodeDarkIJTheme"),

            //Material Theme UI Lite:
            new LookAndFeelInfo("Arc Dark (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme"),
            new LookAndFeelInfo("Atom One Dark (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme"),
            new LookAndFeelInfo("Atom One Light (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme"),
            new LookAndFeelInfo("Dracula (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatDraculaIJTheme"),
            new LookAndFeelInfo("GitHub (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme"),
            new LookAndFeelInfo("GitHub Dark (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme"),
            new LookAndFeelInfo("Light Owl (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlIJTheme"),
            new LookAndFeelInfo("Material Darker (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme"),
            new LookAndFeelInfo("Material Deep Ocean (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme"),
            new LookAndFeelInfo("Material Lighter (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme"),
            new LookAndFeelInfo("Material Oceanic (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialOceanicIJTheme"),
            new LookAndFeelInfo("Material Palenight (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialPalenightIJTheme"),
            new LookAndFeelInfo("Monokai Pro (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMonokaiProIJTheme"),
            new LookAndFeelInfo("Moonlight (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme"),
            new LookAndFeelInfo("Night Owl (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlIJTheme"),
            new LookAndFeelInfo("Solarized Dark (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedDarkIJTheme"),
            new LookAndFeelInfo("Solarized Light (Material)", "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedLightIJTheme")
    };

    //Вызвать и получить полное имя класса Look And Feel
    public static LookAndFeelDialogResult show(Component parentComponent) {
        for (LookAndFeelInfo curr : LOOK_AND_FEELS) {
            UIManager.installLookAndFeel(curr);
        }

        JComboBox<LookAndFeelInfo> availableLookAndFeels = getLookAndFeelJComboBox();

        //Компоненты внутри диалогового окна
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Выберите Look and Feel:"),
                availableLookAndFeels
        };

        //Показать диалоговое окно
        int selectedOption = JOptionPane.showConfirmDialog(parentComponent, inputs, Main.superShop.getName(), JOptionPane.OK_CANCEL_OPTION);

        //Если нажали OK
        if (selectedOption == JOptionPane.OK_OPTION) {
            LookAndFeelInfo selectedLookAndFeel = (LookAndFeelInfo) availableLookAndFeels.getSelectedItem();

            if (selectedLookAndFeel == null) {
                return new LookAndFeelDialogResult(DialogResult.INCORRECT_INPUT, null);
            }

            return new LookAndFeelDialogResult(DialogResult.OK, selectedLookAndFeel.getClassName());
        }

        return new LookAndFeelDialogResult(DialogResult.CANCEL, null);
    }

    private static JComboBox<LookAndFeelInfo> getLookAndFeelJComboBox() {
        //Создать ComboBox из установленных Look and Feel
        JComboBox<LookAndFeelInfo> availableLookAndFeels = new JComboBox<>(UIManager.getInstalledLookAndFeels());

        //Правильное отображение элементов в LookAndFeel
        availableLookAndFeels.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                value = ((LookAndFeelInfo) value).getName();
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                return this;
            }
        });
        return availableLookAndFeels;
    }
}
