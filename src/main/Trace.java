package main;

import java.util.Hashtable;
import java.util.Vector;

public class Trace {

	private String name;
	private Vector<String> original_trace_content_vector;
	private Vector<String> PDDL_trace_content_vector;
	private Hashtable<String, String> trace_hashtable = new Hashtable<String, String>();
	private Vector<String> missing_tasks_vector;
	
	public Trace(String trace_name) {		
		name = trace_name;
		original_trace_content_vector = new Vector<String>();
		PDDL_trace_content_vector = new Vector<String>();
		trace_hashtable = new Hashtable<String,String>();
	}
	
	public String getTraceName() {
		return name;
	}
	
	public void setTraceName(String trace_name) {
		name = trace_name;
	}

	public Vector<String> get_Original_Trace_content_vector() {
		return original_trace_content_vector;
	}

	public void set_Original_Trace_content_vector(Vector<String> trace_content) {
		original_trace_content_vector = trace_content;
	}

	public Vector<String> get_PDDL_Trace_content_vector() {
		return PDDL_trace_content_vector;
	}

	public void set_PDDL_Trace_content_vector(Vector<String> pddl_trace_content) {
		PDDL_trace_content_vector = pddl_trace_content;
	}
	
	public Vector<String> get_missing_tasks_vector() {
		return missing_tasks_vector;
	}

	public void set_missing_tasks_vector(Vector<String> missing_tasks_vector) {
		this.missing_tasks_vector = missing_tasks_vector;
	}

	public Hashtable<String, String> get_Trace_Hashtable() {
		return trace_hashtable;
	}

	public void set_Trace_Hashtable(Hashtable<String, String> trace_hashtable) {
		this.trace_hashtable = trace_hashtable;
	}
	
	
}
