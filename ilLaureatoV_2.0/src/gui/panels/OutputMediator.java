package gui.panels;

import javafx.scene.control.TextArea;

public class OutputMediator {
	private static TextArea ta;

	static void setTextArea(TextArea ta) {
		OutputMediator.ta = ta;
		ta.setTranslateZ(-1);
	}

	public static void println(String s) {
		ta.appendText(s+'\n');
	}

	public static void print(String s) {
		ta.appendText(s);
	}

}
