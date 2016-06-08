

public class Position {
	protected int rownum;
	protected int colnum;
	
	public Position(int rownum, int colnum){
		this.rownum = rownum;
		this.colnum = colnum;
	}
	
	@Override
	protected Position clone() {
		Position clone = new Position(this.rownum,this.colnum);
		return clone;
	}

	@Override
	public String toString() {
		return "Position [rownum=" + rownum + ", colnum=" + colnum + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colnum;
		result = prime * result + rownum;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (colnum != other.colnum)
			return false;
		if (rownum != other.rownum)
			return false;
		return true;
	}
}
