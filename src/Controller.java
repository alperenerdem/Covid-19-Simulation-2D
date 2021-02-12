import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    JButton addButton,addButton5,addButton30,addButton200;
    JButton addInfectedButton,addInfectedButton5;
    JButton upSpreadingFactor,downSpreadingFactor,upMortalityRate,downMortalityRate,upMaskRate,downMaskRate;
    JLabel spread,mortality,maskRate;
    JButton stopButton,restartButton;

    People people;

    /**
     * initiate Controller with add and stop buttons,and reference of observable Mediator
     * @param people
     * @param addButton
     * @param addButton5
     * @param addButton30
     * @param addButton200
     * @param stopButton
     */
    public Controller(People people, JButton addButton, JButton addButton5, JButton addButton30, JButton addButton200, JButton stopButton){
        this.people = people;
        this.addButton=addButton;
        this.addButton5=addButton5;
        this.addButton30=addButton30;
        this.addButton200=addButton200;

        this.stopButton=stopButton;


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton){

            people.addPerson(1);
        }
        else if(e.getSource()==addButton5){

            people.addPerson(5);
        }
        else if(e.getSource()==addButton30){

            people.addPerson(30);
        }
        else if(e.getSource()==addButton200){

            people.addPerson(200);
        }
        else if(e.getSource()==addInfectedButton){

            people.addSickPerson(1);
        }
        else if(e.getSource()==addInfectedButton5){

            people.addSickPerson(5);
        }
        else if(e.getSource()==restartButton){
            people.restart();
        }
        else if(e.getSource()==stopButton){

            people.stopStart();
        }
        else if(e.getSource()==upSpreadingFactor){
            people.upSpread();
            spread.setText("<html>Spreading Factor <br/>"+String.format("%.1f", +people.getSpreadingFactor())+"</html>");
        }
        else if(e.getSource()==downSpreadingFactor){
            people.downSpread();
            spread.setText("<html>Spreading Factor <br/>"+String.format("%.1f", +people.getSpreadingFactor())+"</html>");
        }
        else if(e.getSource()==upMortalityRate){
            people.upMortality();
            mortality.setText("<html>Mortality Factor <br/>"+String.format("%.1f", +people.getMortalityRate())+"</html>");
        }
        else if(e.getSource()==downMortalityRate){
            people.downMortality();
            mortality.setText("<html>Mortality Factor <br/>"+String.format("%.1f", +people.getMortalityRate())+"</html>");
        }
        else if(e.getSource()==upMaskRate){
            people.upMask();
            maskRate.setText("<html>Mask Rate <br/>"+"%"+ people.getMaskRate() +"0</html>");
        }
        else if(e.getSource()==downMaskRate){
            people.downMask();
            maskRate.setText("<html>Mask Rate <br/>"+"%"+ people.getMaskRate() +"0</html>");
        }
    }

    /**
     * add Buttons for adding infected people to canvas
     * @param addInfectedButton5
     * @param addInfectedButton
     */
    public void addInfectedButtons(JButton addInfectedButton5, JButton addInfectedButton) {
        this.addInfectedButton=addInfectedButton;
        this.addInfectedButton.addActionListener(this);
        this.addInfectedButton5=addInfectedButton5;
        this.addInfectedButton5.addActionListener(this);
    }

    public void addRestartButton(JButton restartButton) {
        this.restartButton=restartButton;
        this.restartButton.addActionListener(this);
    }

    /**
     * add buttons and texts for manipulating mortality factor, spread factor.
     * @param mortalityFactor
     * @param spreadFactor
     * @param upMortalityRate
     * @param downMortalityRate
     * @param upSpreadingFactor
     * @param downSpreadingFactor
     */
    public void addFactorsAndButtons(JLabel mortalityFactor, JLabel spreadFactor, JButton upMortalityRate, JButton downMortalityRate, JButton upSpreadingFactor, JButton downSpreadingFactor) {
        this.mortality=mortalityFactor;
        this.spread=spreadFactor;
        this.upMortalityRate=upMortalityRate;
        this.upMortalityRate.addActionListener(this);
        this.downMortalityRate=downMortalityRate;
        this.downMortalityRate.addActionListener(this);
        this.upSpreadingFactor=upSpreadingFactor;
        this.upSpreadingFactor.addActionListener(this);
        this.downSpreadingFactor=downSpreadingFactor;
        this.downSpreadingFactor.addActionListener(this);
    }

    /**
     * add buttons and text place for manipulating mask rate.
     * @param maskRate
     * @param upMaskRate
     * @param downMaskRate
     */
    public void addMaskButtons(JLabel maskRate, JButton upMaskRate, JButton downMaskRate) {
        this.maskRate=maskRate;
        this.upMaskRate=upMaskRate;
        this.upMaskRate.addActionListener(this);
        this.downMaskRate=downMaskRate;
        this.downMaskRate.addActionListener(this);

    }
}
