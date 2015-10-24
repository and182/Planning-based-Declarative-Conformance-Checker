package main;

import java.util.Vector;
import javax.swing.JFrame;
import view.AlphabetPerspective;
import view.MenuPerspective;
import view.RulesPerspective;
import view.TracePerspective;

public class Constants {

	private static JFrame desktop;
	private static MenuPerspective menuPerspective;
	private static AlphabetPerspective alphabetPanel;
	private static TracePerspective tracePanel;
	private static RulesPerspective rulesPanel;
	
	//
	// Vector that records the complete alphabet of tasks of the log. 
	// Notice that the alphabet of tasks may include tasks that are included in some trace, 
	// but not used by any DECLARE rule, and vice-versa.
	//	
	private static Vector<String> tasks_repository_vector = new Vector<String>();	
	
	//
	// Vector that records all the traces (java object Traces) of the log. 
	//	
	private static Vector<Trace> all_traces_vector = new Vector<Trace>();

	private static Vector<String> rules_vector = new Vector<String>();
	private static Vector<String> PDDL_rules_vector = new Vector<String>();
	
	public static String LTL_NOT = "\u00AC";
	public static String LTL_OR = "\u2228";
	public static String LTL_AND = "\u2227";
	public static String LTL_IMPLIES = "\u2192";
	public static String LTL_eventually = "\u25C7";
	public static String LTL_globally = "\u25A1";
	public static String LTL_next = "\u25CB";	
	public static String LTL_weak_until = "W".toUpperCase();
	public static String LTL_until = "U".toUpperCase();
	
	public static JFrame getDesktop() {
		return desktop;
	}
	public static void setDesktop(JFrame desk) {
		desktop = desk;
	}
	public static TracePerspective getTracePerspective() {
		return tracePanel;
	}
	public static void setTracePerspective(TracePerspective trcPanel) {
		tracePanel = trcPanel;
	}
	public static AlphabetPerspective getAlphabetPerspective() {
		return alphabetPanel;
	}
	public static void setAlphabetPerspective(AlphabetPerspective alphPanel) {
		alphabetPanel = alphPanel;
	}	
	public static RulesPerspective getRulesPerspective() {
		return rulesPanel;
	}
	public static void setRulesPerspective(RulesPerspective rlPanel) {
		rulesPanel = rlPanel;
	}
	public static MenuPerspective getMenuPerspective() {
		return menuPerspective;
	}
	public static void setMenuPerspective(MenuPerspective menu) {
		menuPerspective = menu;
	}
	public static Vector<String> getTasksRepositoryVector() {
		return tasks_repository_vector;
	}
	public static void setTasksRepositoryVector(Vector<String> v) {
		tasks_repository_vector = v;
	}
	public static Vector<Trace> get_all_traces_vector() {
		return all_traces_vector;
	}
	public static void set_all_traces_vector(Vector<Trace> all_traces_vector) {
		Constants.all_traces_vector = all_traces_vector;
	}
	public static Vector<String> getRulesVector() {
		return rules_vector;
	}
	public static void setRulesVector(Vector<String> rules_vector) {
		Constants.rules_vector = rules_vector;
	}
	public static Vector<String> getPDDLRulesVector() {
		return PDDL_rules_vector;
	}
	public static void setPDDLRulesVector(Vector<String> PDDL_rules_vector) {
		Constants.PDDL_rules_vector = PDDL_rules_vector;
	}	
}
