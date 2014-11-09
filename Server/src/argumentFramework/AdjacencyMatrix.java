package argumentFramework;

import java.util.ArrayList;

public class AdjacencyMatrix {
	private ArrayList<ArrayList<Boolean>> matrix;
	private int rowSize;
	private int columnSize;

	public AdjacencyMatrix() {
		this(0, 0);
	}

	public AdjacencyMatrix(int rowSize, int columnSize) {
		this.matrix = new ArrayList<ArrayList<Boolean>>();
		this.rowSize = 0;
		this.columnSize = 0;

		for(int i=0; i<rowSize; i++){ addRowAt(this.rowSize); }
		for(int i=0; i<columnSize; i++){ addColumnAt(this.columnSize); }
	}

	public Boolean getValue(int row, int column) { return this.matrix.get(row).get(column); }
	public void setValue(int row, int column, boolean value) { this.matrix.get(row).set(column, value); }

	public void addRowAt(int index){
		if(index <= this.rowSize){
			ArrayList<Boolean> newRow = new ArrayList<Boolean>();
			for(int i=0; i<this.columnSize; i++){
				newRow.add(false);
			}
			this.matrix.add(index, newRow);
			this.rowSize = this.matrix.size();
		}
	}

	public void removeRowAt(int index){

	}

	public void addColumnAt(int index){
		if(index <= this.columnSize){
			for(int i=0; i<this.rowSize; i++){
				ArrayList<Boolean> row = this.matrix.get(i);
				row.add(index, false);
			}
			this.columnSize++;
		}
	}

	public void removeColumnAt(int index){

	}

	public String toString(){
		System.out.println(this.rowSize + " : " + this.columnSize);
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<this.rowSize; i++){
			for(int j=0; j<this.columnSize; j++){
				if(this.matrix.get(i).get(j)){
					sb.append("o ");
				}else{
					sb.append("x ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args){
		AdjacencyMatrix matrix = new AdjacencyMatrix(2, 3);
		matrix.setValue(0, 0, true);
		matrix.setValue(1, 2, true);
		System.out.println("print matrix");
		System.out.println(matrix.toString());
		matrix.addColumnAt(0);
		matrix.addColumnAt(1);
		matrix.addRowAt(1);
		matrix.setValue(1, 4, true);
		System.out.println("print matrix");
		System.out.println(matrix.toString());
		matrix.setValue(2, 4, false);
		matrix.addColumnAt(3);
		matrix.addRowAt(3);
		System.out.println("print matrix");
		System.out.println(matrix.toString());
	}
}
