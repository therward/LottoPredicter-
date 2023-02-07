//This application is NOT intended to be used for gambling and is purely a joke application\\ 


package LottoPredictor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

public class JavaApplication1 extends JFrame {
    private JButton startButton, refreshButton, stopButton;
    private JLabel randomLabel; 
    private JPanel panel;

    public JavaApplication1() {
        setTitle("Lottery Number Predictor");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(Color.BLACK);

        startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());

        refreshButton = new JButton("Refresh");
        refreshButton.setVisible(false);
        refreshButton.addActionListener(new RefreshButtonListener());

        stopButton = new JButton("Stop");
        stopButton.setVisible(false);
        stopButton.addActionListener(new StopButtonListener());

        randomLabel = new JLabel();
        randomLabel.setForeground(Color.WHITE);

        panel.add(startButton);
        panel.add(refreshButton);
        panel.add(stopButton);
        panel.add(randomLabel);
        add(panel);

        setVisible(true);
    }

    private class StartButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            startButton.setVisible(false);
            refreshButton.setVisible(true);
            stopButton.setVisible(true);
            generateRandomNumbers();
        }
    }

    private class RefreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            generateRandomNumbers();
            
            
                //to be added in future version
                //refreshButton.setVisible(false);
                //stopButton.setVisible(false);
            
            
            
            
           try {
            // Create a new Synthesizer and open it
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();

            // Get the default soundbank and load it into the synthesizer
            Soundbank soundbank = synth.getDefaultSoundbank();
            synth.loadAllInstruments(soundbank);

            // Create a new sequence and track
            Sequence seq = new Sequence(Sequence.PPQ, 16);
            Track track = seq.createTrack();

            // Create a random number generator
            Random rand = new Random();

            // Add random notes to the track
            for (int i = 0; i < 64; i++) {
                int note = rand.nextInt(127);
                int instrument = rand.nextInt(127);
                int velocity = rand.nextInt(127);
                int duration = rand.nextInt(64);

                track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0), i));
                track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, note, velocity), i));
                track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, note, velocity), i + duration));
            }

            // Play the sequence
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(seq);
            sequencer.start();
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
            
        } 
        }
    }

    private class StopButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            randomLabel.setText("");
            startButton.setVisible(true);
            refreshButton.setVisible(false);
            stopButton.setVisible(false);
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void generateRandomNumbers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomNum = (int)(Math.random() * 100);
            sb.append(randomNum + " ");
        }
        int randomNum = (int)(Math.random() * 100);
        sb.append(randomNum);
        randomLabel.setText(sb.toString());
    }

    public static void main(String[] args) {
        new JavaApplication1();
    }
}

