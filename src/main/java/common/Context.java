package common;

/**
 * @author kinst
 */
public class Context {
	int start = 0;
	int end = 0;
	int split =1;
	int cnNumber = 0;
	int otherNumber =0;
	int passNumber =0;

	String res = "";
	StringBuffer sb = new StringBuffer();

	public Context(int split) {
		this.split = split;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getSplit() {
		return split;
	}

	public void setSplit(int split) {
		this.split = split;
	}

	public int getCnNumber() {
		return cnNumber;
	}

	public void addCnNumber() {
		this.cnNumber++;
	}

	public void setCnNumber(int cnNumber) {
		this.cnNumber = cnNumber;
	}

	public int getOtherNumber() {
		return otherNumber;
	}

	public void addOtherNumber() {
		this.otherNumber++;
	}

	public void setOtherNumber(int otherNumber) {
		this.otherNumber = otherNumber;
	}

	public int getPassNumber() {
		return passNumber;
	}

	public void setPassNumber(int passNumber) {
		this.passNumber = passNumber;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public StringBuffer getSb() {
		return sb;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
}
