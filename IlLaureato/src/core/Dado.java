package core;

import java.io.Serializable;

public class Dado implements Serializable{

	private static final long serialVersionUID = 1L;

	private int dadi;

	public Dado(){}

	public int lanciaDadi(){

		this.dadi = 1;// ( int )( ( Math.random() * 6 ) + 1 );
		return this.dadi;
	}
}
