package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import control.H_RulesPerspective;

public class RulesPerspective extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> ruleComboBox;
	private JComboBox<String> firstTaskComboBox;
	private JComboBox<String> secondTaskComboBox;
	
	private JPanel rulePanel;
	private JPanel addTaskButtonPanel;
	
	private JLabel firstBoxLabel;
	private JLabel secondBoxLabel;
	private JLabel thirdBoxLabel;
	private JLabel LTLconstraintLabel;
	private JLabel blankLabel;
		
	private DefaultListModel<String> rulesListModel;
	private JList<String> rulesList;
	private JScrollPane rulesScrollPane;
	
	private JButton rightButton;
	private JButton removeButton;
	
	private JButton nextStepButton;
	private JButton previousStepButton;
	
	protected H_RulesPerspective _handler;
	
	public RulesPerspective(){
		super();
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		this.setLayout(new FlowLayout());
		this.setBorder(new TitledBorder(null, "STEP 3: Define DECLARE rules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		firstBoxLabel = new JLabel("Rule : ");
		firstBoxLabel.setPreferredSize(new Dimension(60,25));
		secondBoxLabel = new JLabel("Task 1 : ");
		secondBoxLabel.setPreferredSize(new Dimension(60,25));
		thirdBoxLabel = new JLabel("Task 2 : ");
		thirdBoxLabel.setPreferredSize(new Dimension(60,25));
		
		ruleComboBox = new JComboBox<String>();
		ruleComboBox.setPreferredSize(new Dimension(150,25));
		
		ruleComboBox.addItem("---");
		ruleComboBox.addItem("existence");
		ruleComboBox.addItem("absence");
		ruleComboBox.addItem("choice");
		ruleComboBox.addItem("ex.choice");
		ruleComboBox.addItem("resp.existence");
		/*
		ruleComboBox.addItem("response");
		ruleComboBox.addItem("precedence");
		ruleComboBox.addItem("alt.response");
		ruleComboBox.addItem("alt.precedence");
		ruleComboBox.addItem("chain response");
		ruleComboBox.addItem("chain precedence");
		ruleComboBox.addItem("not resp.existence");
		ruleComboBox.addItem("not response");
		ruleComboBox.addItem("not precedence");
		ruleComboBox.addItem("not chain resp.");
		ruleComboBox.addItem("not chain prec.");
		*/
		firstTaskComboBox = new JComboBox<String>();
		firstTaskComboBox.addItem("---");
		firstTaskComboBox.setPreferredSize(new Dimension(150,25));
		firstTaskComboBox.setEnabled(false);
		secondTaskComboBox = new JComboBox<String>();
		secondTaskComboBox.addItem("---");
		secondTaskComboBox.setPreferredSize(new Dimension(150,25));
		secondTaskComboBox.setEnabled(false);
		
		
		rulePanel = new JPanel();
		rulePanel.setLayout(new FlowLayout());
		rulePanel.setPreferredSize(new Dimension(220,100));
		rulePanel.add(firstBoxLabel);
		rulePanel.add(ruleComboBox);
		rulePanel.add(secondBoxLabel);
		rulePanel.add(firstTaskComboBox);
		rulePanel.add(thirdBoxLabel);
		rulePanel.add(secondTaskComboBox);
		
		//
		// JPanel containing the JButtons for adding new tasks in the alphabet
		//
		addTaskButtonPanel = new JPanel();
		addTaskButtonPanel.setPreferredSize(new Dimension(90,100)); 
		addTaskButtonPanel.setLayout(new FlowLayout());
		
		rightButton = new JButton("ADD>>");
		rightButton.setPreferredSize(new Dimension(85,25)); 
		removeButton = new JButton("<<DEL");
		removeButton.setPreferredSize(new Dimension(85,25));
		
		addTaskButtonPanel.add(rightButton);
		addTaskButtonPanel.add(removeButton);

		rulesListModel = new DefaultListModel<String>();
		rulesList = new JList<String>(rulesListModel);
		rulesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rulesList.setSelectedIndex(-1);

		rulesScrollPane = new JScrollPane(rulesList);
		rulesScrollPane.setPreferredSize(new Dimension(200,90));
		rulesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rulesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		LTLconstraintLabel = new JLabel("Corresponding LTL constraint: ");
		LTLconstraintLabel.setPreferredSize(new Dimension(520,25));
		
		blankLabel = new JLabel();
		blankLabel.setPreferredSize(new Dimension(250,20));
		nextStepButton = new JButton("Next Step >");
		previousStepButton = new JButton("< Previous Step");
		
		this.add(rulePanel);
		this.add(addTaskButtonPanel);
		this.add(rulesScrollPane);
		this.add(LTLconstraintLabel);
		this.add(previousStepButton);
		this.add(blankLabel);
		this.add(nextStepButton);
		this.setPreferredSize(new Dimension(540,200));
		this.setVisible(true);
		
	}
	
	public void setComponentEnabled(boolean enabled) {

		firstBoxLabel.setEnabled(enabled);
		secondBoxLabel.setEnabled(enabled);
		thirdBoxLabel.setEnabled(enabled);
		ruleComboBox.setEnabled(enabled);
		ruleComboBox.setSelectedIndex(0);
		rightButton.setEnabled(enabled);
		removeButton.setEnabled(enabled);
		rulesList.setEnabled(enabled);
		LTLconstraintLabel.setText("Corresponding LTL constraint : ");
		LTLconstraintLabel.setEnabled(enabled);
		nextStepButton.setEnabled(enabled);
		previousStepButton.setEnabled(enabled);
		this.setEnabled(enabled);
	}
	
	public void resetComponent() {

		rulesListModel.removeAllElements();

	}
	
	private void initHandler() {
		
		_handler = new H_RulesPerspective(this);
		
	}

	public JComboBox<String> getRuleComboBox() {
		return ruleComboBox;
	}

	public JComboBox<String> getFirstTaskComboBox() {
		return firstTaskComboBox;
	}

	public JComboBox<String> getSecondTaskComboBox() {
		return secondTaskComboBox;
	}

	public JLabel getSecondBoxLabel() {
		return secondBoxLabel;
	}

	public DefaultListModel<String> getRulesListModel() {
		return rulesListModel;
	}

	public JList<String> getRulesList() {
		return rulesList;
	}

	public JButton getRightButton() {
		return rightButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public JButton getNextStepButton() {
		return nextStepButton;
	}

	public JButton getPreviousStepButton() {
		return previousStepButton;
	}

	public void setRuleComboBox(JComboBox<String> ruleComboBox) {
		this.ruleComboBox = ruleComboBox;
	}

	public void setFirstTaskComboBox(JComboBox<String> firstTaskComboBox) {
		this.firstTaskComboBox = firstTaskComboBox;
	}

	public void setSecondTaskComboBox(JComboBox<String> secondTaskComboBox) {
		this.secondTaskComboBox = secondTaskComboBox;
	}

	public void setSecondBoxLabel(JLabel secondBoxLabel) {
		this.secondBoxLabel = secondBoxLabel;
	}

	public void setRulesListModel(DefaultListModel<String> rulesListModel) {
		this.rulesListModel = rulesListModel;
	}

	public void setRulesList(JList<String> rulesList) {
		this.rulesList = rulesList;
	}

	public void setRightButton(JButton rightButton) {
		this.rightButton = rightButton;
	}

	public void setRemoveButton(JButton removeButton) {
		this.removeButton = removeButton;
	}

	public void setNextStepButton(JButton nextStepButton) {
		this.nextStepButton = nextStepButton;
	}

	public void setPreviousStepButton(JButton previousStepButton) {
		this.previousStepButton = previousStepButton;
	}

	public JLabel getLTLconstraintLabel() {
		return LTLconstraintLabel;
	}

}
