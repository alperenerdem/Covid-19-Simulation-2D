import java.awt.*;
import javax.swing.*;

public class View extends JFrame {

    People people;

    /**
     * creating all views(buttons,canvas) on gui
     */
    public View(){
        this.setSize(1100,639);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);


        JButton addButton= new JButton("addPerson");
        addButton.setSize(100,20);
        addButton.setLocation(0,20);
        this.add(addButton);

        JButton addButton5= new JButton("5");
        addButton5.setSize(50,20);
        addButton5.setLocation(0,50);
        this.add(addButton5);

        JButton addButton30= new JButton("30");
        addButton30.setSize(50,20);
        addButton30.setLocation(50,50);
        this.add(addButton30);

        JButton addButton200= new JButton("200");
        addButton200.setSize(100,20);
        addButton200.setLocation(0,80);
        this.add(addButton200);

        JButton stopButton= new JButton("Start-Stop");
        stopButton.setSize(100,20);
        stopButton.setLocation(0,390);
        this.add(stopButton);

        JButton restartButton= new JButton("Restart");
        restartButton.setSize(100,20);
        restartButton.setLocation(0,420);
        this.add(restartButton);



        JPanel infoPanel= new JPanel();
        infoPanel.setBackground(new Color(146, 220, 244));
        infoPanel.setBounds(0,450,100,150);

        JLabel infos= new JLabel();
        infos.setText("\n1 Infected\n");
        infos.setBounds(0,450,100,150);
        this.add(infos);


        people =new People(infos);
        this.add(people);





        JButton addInfectedButton= new JButton("add Infected");
        addInfectedButton.setSize(100,20);
        addInfectedButton.setLocation(0,120);
        this.add(addInfectedButton);

        JButton addInfectedButton5= new JButton("5 Infected");
        addInfectedButton5.setSize(100,20);
        addInfectedButton5.setLocation(0,150);
        this.add(addInfectedButton5);

        JLabel maskRate= new JLabel();
        maskRate.setText("<html>Mask Rate <br/> %90 </html>");
        maskRate.setBounds(0,170,100,40);
        this.add(maskRate);

        JButton upMaskRate= new JButton("+");
        upMaskRate.setSize(50,20);
        upMaskRate.setLocation(0,210);
        this.add(upMaskRate);

        JButton downMaskRate= new JButton("-");
        downMaskRate.setSize(50,20);
        downMaskRate.setLocation(50,210);
        this.add(downMaskRate);


        JLabel spreadFactor= new JLabel();
        spreadFactor.setText("<html>Spreading Factor <br/>     0,9 </html>");
        spreadFactor.setBounds(0,230,100,40);
        this.add(spreadFactor);

        JButton upSpreadingFactor= new JButton("+");
        upSpreadingFactor.setSize(50,20);
        upSpreadingFactor.setLocation(0,270);
        this.add(upSpreadingFactor);

        JButton downSpreadingFactor= new JButton("-");
        downSpreadingFactor.setSize(50,20);
        downSpreadingFactor.setLocation(50,270);
        this.add(downSpreadingFactor);


        JLabel mortalityFactor= new JLabel();
        mortalityFactor.setText("<html>Mortality Factor <br/>     0,7 </html>");
        mortalityFactor.setBounds(0,290,100,40);
        this.add(mortalityFactor);


        JButton upMortalityRate= new JButton("+");
        upMortalityRate.setSize(50,20);
        upMortalityRate.setLocation(0,330);
        this.add(upMortalityRate);

        JButton downMortalityRate= new JButton("-");
        downMortalityRate.setSize(50,20);
        downMortalityRate.setLocation(50,330);
        this.add(downMortalityRate);


        Controller controller = new Controller(people,addButton,addButton5,addButton30,addButton200,stopButton);
        controller.addInfectedButtons(addInfectedButton5,addInfectedButton);
        controller.addRestartButton(restartButton);
        controller.addMaskButtons(maskRate,upMaskRate,downMaskRate);
        controller.addFactorsAndButtons(mortalityFactor,spreadFactor,upMortalityRate,downMortalityRate,upSpreadingFactor,downSpreadingFactor);
        addButton.addActionListener(controller);
        addButton5.addActionListener(controller);
        addButton30.addActionListener(controller);
        addButton200.addActionListener(controller);
        stopButton.addActionListener(controller);







        infoPanel.add(infos);

        this.add(infoPanel);


        this.setVisible(true);
    }


}
