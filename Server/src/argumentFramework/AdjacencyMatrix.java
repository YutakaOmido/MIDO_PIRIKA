package argumentFramework;

import java.util.ArrayList;

public class AdjacencyMatrix {
	private ArrayList<ArrayList<Boolean>> matrix;
	private int rowSize;
	private int columnSize;

	public AdjacencyMatrix(){
		this.matrix = new ArrayList<ArrayList<Boolean>>();
		this.rowSize = 0;
		this.columnSize = 0;
	}

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
				row.add(false);
			}
			this.columnSize++;
		}
	}

	public void removeColumnAt(int index){

	}

	public String toString(){
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
		AdjacencyMatrix matrix = new AdjacencyMatrix();
		System.out.println("print matrix");
		System.out.println(matrix.toString());
		matrix.addColumnAt(0);
		matrix.addColumnAt(1);
		matrix.addRowAt(0);
		matrix.addColumnAt(0);
		matrix.addRowAt(0);
		System.out.println("print matrix");
		System.out.println(matrix.toString());
	}
}
